package com.example.ai2_diary.s3.application;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(MultipartFile file, String dirName);

    void deleteFile(String fileName, String dirName);
}
