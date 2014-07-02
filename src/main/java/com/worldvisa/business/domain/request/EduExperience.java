/******************************************************************************
 * Name : EduExperience.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for EduExperience
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;
import com.worldvisa.business.infra.utils.BasicUtils;

/**
 * POJO implementation for EduExperience.
 */
@DataTransferObject(converter = BeanConverter.class)
public class EduExperience
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private Long              degree;
    private String            discipline;
    private java.util.Date    fromDate;
    private String            reportId;
    private Long              seqNum;
    private boolean           tillDate;
    private java.util.Date    toDate;
    private String            university;

    /**
     * Instantiates EduExperience with defaults
     */
    public EduExperience() {
        super();
    }

    /**
     * Instantiates EduExperience with primary key combination
     */
    public EduExperience(final String reportId, final Long seqNum) {
        super();
        this.reportId = reportId;
        this.seqNum = seqNum;
    }

    /**
     * Getter method for degree
     * @return degree
     */
    public Long getDegree() {
        return this.degree;
    }

    /**
     * Getter method for discipline
     * @return discipline
     */
    public String getDiscipline() {
        return this.discipline;
    }

    /**
     * Getter method for fromDate
     * @return fromDate
     */
    public java.util.Date getFromDate() {
        return this.fromDate;
    }

    /**
     * Getter method for reportId
     * @return reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * Getter method for seqNum
     * @return seqNum
     */
    public Long getSeqNum() {
        return this.seqNum;
    }

    /**
     * Getter method for toDate
     * @return toDate
     */
    public java.util.Date getToDate() {
        if (this.tillDate) {
            return null;
        } else {
            return this.toDate;
        }
    }

    /**
     * Getter method for university
     * @return university
     */
    public String getUniversity() {
        return this.university;
    }

    /**
     * @return the tillDate
     */
    public boolean isTillDate() {
        return this.tillDate;
    }

    /**
     * Setter method for degree
     * @param degree Value for degree
     */
    public void setDegree(final Long degree) {
        this.degree = degree;
    }

    /**
     * Setter method for discipline
     * @param discipline Value for discipline
     */
    public void setDiscipline(final String discipline) {
        this.discipline = discipline;
    }

    /**
     * Setter method for fromDate
     * @param fromDate Value for fromDate
     */
    public void setFromDate(final java.util.Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Setter method for reportId
     * @param reportId Value for reportId
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }

    /**
     * Setter method for seqNum
     * @param seqNum Value for seqNum
     */
    public void setSeqNum(final Long seqNum) {
        this.seqNum = seqNum;
    }

    /**
     * @param tillDate the tillDate to set
     */
    public void setTillDate(final boolean tillDate) {
        if (tillDate) {
            this.toDate = null;
        } else if (this.toDate == null) {
            this.toDate = BasicUtils.getCurrentDateTime();
        }
        this.tillDate = tillDate;
    }

    /**
     * Setter method for toDate
     * @param toDate Value for toDate
     */
    public void setToDate(final java.util.Date toDate) {
        this.toDate = toDate;
        if (this.toDate == null) {
            this.tillDate = true;
        }
    }

    /**
     * Setter method for university
     * @param university Value for university
     */
    public void setUniversity(final String university) {
        this.university = university;
    }
}
