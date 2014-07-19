/******************************************************************************
 * Name : FileDetailsDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the FileDetailsDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.workflow.FileDetailsDAO;
import com.worldvisa.business.domain.workflow.FileDetails;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.WORKFLOW_FILE_DETAILS table<br/>
 */
@Repository("workflowFileDetailsDAOImpl")
public class FileDetailsDAOImpl
extends BaseIbatisDAOImpl<FileDetails>
implements FileDetailsDAO {
    /**
     * @see com.worldvisa.business.dao.workflow.FileDetailsDAO#delete(java.util.List)
     */
    public void delete(final List<Long> fileIds, final String reportId)
    throws Exception {
        BaseIbatisDAOImpl.log.debug(fileIds + "      " + reportId);
        final Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("fileIds", fileIds);
        paramMap.put("reportId", reportId);
        this.getSqlMapClientTemplate().delete("workflowFileDetails.deleteByFileIds", paramMap);
    }
}
