package com.shameer.spring.aws.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SpringAwsS3ServiceImpl implements SpringAwsS3Service {

    @Autowired
    AmazonS3 amazonS3;


    @Override
    public String getContent(String bucketName, String fileName) {
        log.debug("Enter getContent fileName={}", fileName);
        InputStream inputStream = null;
        byte[] contentAsBytes;
        try {
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, fileName));
            inputStream = s3Object.getObjectContent();
            contentAsBytes = IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            log.error("IOException getContent fileName={}", fileName, e);
            return new String("<Error>File couldn't be located or retrieved :" + fileName + "</Error>");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("IOException while closing inputStream in getContent fileName={}", fileName, e);
                }
            }
        }
        log.debug("Exit getContent fileName={}", fileName);
        return new String(contentAsBytes);
    }

    @Override
    public String uploadContent(String bucketName, String fileName, MultipartFile content) {
        ByteArrayInputStream contentAsStream = null;
        try {
            contentAsStream = new ByteArrayInputStream(content.getBytes());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(content.getBytes().length);
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, contentAsStream, metadata));
        } catch (AmazonServiceException e) {
            log.error("Error in uploadContent fileName={}, exception={}", fileName, e.getMessage());
            throw new RuntimeException("Error while uploading content for filename=" + fileName, e);
        } catch (IOException e) {
            log.error("IOException uploadContent fileName={}", fileName, e);
            throw new RuntimeException("Error while uploading content for filename=" + fileName, e);
        } finally {
            if (contentAsStream != null) {
                try {
                    contentAsStream.close();
                } catch (IOException e) {
                    log.error("Content Stream close error in putContentToS3 fileName={}, exception={}", fileName, e.getMessage());
                }
            }
        }
        return "{\"result\":\"" + fileName + " uploaded successfully\"}";
    }

    @Override
    public List<String> getObjectsList(String bucketName, String contentPath) {
        List<String> objectNames = new ArrayList<>();
        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(contentPath);
        ListObjectsV2Result listing = amazonS3.listObjectsV2(req);
        for (S3ObjectSummary summary : listing.getObjectSummaries()) {
            objectNames.add(summary.getKey());
        }
        return objectNames;
    }

}
