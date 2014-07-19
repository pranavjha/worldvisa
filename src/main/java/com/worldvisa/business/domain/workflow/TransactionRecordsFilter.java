/******************************************************************************
 * Name : TransactionRecordsFilter.java Author : Administrator Date : 17-May-2010,8:27:40 PM Description : Implementation for TransactionRecordsFilter
 *****************************************************************************/
package com.worldvisa.business.domain.workflow;

import java.util.Date;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.common.DataTableRequest;

@DataTransferObject(converter = BeanConverter.class)
public class TransactionRecordsFilter
extends DataTableRequest {
    private static final long serialVersionUID = 7906863101222881196L;
    private Double            amountRangeFrom;
    private Double            amountRangeTo;
    private String            chequeDdNo;
    private String            customerEmail;
    private String            customerName;
    private String            employeeName;
    private String            paymentId;
    private Long              paymentMode;
    private List<Date>        transactionDates;

    /**
     * @return the amountRangeFrom
     */
    public Double getAmountRangeFrom() {
        return this.amountRangeFrom;
    }

    /**
     * @return the amountRangeTo
     */
    public Double getAmountRangeTo() {
        return this.amountRangeTo;
    }

    /**
     * @return the chequeDdNo
     */
    public String getChequeDdNo() {
        return this.chequeDdNo;
    }

    /**
     * @return the customerEmail
     */
    public String getCustomerEmail() {
        return this.customerEmail;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return this.employeeName;
    }

    /**
     * @return the paymentId
     */
    public String getPaymentId() {
        return this.paymentId;
    }

    /**
     * @return the paymentMode
     */
    public Long getPaymentMode() {
        return this.paymentMode;
    }

    /**
     * @return the transactionDates
     */
    public List<Date> getTransactionDates() {
        return this.transactionDates;
    }

    /**
     * @param amountRangeFrom the amountRangeFrom to set
     */
    public void setAmountRangeFrom(final Double amountRangeFrom) {
        this.amountRangeFrom = amountRangeFrom;
    }

    /**
     * @param amountRangeTo the amountRangeTo to set
     */
    public void setAmountRangeTo(final Double amountRangeTo) {
        this.amountRangeTo = amountRangeTo;
    }

    /**
     * @param chequeDdNo the chequeDdNo to set
     */
    public void setChequeDdNo(final String chequeDdNo) {
        this.chequeDdNo = chequeDdNo;
    }

    /**
     * @param customerEmail the customerEmail to set
     */
    public void setCustomerEmail(final String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(final String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * @param paymentId the paymentId to set
     */
    public void setPaymentId(final String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @param paymentMode the paymentMode to set
     */
    public void setPaymentMode(final Long paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     * @param transactionDates the transactionDates to set
     */
    public void setTransactionDates(final List<Date> transactionDates) {
        this.transactionDates = transactionDates;
    }
}
