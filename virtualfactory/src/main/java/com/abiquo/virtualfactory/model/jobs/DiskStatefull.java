//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.18 at 12:55:00 PM CET 
//


package com.abiquo.virtualfactory.model.jobs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiskStatefull complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiskStatefull">
 *   &lt;complexContent>
 *     &lt;extension base="{http://abiquo.com/virtualfactory/model/jobs}DiskDesc">
 *       &lt;sequence>
 *         &lt;element name="iqn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiskStatefull", propOrder = {
    "iqn"
})
@XmlSeeAlso({
    AuxDisk.class
})
public class DiskStatefull
    extends DiskDesc
{

    @XmlElement(required = true)
    protected String iqn;

    /**
     * Gets the value of the iqn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIqn() {
        return iqn;
    }

    /**
     * Sets the value of the iqn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIqn(String value) {
        this.iqn = value;
    }

}