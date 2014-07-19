/******************************************************************************
 * Name : LangProfeciency.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for LangProfeciency
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for LangProfeciency.
 */
@DataTransferObject(converter = BeanConverter.class)
public class LangProfeciency
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private Long              languageId;
    private Long              listeningLevel;
    private Long              readingLevel;
    private String            reportId;
    private Long              seqNum;
    private Long              speakingLevel;
    private Long              writingLevel;

    /**
     * Instantiates LangProfeciency with defaults
     */
    public LangProfeciency() {
        super();
    }

    /**
     * Instantiates LangProfeciency with primary key combination
     */
    public LangProfeciency(final String reportId, final Long seqNum) {
        super();
        this.reportId = reportId;
        this.seqNum = seqNum;
    }

    /**
     * Getter method for languageId
     * @return languageId
     */
    public Long getLanguageId() {
        return this.languageId;
    }

    /**
     * @return the listeningLevel
     */
    public Long getListeningLevel() {
        return this.listeningLevel;
    }

    /**
     * Getter method for readingLevel
     * @return readingLevel
     */
    public Long getReadingLevel() {
        return this.readingLevel;
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
     * Getter method for speakingLevel
     * @return speakingLevel
     */
    public Long getSpeakingLevel() {
        return this.speakingLevel;
    }

    /**
     * Getter method for writingLevel
     * @return writingLevel
     */
    public Long getWritingLevel() {
        return this.writingLevel;
    }

    /**
     * Setter method for languageId
     * @param languageId Value for languageId
     */
    public void setLanguageId(final Long languageId) {
        this.languageId = languageId;
    }

    /**
     * @param listeningLevel the listeningLevel to set
     */
    public void setListeningLevel(final Long listeningLevel) {
        this.listeningLevel = listeningLevel;
    }

    /**
     * Setter method for readingLevel
     * @param readingLevel Value for readingLevel
     */
    public void setReadingLevel(final Long readingLevel) {
        this.readingLevel = readingLevel;
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
     * Setter method for speakingLevel
     * @param speakingLevel Value for speakingLevel
     */
    public void setSpeakingLevel(final Long speakingLevel) {
        this.speakingLevel = speakingLevel;
    }

    /**
     * Setter method for writingLevel
     * @param writingLevel Value for writingLevel
     */
    public void setWritingLevel(final Long writingLevel) {
        this.writingLevel = writingLevel;
    }
}
