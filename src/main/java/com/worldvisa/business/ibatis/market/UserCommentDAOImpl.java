/******************************************************************************
 * Name : FollowupStatusDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the FollowupStatusDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.market;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.market.UserCommentDAO;
import com.worldvisa.business.domain.market.UserComment;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.MARKET_FOLLOWUP_STATUS table<br/>
 */
@Repository("marketUserCommentDAOImpl")
public class UserCommentDAOImpl
extends BaseIbatisDAOImpl<UserComment>
implements UserCommentDAO {
    public void deleteByUserId(List<Long> userIdList)
    throws Exception {
        // TODO:
    }
}
