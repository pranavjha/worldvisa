/******************************************************************************
 * Name : ConversationDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the ConversationDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.workflow;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.workflow.ConversationDAO;
import com.worldvisa.business.domain.workflow.Conversation;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.WORKFLOW_CONVERSATION table<br/>
 */
@Repository("workflowConversationDAOImpl")
public class ConversationDAOImpl
extends BaseIbatisDAOImpl<Conversation>
implements ConversationDAO {
}
