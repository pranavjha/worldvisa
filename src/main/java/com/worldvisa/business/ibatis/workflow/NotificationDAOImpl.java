/******************************************************************************
 * Name : NotificationDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the NotificationDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.workflow;

import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.workflow.NotificationDAO;
import com.worldvisa.business.domain.common.ContextAwareDataTableRequest;
import com.worldvisa.business.domain.workflow.Notification;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.WORKFLOW_NOTIFICATION table<br/>
 */
@Repository("workflowNotificationDAOImpl")
public class NotificationDAOImpl
extends BaseIbatisDAOImpl<Notification>
implements NotificationDAO {
    public Integer readLengthForUser(final ContextAwareDataTableRequest request)
    throws Exception {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("workflowNotification.readLengthForUser", request);
    }

    /**
     * @see com.worldvisa.business.dao.workflow.NotificationDAO#readListForReport(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Notification> readListForReport(final String reportId)
    throws Exception {
        return this.getSqlMapClientTemplate().queryForList("workflowNotification.readForReport", reportId);
    }

    /**
     * @see com.worldvisa.business.dao.workflow.NotificationDAO#readListForUser(com.worldvisa.business.domain.workflow.Notification)
     */
    @SuppressWarnings("unchecked")
    public List<Notification> readListForUser(final ContextAwareDataTableRequest request)
    throws Exception {
        return this.getSqlMapClientTemplate().queryForList("workflowNotification.readForUser", request);
    }

    /**
     * @see com.worldvisa.business.infra.base.BaseIbatisDAOImpl#update(java.lang.Object, java.lang.Object)
     */
    @Override
    public int update(final Notification data, final Notification filter)
    throws SQLException {
        this.getSqlMapClientTemplate().insert("workflowNotification.update", data);
        return 0;
    }
}
