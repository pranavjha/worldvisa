package com.worldvisa.business.infra.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import com.worldvisa.business.infra.utils.BusinessParams;

/**
 * Defines FileUploaderServlet
 */
public class FileUploaderServlet
extends HttpServlet {
    private static final Logger log              = Logger.getLogger(FileUploaderServlet.class);
    private static final long   serialVersionUID = -5267663558239985780L;

    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
    throws ServletException, IOException {
        this.processRequest(request, response);
    }

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
    throws ServletException, IOException {
        this.processRequest(request, response);
    }

    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response)
    throws ServletException, IOException {
        final PrintWriter out = response.getWriter();
        try {
            final UUID newFileName = UUID.randomUUID();
            /* Check that we have a file upload request */
            final boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                final File tempDir = new File(BusinessParams.get("worldvisa.shared.dir"));
                tempDir.mkdirs();
                final FileItemFactory factory = new DiskFileItemFactory(75000, tempDir);
                final ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(4194304);
                @SuppressWarnings("unchecked")
                final List<FileItem> items = upload.parseRequest(request);
                FileItem file = null;
                String allowedExtensions = null;
                String fileName = null;
                for (final FileItem fileItem : items) {
                    if (!fileItem.isFormField()) {
                        file = fileItem;
                    } else {
                        if (fileItem.getFieldName().equals("allowedTypes")) {
                            allowedExtensions = fileItem.getString();
                        }
                        if (fileItem.getFieldName().equals("fileName")) {
                            fileName = fileItem.getString();
                        }
                    }
                }
                if (null != allowedExtensions && allowedExtensions.trim().length() > 0
                && allowedExtensions.indexOf(fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase()) == -1) {
                    throw new org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException(
                    "The file you are trying to upload is not allowed. Only files with extensions " + allowedExtensions + " can be uploaded.");
                }
                final File uploadedFile = new File(BusinessParams.get("worldvisa.shared.dir") + "/" + newFileName);
                file.write(uploadedFile);
                out.print("{\"fileName\":\"" + fileName + "\", \"relativePath\":\"" + newFileName + "\"}");
            }
        } catch (final org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException ex) {
            out.print("{\"exception\":\"" + ex.getMessage() + "\"}");
        } catch (final org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException ex) {
            out.print("{\"exception\":\"The file you are trying to upload is larger than 4 MBs. Please upload a smaller file.\"}");
        } catch (final Exception ex) {
            FileUploaderServlet.log.error(ex);
            out.print("{\"exception\":\"The file could not be uploaded. Please try again.\"}");
        } finally {
            out.close();
        }
    }
}
