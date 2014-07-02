package com.worldvisa.business.infra.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.worldvisa.business.infra.utils.BusinessParams;

/**
 * Defines FileUploaderServlet
 */
public class FileDownloaderServlet
extends HttpServlet {
    private static final Logger log              = Logger.getLogger(FileDownloaderServlet.class);
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
        final String path = request.getRequestURI();
        final String relativePath = path.substring(path.indexOf("download/") + 9);
        final File filePath = new File(BusinessParams.get("worldvisa.shared.dir") + "/" + relativePath);
        final BufferedOutputStream responseStream = new BufferedOutputStream(response.getOutputStream());
        InputStream inputStream = null;
        try {
            final String extension = relativePath.substring(relativePath.lastIndexOf(".") + 1).toLowerCase();
            if ("bmp".equals(extension)) {
                response.setContentType("image/bmp");
            } else if ("doc".equals(extension)) {
                response.setContentType("application/msword");
            } else if ("docx".equals(extension)) {
                response.setContentType("application/msword");
            } else if ("dot".equals(extension)) {
                response.setContentType("application/msword");
            } else if ("gif".equals(extension)) {
                response.setContentType("image/gif");
            } else if ("jpg".equals(extension)) {
                response.setContentType("image/jpeg");
            } else if ("jpeg".equals(extension)) {
                response.setContentType("image/jpeg");
            } else if ("pdf".equals(extension)) {
                response.setContentType("application/pdf");
            } else if ("ppt".equals(extension)) {
                response.setContentType("application/mspowerpoint");
            } else if ("rtf".equals(extension)) {
                response.setContentType("application/rtf");
            } else if ("txt".equals(extension)) {
                response.setContentType("text/plain");
            } else if ("xls".equals(extension)) {
                response.setContentType("application/excel");
            } else if ("zip".equals(extension)) {
                response.setContentType("application/zip");
            } else {
                response.setContentType(new MimetypesFileTypeMap().getContentType(filePath));
            }
            inputStream = new FileInputStream(filePath);
            final byte[] bytes = new byte[2048];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(bytes)) != -1) {
                responseStream.write(bytes, 0, bytesRead);
            }
            response.flushBuffer();
        } catch (final Exception ex) {
            FileDownloaderServlet.log.error(ex);
            response.sendError(500);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            responseStream.flush();
            responseStream.close();
        }
    }
}
