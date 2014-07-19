/******************************************************************************
 * Name : UserDetails.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for UserDetails
 *****************************************************************************/
package com.worldvisa.business.domain.profile;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;

/**
 * POJO implementation for UserDetails.
 */
@DataTransferObject(converter = BeanConverter.class)
public class UserDetails
extends LoginDetails {
    private static final long serialVersionUID = 1L;
    private String            cellphone;
    private String            city;
    private String            country;
    private java.util.Date    dateOfBirth;
    private String            landline;
    private String            mailingAddress;
    private Long              maritalStatus;
    private String            name;
    private String            salutation;
    private Long              seqNum;
    private String            state;

    /**
     * Instantiates UserDetails with defaults
     */
    public UserDetails() {
        super();
    }

    /**
     * Instantiates UserDetails with primary key combination
     */
    public UserDetails(final Long seqNum, final String emailId) {
        super();
        this.seqNum = seqNum;
        this.setEmailId(emailId);
    }

    /**
     * Getter method for cellphone
     * @return cellphone
     */
    public String getCellphone() {
        return this.cellphone;
    }

    /**
     * Getter method for city
     * @return city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Getter method for country
     * @return country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Getter method for dateOfBirth
     * @return dateOfBirth
     */
    public java.util.Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * Getter method for landline
     * @return landline
     */
    public String getLandline() {
        return this.landline;
    }

    /**
     * Getter method for mailingAddress
     * @return mailingAddress
     */
    public String getMailingAddress() {
        return this.mailingAddress;
    }

    /**
     * Getter method for maritalStatus
     * @return maritalStatus
     */
    public Long getMaritalStatus() {
        return this.maritalStatus;
    }

    /**
     * Getter method for name
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method for salutation
     * @return salutation
     */
    public String getSalutation() {
        return this.salutation;
    }

    /**
     * @return the seqNum
     */
    public Long getSeqNum() {
        return this.seqNum;
    }

    /**
     * Getter method for state
     * @return state
     */
    public String getState() {
        return this.state;
    }

    /**
     * Setter method for cellphone
     * @param cellphone Value for cellphone
     */
    public void setCellphone(final String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * Setter method for city
     * @param city Value for city
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * Setter method for country
     * @param country Value for country
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * Setter method for dateOfBirth
     * @param dateOfBirth Value for dateOfBirth
     */
    public void setDateOfBirth(final java.util.Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Setter method for landline
     * @param landline Value for landline
     */
    public void setLandline(final String landline) {
        this.landline = landline;
    }

    /**
     * Setter method for mailingAddress
     * @param mailingAddress Value for mailingAddress
     */
    public void setMailingAddress(final String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    /**
     * Setter method for maritalStatus
     * @param maritalStatus Value for maritalStatus
     */
    public void setMaritalStatus(final Long maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * Setter method for name
     * @param name Value for name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Setter method for salutation
     * @param salutation Value for salutation
     */
    public void setSalutation(final String salutation) {
        this.salutation = salutation;
    }

    /**
     * @param seqNum the seqNum to set
     */
    public void setSeqNum(final Long seqNum) {
        this.seqNum = seqNum;
    }

    /**
     * Setter method for state
     * @param state Value for state
     */
    public void setState(final String state) {
        this.state = state;
    }
}
