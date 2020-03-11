package com.shameer.spring.aws.s3.service;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SpringAwsS3Service {
    String getContent(String bucketName, String fileName);

    String uploadContent(String bucketName, String fileName, MultipartFile content);

    List<String> getObjectsList(String bucketName, String contentPath);
}
