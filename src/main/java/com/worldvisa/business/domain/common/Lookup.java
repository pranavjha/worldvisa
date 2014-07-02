/******************************************************************************
 * Name : LookupConstants.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for LookupConstants
 *****************************************************************************/
package com.worldvisa.business.domain.common;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for LookupConstants.
 */
@DataTransferObject(converter = BeanConverter.class)
public class Lookup
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            lookupDescription;
    private Long              lookupId;
    private Long              parentLookupId;
    private Long              seqNum;

    /**
     * Instantiates LookupConstants with defaults
     */
    public Lookup() {
        super();
    }

    /**
     * Instantiates LookupConstants with primary key combination
     */
    public Lookup(final Long lookupId) {
        super();
        this.lookupId = lookupId;
    }

    /**
     * Getter method for lookupDescription
     * @return lookupDescription
     */
    public String getLookupDescription() {
        return this.lookupDescription;
    }

    /**
     * Getter method for lookupId
     * @return lookupId
     */
    public Long getLookupId() {
        return this.lookupId;
    }

    /**
     * Getter method for parentLookupId
     * @return parentLookupId
     */
    public Long getParentLookupId() {
        return this.parentLookupId;
    }

    /**
     * Getter method for seqNum
     * @return seqNum
     */
    public Long getSeqNum() {
        return this.seqNum;
    }

    /**
     * Setter method for lookupDescription
     * @param lookupDescription Value for lookupDescription
     */
    public void setLookupDescription(final String lookupDescription) {
        this.lookupDescription = lookupDescription;
    }

    /**
     * Setter method for lookupId
     * @param lookupId Value for lookupId
     */
    public void setLookupId(final Long lookupId) {
        this.lookupId = lookupId;
    }

    /**
     * Setter method for parentLookupId
     * @param parentLookupId Value for parentLookupId
     */
    public void setParentLookupId(final Long parentLookupId) {
        this.parentLookupId = parentLookupId;
    }

    /**
     * Setter method for seqNum
     * @param seqNum Value for seqNum
     */
    public void setSeqNum(final Long seqNum) {
        this.seqNum = seqNum;
    }
}
