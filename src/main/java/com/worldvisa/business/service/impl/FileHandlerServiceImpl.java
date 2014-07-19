package com.worldvisa.business.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.worldvisa.business.domain.common.FileBean;
import com.worldvisa.business.infra.base.BaseServiceImpl;
import com.worldvisa.business.infra.utils.BusinessParams;
import com.worldvisa.business.service.stub.FileHandlerService;

@Service
public class FileHandlerServiceImpl
extends BaseServiceImpl
implements FileHandlerService {
    public void cleanFolder(final String relativeFolderPath, final List<FileBean> updatedFileList)
    throws Exception {
        final File[] files = new File(BusinessParams.get("worldvisa.shared.dir") + "/" + relativeFolderPath).listFiles(new FileFilter() {
            public boolean accept(final File file) {
                for (final FileBean xFiles : updatedFileList) {
                    if (file.getName().equals(xFiles.getRelativePath().substring(xFiles.getRelativePath().lastIndexOf('/') + 1))) {
                        return false;
                    }
                }
                return true;
            }
        });
        if (null == files) {
            return;
        }
        for (final File file : files) {
            file.delete();
        }
    }

    public File createTempFile()
    throws Exception {
        final File file = new File(BusinessParams.get("worldvisa.shared.dir"));
        if (!file.exists()) {
            file.mkdir();
        }
        return new File(BusinessParams.get("worldvisa.shared.dir") + "/" + UUID.randomUUID().toString());
    }

    public void deleteRecursive(final String relativeFolderPath)
    throws Exception {
        final File file = new File(BusinessParams.get("worldvisa.shared.dir") + "/" + relativeFolderPath);
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            final File[] children = file.listFiles();
            for (final File child : children) {
                child.delete();
            }
        }
        file.delete();
    }

    public File readFile(final FileBean fileDetailsWrapper)
    throws Exception {
        return new File(BusinessParams.get("worldvisa.shared.dir") + "/" + fileDetailsWrapper.getRelativePath());
    }

    public void updateFile(final String prevFilePath, final String newFilePath)
    throws Exception {
        new File(new File(BusinessParams.get("worldvisa.shared.dir") + "/" + newFilePath).getParent()).mkdirs();
        new File(BusinessParams.get("worldvisa.shared.dir") + "/" + prevFilePath).renameTo(new File(BusinessParams.get("worldvisa.shared.dir") + "/"
        + newFilePath));
    }
}
