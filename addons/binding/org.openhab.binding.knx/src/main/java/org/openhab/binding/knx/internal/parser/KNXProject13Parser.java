/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.knx.internal.parser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.eclipse.smarthome.config.core.Configuration;
import org.openhab.binding.knx.KNXBindingConstants;
import org.openhab.binding.knx.internal.parser.knxproj13.Area;
import org.openhab.binding.knx.internal.parser.knxproj13.ComObject;
import org.openhab.binding.knx.internal.parser.knxproj13.ComObjectInstanceRef;
import org.openhab.binding.knx.internal.parser.knxproj13.ComObjectInstanceRefs;
import org.openhab.binding.knx.internal.parser.knxproj13.ComObjectRef;
import org.openhab.binding.knx.internal.parser.knxproj13.ComObjectRefs;
import org.openhab.binding.knx.internal.parser.knxproj13.ComObjectTable;
import org.openhab.binding.knx.internal.parser.knxproj13.Connectors;
import org.openhab.binding.knx.internal.parser.knxproj13.DeviceInstance;
import org.openhab.binding.knx.internal.parser.knxproj13.GroupAddress;
import org.openhab.binding.knx.internal.parser.knxproj13.GroupAddressReference;
import org.openhab.binding.knx.internal.parser.knxproj13.GroupRange;
import org.openhab.binding.knx.internal.parser.knxproj13.Hardware;
import org.openhab.binding.knx.internal.parser.knxproj13.Installation;
import org.openhab.binding.knx.internal.parser.knxproj13.KNX;
import org.openhab.binding.knx.internal.parser.knxproj13.Language;
import org.openhab.binding.knx.internal.parser.knxproj13.Languages;
import org.openhab.binding.knx.internal.parser.knxproj13.Line;
import org.openhab.binding.knx.internal.parser.knxproj13.Manufacturer;
import org.openhab.binding.knx.internal.parser.knxproj13.Product;
import org.openhab.binding.knx.internal.parser.knxproj13.Products;
import org.openhab.binding.knx.internal.parser.knxproj13.Translation;
import org.openhab.binding.knx.internal.parser.knxproj13.TranslationElement;
import org.openhab.binding.knx.internal.parser.knxproj13.TranslationUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link KNXProject13Parser} is capable of parsing knxproj files that are generated by ETS5
 *
 * @author Karel Goderis - Initial contribution
 *
 */
public class KNXProject13Parser extends AbstractKNXProjectParser {

    private final Logger logger = LoggerFactory.getLogger(KNXProject13Parser.class);

    private KNX knxMaster;
    private KNX knxZero;
    private KNX knxProject;

    // Data structures to facilitate the lookup of the many elements and data stored in the various XML files
    private HashMap<String, KNX> knxManufacturers = new HashMap<String, KNX>();
    private HashMap<String, String> knxManufacturerIdNameMap = new HashMap<String, String>();
    private HashMap<String, GroupAddress> gaGAMap = new HashMap<String, GroupAddress>();
    private HashMap<String, GroupAddress> gaRefGAMap = new HashMap<String, GroupAddress>();
    private HashMap<GroupAddress, String> GAgaMap = new HashMap<GroupAddress, String>();
    private HashMap<GroupAddress, List<ComObjectRef>> GACorMap = new HashMap<GroupAddress, List<ComObjectRef>>();
    private HashMap<String, ComObjectRef> corRefCorMap = new HashMap<String, ComObjectRef>();
    private HashMap<String, ComObject> coRefCoMap = new HashMap<String, ComObject>();
    private HashMap<ComObjectRef, ComObject> CorCoMap = new HashMap<ComObjectRef, ComObject>();
    private HashMap<String, DeviceInstance> iaDeviceMap = new HashMap<String, DeviceInstance>();
    private HashMap<DeviceInstance, String> deviceIaMap = new HashMap<DeviceInstance, String>();
    private HashMap<String, String> iaCorprefixMap = new HashMap<String, String>();
    private HashMap<DeviceInstance, Set<GroupAddress>> deviceGAMap = new HashMap<DeviceInstance, Set<GroupAddress>>();
    private HashMap<GroupAddress, Set<DeviceInstance>> GAdeviceMap = new HashMap<GroupAddress, Set<DeviceInstance>>();
    private HashMap<ComObject, TranslationElement> coTranslationMap = new HashMap<ComObject, TranslationElement>();
    private HashMap<ComObjectRef, TranslationElement> corTranslationMap = new HashMap<ComObjectRef, TranslationElement>();
    private HashMap<String, Product> productMap = new HashMap<String, Product>();
    private HashMap<Product, TranslationElement> productTranslationMap = new HashMap<Product, TranslationElement>();

    @Override
    public void addXML(HashMap<String, String> xmlRepository) {

        if (xmlRepository.containsKey("knx_master.xml")) {
            addXML("knx_master.xml", xmlRepository.get("knx_master.xml"));
        }

        if (xmlRepository.containsKey("project.xml")) {
            addXML("project.xml", xmlRepository.get("project.xml"));
        }

        for (String anXML : xmlRepository.keySet()) {
            if (anXML.contains("Hardware.xml")) {
                addXML(anXML, xmlRepository.get(anXML));
            }
        }

        for (String anXML : xmlRepository.keySet()) {
            if (!anXML.equals("project.xml") && !anXML.equals("knx_master.xml") && !anXML.contains("Hardware.xml")
                    && !anXML.contains("Baggages.xml")) {
                addXML(anXML, xmlRepository.get(anXML));
            }
        }
    }

    @Override
    public void addXML(String xmlName, String content) {

        logger.trace("Adding XML '{}' to the parser", xmlName);

        try {
            if (xmlName != null && content != null) {

                JAXBContext jaxbContext = JAXBContext.newInstance(KNX.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                KNX knxObject = (KNX) jaxbUnmarshaller
                        .unmarshal(new StreamSource(new ByteArrayInputStream(content.getBytes())));

                String name;
                if (xmlName.contains("Hardware.xml")) {
                    name = "Hardware.xml";
                } else {
                    name = xmlName;
                }

                switch (name) {
                    case "0.xml": {
                        knxZero = knxObject;

                        logger.info("Found KNX project data for project '{}'",
                                knxProject.getProject().getProjectInformation().getName());

                        Installation installation = knxZero.getProject().getInstallations().getInstallation();

                        for (GroupRange l1range : installation.getGroupAddresses().getGroupRanges().getGroupRange()) {
                            @SuppressWarnings("unchecked")
                            List<GroupRange> l2ranges = (List<GroupRange>) (Object) l1range
                                    .getGroupRangeOrGroupAddress();
                            for (GroupRange l2range : l2ranges) {
                                @SuppressWarnings("unchecked")
                                List<GroupAddress> groupAddresses = (List<GroupAddress>) (Object) l2range
                                        .getGroupRangeOrGroupAddress();
                                for (GroupAddress ga : groupAddresses) {
                                    String groupAddress = convertGroupAddress(ga.getAddress());
                                    gaGAMap.put(groupAddress, ga);
                                    GAgaMap.put(ga, groupAddress);
                                    gaRefGAMap.put(ga.getId(), ga);
                                }
                            }
                        }

                        for (Area area : installation.getTopology().getArea()) {
                            for (Line areaLine : area.getLine()) {
                                for (DeviceInstance deviceInstance : areaLine.getDeviceInstance()) {
                                    String address = area.getAddress() + "." + areaLine.getAddress() + "."
                                            + deviceInstance.getAddress();
                                    iaDeviceMap.put(address, deviceInstance);

                                    Set<GroupAddress> groupAddresses = new HashSet<GroupAddress>();
                                    ComObjectInstanceRefs comObjectInstanceRefs = deviceInstance
                                            .getComObjectInstanceRefs();
                                    if (comObjectInstanceRefs != null) {
                                        for (ComObjectInstanceRef comObjectInstanceRef : comObjectInstanceRefs
                                                .getComObjectInstanceRef()) {
                                            iaCorprefixMap.put(address,
                                                    StringUtils.substringBefore(comObjectInstanceRef.getRefId(), "_O"));
                                            Connectors connectors = comObjectInstanceRef.getConnectors();
                                            if (connectors != null) {
                                                for (JAXBElement<GroupAddressReference> groupAddressReference : connectors
                                                        .getSendOrReceive()) {
                                                    groupAddresses.add(gaRefGAMap.get(
                                                            groupAddressReference.getValue().getGroupAddressRefId()));
                                                }
                                            }
                                        }
                                    }
                                    deviceGAMap.put(deviceInstance, groupAddresses);
                                }
                            }
                        }

                        break;
                    }
                    case "knx_master.xml": {
                        knxMaster = knxObject;
                        logger.info("Found KNX master data with version '{}'", knxMaster.getMasterData().getVersion());

                        List<Manufacturer> manufacturers = knxMaster.getMasterData().getManufacturers()
                                .getManufacturer();
                        for (Manufacturer manufacturer : manufacturers) {
                            knxManufacturerIdNameMap.put(manufacturer.getId(), manufacturer.getName());
                        }
                        break;
                    }
                    case "project.xml": {
                        knxProject = knxObject;
                        logger.info("Found KNX project '{}' : {} devices, last modified {}",
                                knxProject.getProject().getProjectInformation().getName(),
                                knxProject.getProject().getProjectInformation().getDeviceCount(),
                                knxProject.getProject().getProjectInformation().getLastModified());
                        break;
                    }
                    case "Hardware.xml": {
                        logger.trace("Found KNX hardware description for '{}'", knxManufacturerIdNameMap
                                .get(knxObject.getManufacturerData().getManufacturer().getRefId()));

                        Hardware hardware = knxObject.getManufacturerData().getManufacturer().getHardware();
                        if (hardware != null) {
                            List<Hardware> hardwares = hardware.getHardware();
                            for (Hardware aHardware : hardwares) {
                                Products products = aHardware.getProducts();
                                if (products != null) {
                                    List<Product> allProducts = products.getProduct();
                                    for (Product aProduct : allProducts) {
                                        productMap.put(aProduct.getId(), aProduct);
                                        logger.info("Found KNX product '{}' by '{}'", aProduct.getText(),
                                                knxManufacturerIdNameMap.get(
                                                        knxObject.getManufacturerData().getManufacturer().getRefId()));
                                    }
                                }
                            }
                        }

                        Languages languages = knxObject.getManufacturerData().getManufacturer().getLanguages();
                        if (languages != null) {
                            for (Language language : languages.getLanguage()) {
                                if (language.getIdentifier().equals("en-US")) {
                                    List<TranslationUnit> translationUnits = language.getTranslationUnit();
                                    if (translationUnits != null) {
                                        for (TranslationUnit unit : translationUnits) {
                                            for (TranslationElement anElement : unit.getTranslationElement()) {
                                                Product product = productMap.get(anElement.getRefId());
                                                if (product != null) {
                                                    String productName = "";
                                                    if (anElement != null) {
                                                        for (Translation translation : anElement.getTranslation()) {
                                                            if (translation.getAttributeName().equals("Text")) {
                                                                productName = translation.getText();
                                                            }
                                                        }
                                                    }
                                                    productTranslationMap.put(product, anElement);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
                    default: {
                        knxManufacturers.put(name, knxObject);

                        ComObjectRefs comObjectRefs = knxObject.getManufacturerData().getManufacturer()
                                .getApplicationPrograms().getApplicationProgram().getStatic().getComObjectRefs();
                        ComObjectTable comObjects = knxObject.getManufacturerData().getManufacturer()
                                .getApplicationPrograms().getApplicationProgram().getStatic().getComObjectTable();

                        if (comObjectRefs != null) {
                            for (ComObjectRef comObjectRef : comObjectRefs.getComObjectRef()) {
                                corRefCorMap.put(comObjectRef.getId(), comObjectRef);
                                if (comObjects != null) {
                                    for (ComObject comObject : comObjects.getComObject()) {
                                        if (comObject.getId().equals(comObjectRef.getRefId())) {
                                            CorCoMap.put(comObjectRef, comObject);
                                            break;
                                        }
                                        coRefCoMap.put(comObject.getId(), comObject);
                                    }
                                }
                            }
                        }

                        Languages languages = knxObject.getManufacturerData().getManufacturer().getLanguages();
                        if (languages != null) {
                            for (Language language : languages.getLanguage()) {
                                if (language.getIdentifier().equals("en-US")) {
                                    List<TranslationUnit> translationUnits = language.getTranslationUnit();
                                    if (translationUnits != null) {
                                        for (TranslationUnit unit : translationUnits) {
                                            for (TranslationElement anElement : unit.getTranslationElement()) {
                                                ComObject comObject = this.coRefCoMap.get(anElement.getRefId());
                                                if (comObject != null) {
                                                    this.coTranslationMap.put(comObject, anElement);
                                                }
                                                ComObjectRef comObjectRef = this.corRefCorMap.get(anElement.getRefId());
                                                if (comObjectRef != null) {
                                                    this.corTranslationMap.put(comObjectRef, anElement);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        logger.trace("Found KNX manufacturer data for a device by '{}'", knxManufacturerIdNameMap
                                .get(knxObject.getManufacturerData().getManufacturer().getRefId()));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("An exception occurred while unmarshalling the KNX project data '{}' : '{}' : {}", xmlName,
                    e.getMessage(), e);
        }
    }

    @Override
    public void postProcess() {

        try {

            if (knxZero != null) {
                Installation installation = knxZero.getProject().getInstallations().getInstallation();

                for (Area area : installation.getTopology().getArea()) {
                    for (Line areaLine : area.getLine()) {
                        for (DeviceInstance deviceInstance : areaLine.getDeviceInstance()) {
                            ComObjectInstanceRefs comObjectInstanceRefs = deviceInstance.getComObjectInstanceRefs();
                            if (comObjectInstanceRefs != null) {
                                for (ComObjectInstanceRef comObjectInstanceRef : comObjectInstanceRefs
                                        .getComObjectInstanceRef()) {
                                    Connectors connectors = comObjectInstanceRef.getConnectors();
                                    if (connectors != null) {
                                        for (JAXBElement<GroupAddressReference> groupAddressReference : connectors
                                                .getSendOrReceive()) {
                                            if (GACorMap.get(gaRefGAMap.get(
                                                    groupAddressReference.getValue().getGroupAddressRefId())) == null) {
                                                GACorMap.put(
                                                        gaRefGAMap.get(groupAddressReference.getValue()
                                                                .getGroupAddressRefId()),
                                                        new ArrayList<ComObjectRef>());
                                            }

                                            List<ComObjectRef> corList = GACorMap.get(gaRefGAMap
                                                    .get(groupAddressReference.getValue().getGroupAddressRefId()));
                                            corList.add(corRefCorMap.get(comObjectInstanceRef.getRefId()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (DeviceInstance device : deviceGAMap.keySet()) {
                Set<GroupAddress> set = deviceGAMap.get(device);

                for (GroupAddress ga : set) {
                    if (!GAdeviceMap.keySet().contains(ga)) {
                        GAdeviceMap.put(ga, new HashSet<DeviceInstance>());
                    }

                    GAdeviceMap.get(ga).add(device);
                }
            }

            for (String address : iaDeviceMap.keySet()) {
                DeviceInstance instance = iaDeviceMap.get(address);
                deviceIaMap.put(instance, address);
            }
        } catch (Exception e) {
            logger.error("An exception occurred while post processing the KNX project data : '{}'", e.getMessage(), e);
        }
    }

    @Override
    public Set<String> getIndividualAddresses() {
        return iaDeviceMap.keySet();
    }

    @Override
    public Set<String> getGroupAddresses(String individualAddress) {

        Set<String> gas = new HashSet<String>();

        DeviceInstance device = iaDeviceMap.get(individualAddress);
        if (device != null) {
            Set<GroupAddress> groupAddresses = deviceGAMap.get(device);
            for (GroupAddress groupAddress : groupAddresses) {
                gas.add(GAgaMap.get(groupAddress));
            }
        }

        return gas;
    }

    private String verifyDPT(GroupAddress GA, String dpt, String input) {

        Set<DeviceInstance> devices = GAdeviceMap.get(GA);
        String result = dpt;

        for (DeviceInstance device : devices) {
            Map<String, String> properties = this.getDeviceProperties(deviceIaMap.get(device));
            result = KNXDPTException.transform(result, properties.get(KNXBindingConstants.MANUFACTURER_NAME),
                    properties.get(KNXBindingConstants.MANUFACTURER_HARDWARE_TYPE), input);

        }

        return result;
    }

    private String verifyDPTs(GroupAddress GA, Set<String> dpts, String input) {

        Set<DeviceInstance> devices = GAdeviceMap.get(GA);
        String result = null;

        for (DeviceInstance device : devices) {
            Map<String, String> properties = this.getDeviceProperties(deviceIaMap.get(device));
            result = KNXDPTException.transform(dpts, properties.get(KNXBindingConstants.MANUFACTURER_NAME),
                    properties.get(KNXBindingConstants.MANUFACTURER_HARDWARE_TYPE), input);

        }

        if (result == null && dpts.size() > 0) {
            return (String) dpts.toArray()[0];
        }

        return result;
    }

    @Override
    public String getDPT(String groupAddress) {

        String dpt = null;

        String corObjectSize = "";
        String corFunctionText = "";
        String corName = "";
        ArrayList<String> corTranslation = new ArrayList<>();
        Set<KNXDPTEvaluation> corMatches = new HashSet<KNXDPTEvaluation>();
        Set<KNXDPTRule> corRules = new HashSet<KNXDPTRule>();
        Set<KNXDPTEvaluation> corAttributeMatches = new HashSet<KNXDPTEvaluation>();
        Set<KNXDPTRule> corAttributeRules = new HashSet<KNXDPTRule>();

        String coObjectSize = "";
        String coFunctionText = "";
        String coName = "";
        ArrayList<String> coTranslation = new ArrayList<>();
        Set<KNXDPTEvaluation> coMatches = new HashSet<KNXDPTEvaluation>();
        Set<KNXDPTRule> coRules = new HashSet<KNXDPTRule>();
        Set<KNXDPTEvaluation> coAttributeMatches = new HashSet<KNXDPTEvaluation>();
        Set<KNXDPTRule> coAttributeRules = new HashSet<KNXDPTRule>();

        Set<String> dpts = new HashSet<String>();

        try {
            GroupAddress GA = gaGAMap.get(groupAddress);
            List<ComObjectRef> corList = GACorMap.get(GA);

            for (ComObjectRef coR : corList) {

                dpt = convertDPT(GA.getDatapointType());
                dpt = dpt == null ? convertDPT(coR.getDatapointType()) : dpt;
                dpt = dpt == null ? convertDPT(CorCoMap.get(coR).getDatapointType()) : dpt;

                // Check first on translations of the ComObject Reference itself, incl the object size

                corObjectSize = coR.getObjectSize();
                if (corObjectSize != null) {
                    corMatches.addAll(KNXDPTEvaluation.getMatchingEvaluations(corObjectSize));
                    dpt = (dpt != null) ? verifyDPT(GA, dpt, corObjectSize) : null;
                }

                TranslationElement anElement = this.corTranslationMap.get(coR);

                if (anElement != null) {
                    for (Translation translation : anElement.getTranslation()) {
                        corTranslation.add(translation.getText());
                        corMatches.addAll(KNXDPTEvaluation.getMatchingEvaluations(translation.getText()));
                        dpt = (dpt != null) ? verifyDPT(GA, dpt, translation.getText()) : null;

                    }
                }

                if (dpt == null) {
                    if (corMatches.size() == 0) {
                        // no matches
                    } else if (corMatches.size() == 1) {
                        for (KNXDPTEvaluation theMatch : corMatches) {
                            dpt = theMatch.getDPT();
                        }
                    } else {
                        KNXDPTEvaluation decision = KNXDPTRule.evaluateAll(corMatches);
                        if (decision != null) {
                            dpt = decision.getDPT();
                        } else {
                            corRules = KNXDPTRule.getMatchingRules(corMatches);
                        }
                    }
                }

                // Check the remaining attributes of the ComObjectReference itself

                corFunctionText = coR.getFunctionText();
                if (corFunctionText != null) {
                    corAttributeMatches.addAll(KNXDPTEvaluation.getMatchingEvaluations(corFunctionText));
                    dpt = (dpt != null) ? verifyDPT(GA, dpt, corFunctionText) : null;

                }

                corName = coR.getName();
                if (corName != null) {
                    corAttributeMatches.addAll(KNXDPTEvaluation.getMatchingEvaluations(corName));
                    dpt = (dpt != null) ? verifyDPT(GA, dpt, corName) : null;
                }

                if (dpt == null) {
                    if (corAttributeMatches.size() == 0) {
                        // no matches
                    } else if (corAttributeMatches.size() == 1) {
                        for (KNXDPTEvaluation theMatch : corAttributeMatches) {
                            dpt = theMatch.getDPT();
                        }
                    } else {
                        KNXDPTEvaluation decision = KNXDPTRule.evaluateAll(corAttributeMatches);
                        if (decision != null) {
                            dpt = decision.getDPT();
                        } else {
                            corAttributeRules = KNXDPTRule.getMatchingRules(corAttributeMatches);
                        }
                    }
                }

                // Check then on translation of the ComObject, incl the object size

                coObjectSize = CorCoMap.get(coR).getObjectSize();
                if (coObjectSize != null) {
                    coMatches.addAll(KNXDPTEvaluation.getMatchingEvaluations(coObjectSize));
                    dpt = (dpt != null) ? verifyDPT(GA, dpt, coObjectSize) : null;

                }

                anElement = this.coTranslationMap.get(CorCoMap.get(coR));

                if (anElement != null) {
                    for (Translation translation : anElement.getTranslation()) {
                        coTranslation.add(translation.getText());
                        coMatches.addAll(KNXDPTEvaluation.getMatchingEvaluations(translation.getText()));
                        dpt = (dpt != null) ? verifyDPT(GA, dpt, translation.getText()) : null;
                    }
                }

                if (dpt == null) {
                    if (coMatches.size() == 0) {
                        // no matches
                    } else if (coMatches.size() == 1) {
                        for (KNXDPTEvaluation theMatch : coMatches) {
                            dpt = theMatch.getDPT();
                        }
                    } else {
                        KNXDPTEvaluation decision = KNXDPTRule.evaluateAll(coMatches);

                        if (decision != null) {
                            dpt = decision.getDPT();
                        } else {
                            coRules = KNXDPTRule.getMatchingRules(coMatches);
                        }
                    }
                }

                // Check the remaining attributes of the ComObject

                coFunctionText = CorCoMap.get(coR).getFunctionText();
                if (coFunctionText != null) {
                    coAttributeMatches.addAll(KNXDPTEvaluation.getMatchingEvaluations(coFunctionText));
                    dpt = (dpt != null) ? verifyDPT(GA, dpt, coFunctionText) : null;
                }

                coName = CorCoMap.get(coR).getName();
                if (coName != null) {
                    coAttributeMatches.addAll(KNXDPTEvaluation.getMatchingEvaluations(coName));
                    dpt = (dpt != null) ? verifyDPT(GA, dpt, coName) : null;
                }

                if (dpt == null) {
                    if (coAttributeMatches.size() == 0) {
                        // no matches
                    } else if (coAttributeMatches.size() == 1) {
                        for (KNXDPTEvaluation theMatch : coAttributeMatches) {
                            dpt = theMatch.getDPT();
                        }
                    } else {
                        KNXDPTEvaluation decision = KNXDPTRule.evaluateAll(coAttributeMatches);
                        if (decision != null) {
                            dpt = decision.getDPT();
                        } else {
                            coAttributeRules = KNXDPTRule.getMatchingRules(coAttributeMatches);
                        }
                    }
                }

                if (dpt == null) {
                    // Write extensive information to the logs to make the process/job to extend the KNXDPTRule and
                    // KNXDPTEvaluation Enum easier
                    logger.warn("Could not find the DPT for Group Address {}", groupAddress);
                    logger.warn(
                            "The CommunicationObjectReference size '{}' and translations '{}' matched '{}' which were positively evaluated by rules '{}'",
                            corObjectSize, corTranslation, corMatches, corRules);
                    logger.warn(
                            "The CommunicationObjectReference function text '{}' and name '{}' matched '{}' which were positively evaluated by rules '{}'",
                            corFunctionText, corName, corAttributeMatches, corAttributeRules);
                    logger.warn(
                            "The CommunicationObject size '{}' and translations '{}' matched '{}' which were positively evaluated by rules '{}'",
                            coObjectSize, coTranslation, coMatches, coRules);
                    logger.warn(
                            "The CommunicationObject function text '{}' and name '{}' matched '{}' which were positively evaluated by rules '{}'",
                            coFunctionText, coName, coAttributeMatches, coAttributeRules);
                } else {
                    dpts.add(dpt);
                }
            }

            if (dpts.size() > 1) {
                dpt = verifyDPTs(GA, dpts, null);
                logger.warn("Multiple DPT are found for Group Address {} : {}. We settle for {}", groupAddress, dpts,
                        dpt);
            }

        } catch (Exception e) {
            logger.trace("An exception occurred while evaluating a DPT : '{}'", e.getMessage(), e);
        }

        return dpt;
    }

    @Override
    public Configuration getGroupAddressConfiguration(String groupAddress, String individualAddress) {

        Configuration configuration = new Configuration();
        DeviceInstance deviceInstance = iaDeviceMap.get(individualAddress);
        String corPrefix = iaCorprefixMap.get(individualAddress);

        if (groupAddress != null && deviceInstance != null && corPrefix != null) {
            GroupAddress gA = this.gaGAMap.get(groupAddress);
            if (gA != null) {
                configuration.put(KNXBindingConstants.DESCRIPTION, gA.getName());
            }

            List<ComObjectRef> corList = GACorMap.get(gA);
            for (ComObjectRef cor : corList) {
                if (cor.getId().contains(corPrefix)) {
                    if (cor != null) {
                        if (cor.getReadFlag() != null) {
                            if (cor.getReadFlag().equals("Enabled")) {
                                configuration.put(KNXBindingConstants.READ, true);
                            } else if (cor.getReadFlag().equals("Disabled")) {
                                configuration.put(KNXBindingConstants.READ, false);
                            }
                        } else {
                            ComObject co = CorCoMap.get(cor);
                            if (co != null) {
                                if (co.getReadFlag() != null && co.getReadFlag().equals("Enabled")) {
                                    configuration.put(KNXBindingConstants.READ, true);
                                } else if (co.getReadFlag() != null && co.getReadFlag().equals("Disabled")) {
                                    configuration.put(KNXBindingConstants.READ, false);
                                }
                            }
                        }

                        if (cor.getWriteFlag() != null) {
                            if (cor.getWriteFlag().equals("Enabled")) {
                                configuration.put(KNXBindingConstants.WRITE, true);
                            } else if (cor.getWriteFlag().equals("Disabled")) {
                                configuration.put(KNXBindingConstants.WRITE, false);
                            }
                        } else {
                            ComObject co = CorCoMap.get(cor);
                            if (co != null) {
                                if (co.getWriteFlag() != null && co.getWriteFlag().equals("Enabled")) {
                                    configuration.put(KNXBindingConstants.WRITE, true);
                                } else if (co.getWriteFlag() != null && co.getWriteFlag().equals("Disabled")) {
                                    configuration.put(KNXBindingConstants.WRITE, false);
                                }
                            }
                        }

                        if (cor.getTransmitFlag() != null) {
                            if (cor.getTransmitFlag().equals("Enabled")) {
                                configuration.put(KNXBindingConstants.TRANSMIT, true);
                            } else if (cor.getTransmitFlag().equals("Disabled")) {
                                configuration.put(KNXBindingConstants.TRANSMIT, false);
                            }
                        } else {
                            ComObject co = CorCoMap.get(cor);
                            if (co != null) {
                                if (co.getTransmitFlag() != null && co.getTransmitFlag().equals("Enabled")) {
                                    configuration.put(KNXBindingConstants.TRANSMIT, true);
                                } else if (co.getTransmitFlag() != null && co.getTransmitFlag().equals("Disabled")) {
                                    configuration.put(KNXBindingConstants.TRANSMIT, false);
                                }
                            }
                        }

                        if (cor.getUpdateFlag() != null) {
                            if (cor.getUpdateFlag().equals("Enabled")) {
                                configuration.put(KNXBindingConstants.UPDATE, true);
                            } else if (cor.getUpdateFlag().equals("Disabled")) {
                                configuration.put(KNXBindingConstants.UPDATE, false);
                            }
                        } else {
                            ComObject co = CorCoMap.get(cor);
                            if (co != null) {
                                if (co.getUpdateFlag() != null && co.getUpdateFlag().equals("Enabled")) {
                                    configuration.put(KNXBindingConstants.UPDATE, true);
                                } else if (co.getUpdateFlag() != null && co.getUpdateFlag().equals("Disabled")) {
                                    configuration.put(KNXBindingConstants.UPDATE, false);
                                }
                            }
                        }
                    }
                }
            }
        }

        return configuration;
    }

    @Override
    public Configuration getDeviceConfiguration(String device) {
        Configuration configuration = new Configuration();

        // Just do a quick dummy check to see if a device with the given "device" address exists
        if (device != null) {
            DeviceInstance deviceInstance = iaDeviceMap.get(device);
            if (deviceInstance != null) {
                configuration.put(KNXBindingConstants.ADDRESS, device);
            }
        }

        return configuration;
    }

    @Override
    public Map<String, String> getDeviceProperties(String device) {
        Map<String, String> properties = new HashMap<String, String>();

        if (device != null) {
            DeviceInstance deviceInstance = iaDeviceMap.get(device);
            Product product = productMap.get(deviceInstance.getProductRefId());
            if (product != null) {

                String manufacturerId = StringUtils.substringBefore(product.getId(), "_");

                List<Manufacturer> manufacturers = knxMaster.getMasterData().getManufacturers().getManufacturer();
                for (Manufacturer manufacturer : manufacturers) {
                    if (manufacturer.getId().equals(manufacturerId)) {
                        properties.put(KNXBindingConstants.MANUFACTURER_NAME, manufacturer.getName());
                        break;
                    }
                }

                TranslationElement element = productTranslationMap.get(product);
                if (element != null) {
                    for (Translation translation : element.getTranslation()) {
                        if (translation.getAttributeName().equals("Text")) {
                            properties.put(KNXBindingConstants.MANUFACTURER_HARDWARE_TYPE, translation.getText());
                        }
                    }
                } else {
                    properties.put(KNXBindingConstants.MANUFACTURER_HARDWARE_TYPE, product.getText());
                }
            }
        }

        return properties;
    }
}
