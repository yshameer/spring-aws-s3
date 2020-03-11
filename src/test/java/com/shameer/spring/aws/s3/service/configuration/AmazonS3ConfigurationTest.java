package com.shameer.spring.aws.s3.service.configuration;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AmazonS3Configuration.class})
@TestPropertySource("classpath:application-test.properties")
public class AmazonS3ConfigurationTest {

    @Autowired
    AmazonS3 amazonS3;


    @Test
    public void amazonS3ShouldBePresent() {
        assertThat(amazonS3).isNotNull();
    }

}