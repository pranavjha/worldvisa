/******************************************************************************
 * Name : FollowupStatusDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the FollowupStatusDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.CaseStatusDAO;
import com.worldvisa.business.domain.request.CaseStatus;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_CASE_STATUS table<br/>
 */
@Repository("requestCaseStatusDAOImpl")
public class CaseStatusDAOImpl
extends BaseIbatisDAOImpl<CaseStatus>
implements CaseStatusDAO {
}
