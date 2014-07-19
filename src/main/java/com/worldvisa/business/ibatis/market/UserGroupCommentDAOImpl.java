/******************************************************************************
 * Name : DataGroupCommentsDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the DataGroupCommentsDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.market;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.market.UserGroupCommentDAO;
import com.worldvisa.business.domain.market.UserGroupComment;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.MARKET_FOLLOWUP_STATUS table<br/>
 */
@Repository("marketDataGroupCommentDAOImpl")
public class UserGroupCommentDAOImpl
extends BaseIbatisDAOImpl<UserGroupComment>
implements UserGroupCommentDAO {
}
