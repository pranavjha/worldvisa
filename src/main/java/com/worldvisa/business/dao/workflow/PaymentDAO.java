/******************************************************************************
 * Name : PaymentDAO.java Author : Administrator Date : 2010/05/08 Description : Implementation for the PaymentDAO interface
 *****************************************************************************/
package com.worldvisa.business.dao.workflow;

import java.util.List;
import com.worldvisa.business.domain.workflow.Payment;
import com.worldvisa.business.domain.workflow.TransactionRecordsFilter;
import com.worldvisa.business.infra.base.BaseIbatisDAO;

/**
 * DAO interface for interfacing with the WORKFLOW_PAYMENT table<br/>
 */
public interface PaymentDAO
extends BaseIbatisDAO<Payment> {
    /**
     * @param filter
     * @return
     */
    public List<Payment> readSearchList(TransactionRecordsFilter filter)
    throws Exception;

    /**
     * @param filter
     * @return
     */
    public Integer readSearchListCount(TransactionRecordsFilter filter)
    throws Exception;
}
