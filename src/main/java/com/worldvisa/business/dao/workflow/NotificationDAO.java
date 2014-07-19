/******************************************************************************
 * Name : NotificationDAO.java Author : Administrator Date : 2010/05/08 Description : Implementation for the NotificationDAO interface
 *****************************************************************************/
package com.worldvisa.business.dao.workflow;

import java.util.List;
import com.worldvisa.business.domain.common.ContextAwareDataTableRequest;
import com.worldvisa.business.domain.workflow.Notification;
import com.worldvisa.business.infra.base.BaseIbatisDAO;

/**
 * DAO interface for interfacing with the WORKFLOW_NOTIFICATION table<br/>
 */
public interface NotificationDAO
extends BaseIbatisDAO<Notification> {
    Integer readLengthForUser(ContextAwareDataTableRequest request)
    throws Exception;

    /**
     * @param reportId
     * @return
     */
    public List<Notification> readListForReport(String reportId)
    throws Exception;

    /**
     * @param notification
     * @param dataTableRequest
     * @return
     */
    public List<Notification> readListForUser(ContextAwareDataTableRequest dataTableRequest)
    throws Exception;
}
