/******************************************************************************
 * Name : FollowupStatusDAO.java Author : Administrator Date : 2010/05/08 Description : Implementation for the FollowupStatusDAO interface
 *****************************************************************************/
package com.worldvisa.business.dao.market;

import java.util.List;
import com.worldvisa.business.domain.market.UserComment;
import com.worldvisa.business.infra.base.BaseIbatisDAO;

/**
 * DAO interface for interfacing with the REQUEST_CASE_STATUS table<br/>
 */
public interface UserCommentDAO
extends BaseIbatisDAO<UserComment> {
    public void deleteByUserId(List<Long> userIdList)
    throws Exception;
}
