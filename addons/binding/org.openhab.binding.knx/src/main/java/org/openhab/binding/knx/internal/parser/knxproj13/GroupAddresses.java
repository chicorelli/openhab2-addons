//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.09 at 08:34:29 PM CET 
//


package org.openhab.binding.knx.internal.parser.knxproj13;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroupAddresses complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroupAddresses">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GroupRanges" type="{http://knx.org/xml/project/13}GroupRanges"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupAddresses", propOrder = {
    "groupRanges"
})
public class GroupAddresses {

    @XmlElement(name = "GroupRanges", required = true)
    protected GroupRanges groupRanges;

    /**
     * Gets the value of the groupRanges property.
     * 
     * @return
     *     possible object is
     *     {@link GroupRanges }
     *     
     */
    public GroupRanges getGroupRanges() {
        return groupRanges;
    }

    /**
     * Sets the value of the groupRanges property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupRanges }
     *     
     */
    public void setGroupRanges(GroupRanges value) {
        this.groupRanges = value;
    }

}