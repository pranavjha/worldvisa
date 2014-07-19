package com.worldvisa.business.service.stub;

import java.io.File;
import java.util.List;
import com.worldvisa.business.domain.common.FileBean;

public interface FileHandlerService {
    public void cleanFolder(String relativeFolderPath, List<FileBean> updatedFileList)
    throws Exception;

    public File createTempFile()
    throws Exception;

    public void deleteRecursive(String relativePath)
    throws Exception;

    public File readFile(FileBean fileDetailsWrapper)
    throws Exception;

    public void updateFile(String prevFilePath, String newFilePath)
    throws Exception;
}
