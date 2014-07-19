/******************************************************************************
 * Name : RelativesAbroad.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for RelativesAbroad
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for RelativesAbroad.
 */
@DataTransferObject(converter = BeanConverter.class)
public class RelativesAbroad
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            cityCountry;
    private Long              relation;
    private String            relativeName;
    private String            reportId;
    private Long              seqNum;

    /**
     * Instantiates RelativesAbroad with defaults
     */
    public RelativesAbroad() {
        super();
    }

    /**
     * Instantiates RelativesAbroad with primary key combination
     */
    public RelativesAbroad(final String reportId, final Long seqNum) {
        super();
        this.reportId = reportId;
        this.seqNum = seqNum;
    }

    /**
     * Getter method for cityCountry
     * @return cityCountry
     */
    public String getCityCountry() {
        return this.cityCountry;
    }

    /**
     * Getter method for relation
     * @return relation
     */
    public Long getRelation() {
        return this.relation;
    }

    /**
     * Getter method for relativeName
     * @return relativeName
     */
    public String getRelativeName() {
        return this.relativeName;
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
     * Setter method for cityCountry
     * @param cityCountry Value for cityCountry
     */
    public void setCityCountry(final String cityCountry) {
        this.cityCountry = cityCountry;
    }

    /**
     * Setter method for relation
     * @param relation Value for relation
     */
    public void setRelation(final Long relation) {
        this.relation = relation;
    }

    /**
     * Setter method for relativeName
     * @param relativeName Value for relativeName
     */
    public void setRelativeName(final String relativeName) {
        this.relativeName = relativeName;
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
}
