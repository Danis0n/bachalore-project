//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.01.04 at 07:35:10 PM MSK 
//


package ru.fp.coreservice.xjc;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Document"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FIToFICstmrCdtTrf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.11}FIToFICustomerCreditTransferV11"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "fiToFICstmrCdtTrf"
})
public class Document {

    @XmlElement(name = "FIToFICstmrCdtTrf", required = true)
    protected FIToFICustomerCreditTransferV11 fiToFICstmrCdtTrf;

    /**
     * Gets the value of the fiToFICstmrCdtTrf property.
     * 
     * @return
     *     possible object is
     *     {@link FIToFICustomerCreditTransferV11 }
     *     
     */
    public FIToFICustomerCreditTransferV11 getFIToFICstmrCdtTrf() {
        return fiToFICstmrCdtTrf;
    }

    /**
     * Sets the value of the fiToFICstmrCdtTrf property.
     * 
     * @param value
     *     allowed object is
     *     {@link FIToFICustomerCreditTransferV11 }
     *     
     */
    public void setFIToFICstmrCdtTrf(FIToFICustomerCreditTransferV11 value) {
        this.fiToFICstmrCdtTrf = value;
    }

}
