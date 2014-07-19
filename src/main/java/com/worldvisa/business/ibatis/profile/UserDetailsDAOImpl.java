/******************************************************************************
 * Name : UserDetailsDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the UserDetailsDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.profile;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.profile.UserDetailsDAO;
import com.worldvisa.business.domain.profile.UserDetails;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.PROFILE_USER_DETAILS table<br/>
 */
@Repository("profileUserDetailsDAOImpl")
public class UserDetailsDAOImpl
extends BaseIbatisDAOImpl<UserDetails>
implements UserDetailsDAO {
}
