/******************************************************************************
 * Name : TestScore.java Author : Administrator Date : 2010/05/16 Description : POJO implementation for TestScore
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for TestScore.
 */
@DataTransferObject(converter = BeanConverter.class)
public class TestScore
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            reportId;
    private String            score;
    private Long              seqNum;
    private java.util.Date    testDate;
    private Long              testId;

    /**
     * Instantiates TestScore with defaults
     */
    public TestScore() {
        super();
    }

    /**
     * Instantiates TestScore with primary key combination
     */
    public TestScore(final String reportId, final Long seqNum) {
        super();
        this.reportId = reportId;
        this.seqNum = seqNum;
    }

    /**
     * Getter method for reportId
     * @return reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * Getter method for score
     * @return score
     */
    public String getScore() {
        return this.score;
    }

    /**
     * Getter method for seqNum
     * @return seqNum
     */
    public Long getSeqNum() {
        return this.seqNum;
    }

    /**
     * Getter method for testDate
     * @return testDate
     */
    public java.util.Date getTestDate() {
        return this.testDate;
    }

    /**
     * Getter method for testId
     * @return testId
     */
    public Long getTestId() {
        return this.testId;
    }

    /**
     * Setter method for reportId
     * @param reportId Value for reportId
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }

    /**
     * Setter method for score
     * @param score Value for score
     */
    public void setScore(final String score) {
        this.score = score;
    }

    /**
     * Setter method for seqNum
     * @param seqNum Value for seqNum
     */
    public void setSeqNum(final Long seqNum) {
        this.seqNum = seqNum;
    }

    /**
     * Setter method for testDate
     * @param testDate Value for testDate
     */
    public void setTestDate(final java.util.Date testDate) {
        this.testDate = testDate;
    }

    /**
     * Setter method for testId
     * @param testId Value for testId
     */
    public void setTestId(final Long testId) {
        this.testId = testId;
    }
}
