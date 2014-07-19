package com.worldvisa.business.domain.market;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.common.DataTableRequest;
import com.worldvisa.business.domain.common.FileBean;

/**
 * Defines ContactsLoader
 */
@DataTransferObject(converter = BeanConverter.class)
public class UserLoader
extends DataTableRequest {
    private static final long serialVersionUID = -5777924027659368134L;
    private String            listFilePath;
    private FileBean          uploadedFile;
    private UserGroup         userGroup;

    /**
     * @return the listFilePath
     */
    public String getListFilePath() {
        return this.listFilePath;
    }

    /**
     * @return the uploadedFile
     */
    public FileBean getUploadedFile() {
        return this.uploadedFile;
    }

    /**
     * @return the userGroup
     */
    public UserGroup getUserGroup() {
        return this.userGroup;
    }

    /**
     * @param listFilePath the listFilePath to set
     */
    public void setListFilePath(String listFilePath) {
        this.listFilePath = listFilePath;
    }

    /**
     * @param uploadedFile the uploadedFile to set
     */
    public void setUploadedFile(FileBean uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    /**
     * @param userGroup the userGroup to set
     */
    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
