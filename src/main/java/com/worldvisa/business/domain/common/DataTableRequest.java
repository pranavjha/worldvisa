package com.worldvisa.business.domain.common;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

@DataTransferObject(converter = BeanConverter.class)
public class DataTableRequest
extends BaseBean {
    private static final long serialVersionUID = 5021226699328604572L;
    private int               numRows;
    private String            sortCol;
    private String            sortOrder;
    private int               startRow;

    /**
     * @return the endRow
     */
    public int getEndRow() {
        return this.startRow + this.numRows;
    }

    /**
     * @return the numRows
     */
    public int getNumRows() {
        return this.numRows;
    }

    /**
     * @return the sortCol
     */
    public String getSortCol() {
        return this.sortCol;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder() {
        return this.sortOrder;
    }

    /**
     * @return the startRow
     */
    public int getStartRow() {
        return this.startRow;
    }

    /**
     * @param numRows the numRows to set
     */
    public void setNumRows(final int numRows) {
        this.numRows = numRows;
    }

    /**
     * @param sortCol the sortCol to set
     */
    public void setSortCol(final String sortCol) {
        this.sortCol = sortCol;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(final String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @param startRow the startRow to set
     */
    public void setStartRow(final int startRow) {
        this.startRow = startRow;
    }

    /**
     * Initializes the DataTableRequest to search inside java code
     * @return this object
     */
    public DataTableRequest setupDefaults() {
        this.startRow = 0;
        this.numRows = 10000;
        this.sortCol = "1";
        this.sortOrder = "ASC";
        return this;
    }
}
