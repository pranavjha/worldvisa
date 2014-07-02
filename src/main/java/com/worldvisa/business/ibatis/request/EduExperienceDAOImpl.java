/******************************************************************************
 * Name : EduExperienceDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the EduExperienceDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.EduExperienceDAO;
import com.worldvisa.business.domain.request.EduExperience;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_EDU_EXPERIENCE table<br/>
 */
@Repository("requestEduExperienceDAOImpl")
public class EduExperienceDAOImpl
extends BaseIbatisDAOImpl<EduExperience>
implements EduExperienceDAO {
}
