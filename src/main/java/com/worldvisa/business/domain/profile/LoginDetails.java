/******************************************************************************
 * Name : LoginDetails.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for LoginDetails
 *****************************************************************************/
package com.worldvisa.business.domain.profile;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for LoginDetails.
 */
@DataTransferObject(converter = BeanConverter.class)
public class LoginDetails
extends BaseBean {
    private static final long serialVersionUID = 1L;

    public static String autoGeneratePassword() {
        return "" + (int) (Math.random() * 100000);
    }

    private String         createdBy;
    private java.util.Date createdDate;
    private String         emailId;
    private Long           isActive;
    private String         password;
    private Long           passwordExpFlag;
    private Long           registrationStatus;
    private Long           userRole;

    /**
     * Instantiates LoginDetails with defaults
     */
    public LoginDetails() {
        super();
    }

    public LoginDetails(final LoginDetails details) {
        this.emailId = details.emailId;
        this.userRole = details.userRole;
        this.registrationStatus = details.registrationStatus;
        this.password = details.password;
        this.passwordExpFlag = details.passwordExpFlag;
        this.createdDate = details.createdDate;
        this.createdBy = details.createdBy;
        this.isActive = details.isActive;
    }

    /**
     * Instantiates LoginDetails with primary key combination
     */
    public LoginDetails(final String emailId) {
        super();
        this.emailId = emailId;
    }

    /**
     * Getter method for createdBy
     * @return createdBy
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Getter method for createdDate
     * @return createdDate
     */
    public java.util.Date getCreatedDate() {
        return this.createdDate;
    }

    /**
     * Getter method for emailId
     * @return emailId
     */
    public String getEmailId() {
        return this.emailId;
    }

    /**
     * Getter method for isActive
     * @return isActive
     */
    public Long getIsActive() {
        return this.isActive;
    }

    /**
     * Getter method for password
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Getter method for passwordExpFlag
     * @return passwordExpFlag
     */
    public Long getPasswordExpFlag() {
        return this.passwordExpFlag;
    }

    /**
     * Getter method for registrationStatus
     * @return registrationStatus
     */
    public Long getRegistrationStatus() {
        return this.registrationStatus;
    }

    /**
     * Getter method for userRole
     * @return userRole
     */
    public Long getUserRole() {
        return this.userRole;
    }

    /**
     * Setter method for createdBy
     * @param createdBy Value for createdBy
     */
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Setter method for createdDate
     * @param createdDate Value for createdDate
     */
    public void setCreatedDate(final java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Setter method for emailId
     * @param emailId Value for emailId
     */
    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    /**
     * Setter method for isActive
     * @param isActive Value for isActive
     */
    public void setIsActive(final Long isActive) {
        this.isActive = isActive;
    }

    /**
     * Setter method for password
     * @param password Value for password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Setter method for passwordExpFlag
     * @param passwordExpFlag Value for passwordExpFlag
     */
    public void setPasswordExpFlag(final Long passwordExpFlag) {
        this.passwordExpFlag = passwordExpFlag;
    }

    /**
     * Setter method for registrationStatus
     * @param registrationStatus Value for registrationStatus
     */
    public void setRegistrationStatus(final Long registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    /**
     * Setter method for userRole
     * @param userRole Value for userRole
     */
    public void setUserRole(final Long userRole) {
        this.userRole = userRole;
    }
}
