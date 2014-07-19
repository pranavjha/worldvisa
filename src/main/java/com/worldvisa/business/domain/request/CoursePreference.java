/******************************************************************************
 * Name : CoursePreference.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for CoursePreference
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for CoursePreference.
 */
@DataTransferObject(converter = BeanConverter.class)
public class CoursePreference
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            courseDetail;
    private String            institutionChoice;
    private String            reportId;
    private Long              seqNum;

    /**
     * Instantiates CoursePreference with defaults
     */
    public CoursePreference() {
        super();
    }

    /**
     * Instantiates CoursePreference with primary key combination
     */
    public CoursePreference(final String reportId, final Long seqNum) {
        super();
        this.reportId = reportId;
        this.seqNum = seqNum;
    }

    /**
     * Getter method for courseDetail
     * @return courseDetail
     */
    public String getCourseDetail() {
        return this.courseDetail;
    }

    /**
     * Getter method for institutionChoice
     * @return institutionChoice
     */
    public String getInstitutionChoice() {
        return this.institutionChoice;
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
     * Setter method for courseDetail
     * @param courseDetail Value for courseDetail
     */
    public void setCourseDetail(final String courseDetail) {
        this.courseDetail = courseDetail;
    }

    /**
     * Setter method for institutionChoice
     * @param institutionChoice Value for institutionChoice
     */
    public void setInstitutionChoice(final String institutionChoice) {
        this.institutionChoice = institutionChoice;
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
