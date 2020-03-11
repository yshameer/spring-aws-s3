package com.shameer.spring.aws.s3.service.api;

import com.shameer.spring.aws.s3.service.SpringAwsS3Service;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
public class AWSS3ContentApiController implements AWSS3ContentApi {

    @Autowired
    SpringAwsS3Service springAwsS3Service;

    public ResponseEntity<String> uploadContent(@ApiParam(value = "Bucket Name.", required = true) @RequestHeader(value = "bucketName", required = true) String bucketName,
                                                @ApiParam(value = "Include S3 path with file name and extension", required = true) @RequestPart(value = "contentPath", required = true) String contentPath,
                                                @ApiParam(value = "file detail") @RequestPart("file") MultipartFile file) {
        return new ResponseEntity<>(springAwsS3Service.uploadContent(bucketName, contentPath, file), HttpStatus.OK);
    }

    public ResponseEntity<String> contentGet(@ApiParam(value = "Bucket Name.", required = true) @RequestHeader(value = "bucketName", required = true) String bucketName,
                                             @NotNull @ApiParam(value = "Include S3 path with file name and extension.", required = true) @RequestParam(value = "contentPath", required = true) String contentPath) {
        return new ResponseEntity<>(springAwsS3Service.getContent(bucketName, contentPath), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> listObjectsGet(@ApiParam(value = "Bucket Name.", required = true) @RequestHeader(value = "bucketName", required = true) String bucketName,
                                                       @NotNull @ApiParam(value = "Include S3 path to get file list", required = true) @RequestParam(value = "contentPath", required = true) String contentPath) {
        return new ResponseEntity<>(springAwsS3Service.getObjectsList(bucketName, contentPath), HttpStatus.OK);
    }

}
