package by.bsuir.shop.service;

import by.bsuir.shop.service.exception.ServiceException;

import javax.servlet.http.Part;


public interface FileUploadService {
    void uploadImage(Part image) throws ServiceException;
}
