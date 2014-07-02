/******************************************************************************
 * Name : PaymentDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the PaymentDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.workflow;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.workflow.PaymentDAO;
import com.worldvisa.business.domain.workflow.Payment;
import com.worldvisa.business.domain.workflow.TransactionRecordsFilter;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.WORKFLOW_PAYMENT table<br/>
 */
@Repository("workflowPaymentDAOImpl")
public class PaymentDAOImpl
extends BaseIbatisDAOImpl<Payment>
implements PaymentDAO {
    /**
     * @see com.worldvisa.business.dao.workflow.PaymentDAO#readSearchList(com.worldvisa.business.domain.workflow.TransactionRecordsFilter)
     */
    @SuppressWarnings("unchecked")
    public List<Payment> readSearchList(final TransactionRecordsFilter filter)
    throws Exception {
        return this.getSqlMapClientTemplate().queryForList("workflowPayment.readSearchList", filter);
    }

    /**
     * @see com.worldvisa.business.dao.workflow.PaymentDAO#readSearchListCount(com.worldvisa.business.domain.workflow.TransactionRecordsFilter)
     */
    public Integer readSearchListCount(final TransactionRecordsFilter filter)
    throws Exception {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("workflowPayment.readSearchListCount", filter);
    }
}
