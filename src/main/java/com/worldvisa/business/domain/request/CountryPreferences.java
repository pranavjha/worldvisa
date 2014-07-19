/******************************************************************************
 * Name : CountryPreferences.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for CountryPreferences
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for CountryPreferences.
 */
@DataTransferObject(converter = BeanConverter.class)
public class CountryPreferences
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            country;
    private String            reportId;
    private Long              seqNum;

    /**
     * Instantiates CountryPreferences with defaults
     */
    public CountryPreferences() {
        super();
    }

    /**
     * Instantiates CountryPreferences with primary key combination
     */
    public CountryPreferences(final String reportId, final Long seqNum) {
        super();
        this.reportId = reportId;
        this.seqNum = seqNum;
    }

    /**
     * Getter method for country
     * @return country
     */
    public String getCountry() {
        return this.country;
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
     * Setter method for country
     * @param country Value for country
     */
    public void setCountry(final String country) {
        this.country = country;
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
