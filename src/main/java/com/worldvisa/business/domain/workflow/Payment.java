/******************************************************************************
 * Name : Payment.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for Payment
 *****************************************************************************/
package com.worldvisa.business.domain.workflow;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for Payment.
 */
@DataTransferObject(converter = BeanConverter.class)
public class Payment
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private Double            amount;
    private String            chequeDdNo;
    private ReportDetails     details;
    private Long              invoiceSentInd;
    private String            paymentId;
    private Long              paymentMode;
    private String            reportId;
    private java.util.Date    transactionDt;
    private String            transactionRemarks;

    /**
     * Instantiates Payment with defaults
     */
    public Payment() {
        super();
        this.details = new ReportDetails();
    }

    /**
     * Instantiates Payment with primary key combination
     */
    public Payment(final String paymentId, final String reportId) {
        super();
        this.details = new ReportDetails();
        this.paymentId = paymentId;
        this.reportId = reportId;
    }

    /**
     * Getter method for amount
     * @return amount
     */
    public Double getAmount() {
        return this.amount;
    }

    /**
     * @return the chequeDdNo
     */
    public String getChequeDdNo() {
        return this.chequeDdNo;
    }

    /**
     * @return the details
     */
    public ReportDetails getDetails() {
        return this.details;
    }

    /**
     * @return the invoiceSentInd
     */
    public Long getInvoiceSentInd() {
        return this.invoiceSentInd;
    }

    /**
     * Getter method for paymentId
     * @return paymentId
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
     * Getter method for reportId
     * @return reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * Getter method for transactionDt
     * @return transactionDt
     */
    public java.util.Date getTransactionDt() {
        return this.transactionDt;
    }

    /**
     * Getter method for transactionRemarks
     * @return transactionRemarks
     */
    public String getTransactionRemarks() {
        return this.transactionRemarks;
    }

    /**
     * Setter method for amount
     * @param amount Value for amount
     */
    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    /**
     * @param chequeDdNo the chequeDdNo to set
     */
    public void setChequeDdNo(final String chequeDdNo) {
        this.chequeDdNo = chequeDdNo;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(final ReportDetails details) {
        this.details = details;
    }

    /**
     * @param invoiceSentInd the invoiceSentInd to set
     */
    public void setInvoiceSentInd(final Long invoiceSentInd) {
        this.invoiceSentInd = invoiceSentInd;
    }

    /**
     * Setter method for paymentId
     * @param paymentId Value for paymentId
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
     * Setter method for reportId
     * @param reportId Value for reportId
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }

    /**
     * Setter method for transactionDt
     * @param transactionDt Value for transactionDt
     */
    public void setTransactionDt(final java.util.Date transactionDt) {
        this.transactionDt = transactionDt;
    }

    /**
     * Setter method for transactionRemarks
     * @param transactionRemarks Value for transactionRemarks
     */
    public void setTransactionRemarks(final String transactionRemarks) {
        this.transactionRemarks = transactionRemarks;
    }
}
