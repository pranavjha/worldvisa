/******************************************************************************
 * Name : PaymentOption.java Author : Administrator Date : 2010/06/01 Description : POJO implementation for PaymentOption
 *****************************************************************************/
package com.worldvisa.business.domain.workflow;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for PaymentOption.
 */
@DataTransferObject(converter = BeanConverter.class)
public class PaymentOption
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            amount;
    private Double            amountValue;
    private Long              caseSubstatus;
    private String            country;
    private Long              instalmentPlan;
    private Long              paymentNo;
    private Long              requestType;

    /**
     * Instantiates PaymentOption with defaults
     */
    public PaymentOption() {
        super();
    }

    /**
     * Instantiates PaymentOption with primary key combination
     */
    public PaymentOption(final String country, final Long instalmentPlan, final Long paymentNo) {
        super();
        this.country = country;
        this.instalmentPlan = instalmentPlan;
        this.paymentNo = paymentNo;
    }

    /**
     * Getter method for amount
     * @return amount
     */
    public String getAmount() {
        return this.amount;
    }

    /**
     * @return the amountValue
     */
    public Double getAmountValue() {
        return this.amountValue;
    }

    /**
     * Getter method for caseSubstatus
     * @return caseSubstatus
     */
    public Long getCaseSubstatus() {
        return this.caseSubstatus;
    }

    /**
     * Getter method for country
     * @return country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Getter method for instalmentPlan
     * @return instalmentPlan
     */
    public Long getInstalmentPlan() {
        return this.instalmentPlan;
    }

    /**
     * Getter method for paymentNo
     * @return paymentNo
     */
    public Long getPaymentNo() {
        return this.paymentNo;
    }

    /**
     * @return the requestType
     */
    public Long getRequestType() {
        return this.requestType;
    }

    /**
     * Setter method for amount
     * @param amount Value for amount
     */
    public void setAmount(final String amount) {
        this.amount = amount;
    }

    /**
     * @param amountValue the amountValue to set
     */
    public void setAmountValue(final Double amountValue) {
        this.amountValue = amountValue;
    }

    /**
     * Setter method for caseSubstatus
     * @param caseSubstatus Value for caseSubstatus
     */
    public void setCaseSubstatus(final Long caseSubstatus) {
        this.caseSubstatus = caseSubstatus;
    }

    /**
     * Setter method for country
     * @param country Value for country
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * Setter method for instalmentPlan
     * @param instalmentPlan Value for instalmentPlan
     */
    public void setInstalmentPlan(final Long instalmentPlan) {
        this.instalmentPlan = instalmentPlan;
    }

    /**
     * Setter method for paymentNo
     * @param paymentNo Value for paymentNo
     */
    public void setPaymentNo(final Long paymentNo) {
        this.paymentNo = paymentNo;
    }

    /**
     * @param requestType the requestType to set
     */
    public void setRequestType(final Long requestType) {
        this.requestType = requestType;
    }
}
