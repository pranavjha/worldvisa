/******************************************************************************
 * Name : UserDetails.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for UserDetails
 *****************************************************************************/
package com.worldvisa.business.domain.market;

import java.util.Date;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for UserDetails.
 */
@DataTransferObject(converter = BeanConverter.class)
public class User
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            addedBy;
    private Date              addedOn;
    private Long              age;
    private String            dateOfBirth;
    private String            desCollege;
    private String            desCountries;
    private String            desCourse;
    private String            email;
    private String            gender;
    private String            mobile;
    private String            name;
    private String            qualCompleted;
    private String            requestNumber;
    private String            specialization;
    private String            telephoneNo;
    private UserGroup         userGroup;
    private Long              userGroupId;
    private Long              userId;

    public User() {
        // default constructor - do nothing here
    }

    public User(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the addedBy
     */
    public String getAddedBy() {
        return this.addedBy;
    }

    /**
     * @return the addedOn
     */
    public Date getAddedOn() {
        return this.addedOn;
    }

    /**
     * @return the age
     */
    public Long getAge() {
        return this.age;
    }

    /**
     * @return the dateOfBirth
     */
    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * @return the desCollege
     */
    public String getDesCollege() {
        return this.desCollege;
    }

    /**
     * @return the desCountries
     */
    public String getDesCountries() {
        return this.desCountries;
    }

    /**
     * @return the desCourse
     */
    public String getDesCourse() {
        return this.desCourse;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the qualCompleted
     */
    public String getQualCompleted() {
        return this.qualCompleted;
    }

    /**
     * @return the requestNumber
     */
    public String getRequestNumber() {
        return this.requestNumber;
    }

    /**
     * @return the specialization
     */
    public String getSpecialization() {
        return this.specialization;
    }

    /**
     * @return the telephoneNo
     */
    public String getTelephoneNo() {
        return this.telephoneNo;
    }

    /**
     * @return the userGroup
     */
    public UserGroup getUserGroup() {
        return this.userGroup;
    }

    /**
     * @return the userGroupId
     */
    public Long getUserGroupId() {
        return this.userGroupId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return this.userId;
    }

    /**
     * @param addedBy the addedBy to set
     */
    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    /**
     * @param addedOn the addedOn to set
     */
    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Long age) {
        this.age = age;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @param desCollege the desCollege to set
     */
    public void setDesCollege(String desCollege) {
        this.desCollege = desCollege;
    }

    /**
     * @param desCountries the desCountries to set
     */
    public void setDesCountries(String desCountries) {
        this.desCountries = desCountries;
    }

    /**
     * @param desCourse the desCourse to set
     */
    public void setDesCourse(String desCourse) {
        this.desCourse = desCourse;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param qualCompleted the qualCompleted to set
     */
    public void setQualCompleted(String qualCompleted) {
        this.qualCompleted = qualCompleted;
    }

    /**
     * @param requestNumber the requestNumber to set
     */
    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    /**
     * @param specialization the specialization to set
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * @param telephoneNo the telephoneNo to set
     */
    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    /**
     * @param userGroup the userGroup to set
     */
    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * @param userGroupId the userGroupId to set
     */
    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}