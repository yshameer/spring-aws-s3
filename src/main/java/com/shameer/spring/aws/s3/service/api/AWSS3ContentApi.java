package com.shameer.spring.aws.s3.service.api;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(value = "AWS S3 Content", description = "the AWS S3 Content API")
public interface AWSS3ContentApi {

    @ApiOperation(value = "", notes = "Upload a new content", response = String.class, tags = {"File Upload",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "content uploaded successfully", response = String.class)})

    @RequestMapping(value = "/content",
            produces = {"application/json"},
            consumes = {"multipart/form-data"},
            method = RequestMethod.POST)
    ResponseEntity<String> uploadContent(@ApiParam(value = "Bucket Name.", required = true) @RequestHeader(value = "bucketName", required = true) String bucketName, @ApiParam(value = "Include S3 path with file name and extension", required = true) @RequestPart(value = "contentPath", required = true) String contentPath, @ApiParam(value = "file detail") @RequestPart("file") MultipartFile file);


    @ApiOperation(value = "Content", notes = "Download an existing content.", response = String.class, tags = {"File Download",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A successful content", response = String.class)})

    @RequestMapping(value = "/content",
            produces = {"text/plain"},
            method = RequestMethod.GET)
    ResponseEntity<String> contentGet(@ApiParam(value = "Bucket Name.", required = true) @RequestHeader(value = "bucketName", required = true) String bucketName, @NotNull @ApiParam(value = "Include S3 path with file name and extension.", required = true) @RequestParam(value = "contentPath", required = true) String contentPath);


    @ApiOperation(value = "Content", notes = "List objects under a bucket path", response = String.class, tags = {"File List",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A successful list of objects", response = String.class)})

    @RequestMapping(value = "/listObjects",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<String>> listObjectsGet(@ApiParam(value = "Bucket Name.", required = true) @RequestHeader(value = "bucketName", required = true) String bucketName, @NotNull @ApiParam(value = "Include S3 path to get list.", required = true) @RequestParam(value = "contentPath", required = true) String contentPath);
}
