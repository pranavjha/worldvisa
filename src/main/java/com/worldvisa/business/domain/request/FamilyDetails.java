/******************************************************************************
 * Name : FamilyDetails.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for FamilyDetails
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import java.util.Date;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for FamilyDetails.
 */
@DataTransferObject(converter = BeanConverter.class)
public class FamilyDetails
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private Date              dateOfBirth;
    private String            highestDegree;
    private Long              relation;
    private String            relativeName;
    private String            reportId;
    private Long              seqNum;
    private Double            workExperience;

    /**
     * Instantiates FamilyDetails with defaults
     */
    public FamilyDetails() {
        super();
    }

    /**
     * Instantiates FamilyDetails with primary key combination
     */
    public FamilyDetails(final String reportId, final Long seqNum) {
        super();
        this.reportId = reportId;
        this.seqNum = seqNum;
    }

    /**
     * @return the dateOfBirth
     */
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * @return the highestDegree
     */
    public String getHighestDegree() {
        return this.highestDegree;
    }

    /**
     * @return the relation
     */
    public Long getRelation() {
        return this.relation;
    }

    /**
     * @return the relativeName
     */
    public String getRelativeName() {
        return this.relativeName;
    }

    /**
     * @return the reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * @return the seqNum
     */
    public Long getSeqNum() {
        return this.seqNum;
    }

    /**
     * @return the workExperience
     */
    public Double getWorkExperience() {
        return this.workExperience;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(final Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @param highestDegree the highestDegree to set
     */
    public void setHighestDegree(final String highestDegree) {
        this.highestDegree = highestDegree;
    }

    /**
     * @param relation the relation to set
     */
    public void setRelation(final Long relation) {
        this.relation = relation;
    }

    /**
     * @param relativeName the relativeName to set
     */
    public void setRelativeName(final String relativeName) {
        this.relativeName = relativeName;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }

    /**
     * @param seqNum the seqNum to set
     */
    public void setSeqNum(final Long seqNum) {
        this.seqNum = seqNum;
    }

    /**
     * @param workExperience the workExperience to set
     */
    public void setWorkExperience(final Double workExperience) {
        this.workExperience = workExperience;
    }
}
