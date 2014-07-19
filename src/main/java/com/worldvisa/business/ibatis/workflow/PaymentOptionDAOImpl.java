/******************************************************************************
 * Name : PaymentOptionDAOImpl.java Author : Administrator Date : 2010/06/01 Description : Implementation for the PaymentOptionDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.workflow;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.workflow.PaymentOptionDAO;
import com.worldvisa.business.domain.workflow.PaymentOption;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.WORKFLOW_PAYMENT_OPTION table<br/>
 */
@Repository("workflowPaymentOptionDAOImpl")
public class PaymentOptionDAOImpl
extends BaseIbatisDAOImpl<PaymentOption>
implements PaymentOptionDAO {
}
