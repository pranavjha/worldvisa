/******************************************************************************
 * Name : FileDetailsDAO.java Author : Administrator Date : 2010/05/08 Description : Implementation for the FileDetailsDAO interface
 *****************************************************************************/
package com.worldvisa.business.dao.market;

import java.util.List;
import com.worldvisa.business.domain.market.FileDetails;
import com.worldvisa.business.infra.base.BaseIbatisDAO;

/**
 * DAO interface for interfacing with the WORKFLOW_FILE_DETAILS table<br/>
 */
public interface FileDetailsDAO
extends BaseIbatisDAO<FileDetails> {
    /**
     * @param fileIds
     */
    public void delete(List<Long> fileIds, Long mailid)
    throws Exception;
}
