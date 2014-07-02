/******************************************************************************
 * Name : CountryPreferencesDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the CountryPreferencesDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.CountryPreferencesDAO;
import com.worldvisa.business.domain.request.CountryPreferences;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_COUNTRY_PREFERENCES table<br/>
 */
@Repository("requestCountryPreferencesDAOImpl")
public class CountryPreferencesDAOImpl
extends BaseIbatisDAOImpl<CountryPreferences>
implements CountryPreferencesDAO {
}
