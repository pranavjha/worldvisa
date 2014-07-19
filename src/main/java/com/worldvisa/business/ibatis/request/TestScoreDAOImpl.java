/******************************************************************************
 * Name : TestScoreDAOImpl.java Author : Administrator Date : 2010/05/16 Description : Implementation for the TestScoreDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.TestScoreDAO;
import com.worldvisa.business.domain.request.TestScore;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_TEST_SCORE table<br/>
 */
@Repository("requestTestScoreDAOImpl")
public class TestScoreDAOImpl
extends BaseIbatisDAOImpl<TestScore>
implements TestScoreDAO {
}
