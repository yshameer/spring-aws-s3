package com.github.yshameer.spring.aws.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class SpringAwsS3ServiceImplTest {

    @Autowired
    SpringAwsS3ServiceImpl springAwsS3Service;

    @MockBean
    AmazonS3 amazonS3;

    @MockBean
    S3Object s3Object;

    @MockBean
    ListObjectsV2Result listObjectsV2Result;

    List<S3ObjectSummary> s3ObjectSummaries = new ArrayList<>();

    private static final String CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<TestContent></TestContent>";

    private String encoding = "UTF-8";

    private MultipartFile multipartFile;

    @TestConfiguration
    static class SpringAwsS3ServiceImplTestConfig {
        @Bean
        public SpringAwsS3ServiceImpl springAwsS3Service() {
            return new SpringAwsS3ServiceImpl();
        }
    }

    @Before
    public void setup() {
        multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return "Test";
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return CONTENT.getBytes();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };


        setupS3ObjectSummaries();
    }

    private void setupS3ObjectSummaries() {
        S3ObjectSummary s3ObjectSummary1 = new S3ObjectSummary();
        s3ObjectSummary1.setKey("Test/215315350.xml");
        s3ObjectSummaries.add(s3ObjectSummary1);

        S3ObjectSummary s3ObjectSummary2 = new S3ObjectSummary();
        s3ObjectSummary2.setKey("Test/215315351.xml");
        s3ObjectSummaries.add(s3ObjectSummary2);

        S3ObjectSummary s3ObjectSummary3 = new S3ObjectSummary();
        s3ObjectSummary3.setKey("Test/215315352.xml");
        s3ObjectSummaries.add(s3ObjectSummary3);
    }

    @Test
    public void testGetContent() throws Exception {
        when(amazonS3.getObject(any(GetObjectRequest.class))).thenReturn(s3Object);
        when(s3Object.getObjectContent()).thenReturn(new S3ObjectInputStream(
                new ByteArrayInputStream(CONTENT.getBytes(encoding)), null));

        String resultContent = springAwsS3Service.getContent("Test", "Test");

        assertEquals(CONTENT, resultContent);
    }

    @Test
    public void testUploadContent() {

        String confMessage = "{\"result\":\"Test uploaded successfully\"}";

        String result = springAwsS3Service.uploadContent("Test", "Test", multipartFile);

        assertEquals(confMessage, result);
    }

    @Test
    public void testGetObjectsList() {
        when(amazonS3.listObjectsV2(any(ListObjectsV2Request.class))).thenReturn(listObjectsV2Result);
        when(listObjectsV2Result.getObjectSummaries()).thenReturn(s3ObjectSummaries);

        List<String> resultContent = springAwsS3Service.getObjectsList("Test", "Test");

        assertEquals(Arrays.asList("Test/215315350.xml", "Test/215315351.xml", "Test/215315352.xml"), resultContent);
    }
}