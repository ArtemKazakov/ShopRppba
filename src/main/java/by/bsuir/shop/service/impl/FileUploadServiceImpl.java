package by.bsuir.shop.service.impl;

import by.bsuir.shop.service.FileUploadService;
import by.bsuir.shop.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.*;


public class FileUploadServiceImpl implements FileUploadService {
    public final static String imageLocation = "/E:/";
    public final static String FILENAME = "filename";
    public final static String CONTENT_DISPOSITION = "content-disposition";

    private final static Logger logger = LogManager.getLogger(FileUploadServiceImpl.class.getName());

    @Override
    public void uploadImage(Part image) throws ServiceException {
        OutputStream out = null;
        InputStream filecontent = null;
        String fileName = getFileName(image);

        try {
            out = new FileOutputStream(new File(imageLocation + File.separator
                    + fileName));

            filecontent = image.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (IOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException ex){
                    logger.error(ex);
                }
            }
            if (filecontent != null){
                try {
                    filecontent.close();
                } catch (IOException ex){
                    logger.error(ex);
                }
            }
        }
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader(CONTENT_DISPOSITION);
        for (String content : part.getHeader(CONTENT_DISPOSITION).split(";")) {
            if (content.trim().startsWith(FILENAME)) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
