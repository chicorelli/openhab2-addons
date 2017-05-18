/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 * This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
 * See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
 * Any modifications to this file will be lost upon recompilation of the source schema. 
 * Generated on: 2017.03.09 at 08:34:29 PM CET 
 */

package org.openhab.binding.knx.internal.parser.knxproj13;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Hardware2Programs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Hardware2Programs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Hardware2Program" type="{http://knx.org/xml/project/13}Hardware2Program" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Hardware2Programs", propOrder = {
    "hardware2Program"
})
public class Hardware2Programs {

    @XmlElement(name = "Hardware2Program")
    protected List<Hardware2Program> hardware2Program;

    /**
     * Gets the value of the hardware2Program property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hardware2Program property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHardware2Program().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Hardware2Program }
     * 
     * 
     */
    public List<Hardware2Program> getHardware2Program() {
        if (hardware2Program == null) {
            hardware2Program = new ArrayList<Hardware2Program>();
        }
        return this.hardware2Program;
    }

}
