/******************************************************************************
 * Name : CoursePreferenceDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the CoursePreferenceDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.CoursePreferenceDAO;
import com.worldvisa.business.domain.request.CoursePreference;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_COURSE_PREFERENCE table<br/>
 */
@Repository("requestCoursePreferenceDAOImpl")
public class CoursePreferenceDAOImpl
extends BaseIbatisDAOImpl<CoursePreference>
implements CoursePreferenceDAO {
}
