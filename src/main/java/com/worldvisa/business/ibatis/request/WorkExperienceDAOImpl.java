/******************************************************************************
 * Name : WorkExperienceDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the WorkExperienceDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.WorkExperienceDAO;
import com.worldvisa.business.domain.request.WorkExperience;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_WORK_EXPERIENCE table<br/>
 */
@Repository("requestWorkExperienceDAOImpl")
public class WorkExperienceDAOImpl
extends BaseIbatisDAOImpl<WorkExperience>
implements WorkExperienceDAO {
}
