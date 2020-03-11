
# Spring AWS S3 Service

This service provides basic operations to connect to AWS S3 
```
 `POST /content` to upload content to S3 on specified bucket, full file path and file
 `GET /content` to download content from S3 on specified bucket and filename including full path
 `GET /listObjects` to get list of file names under specified bucket and path
 ```

Before running the project please update the application.properties under src/main/resources with your s3 connection details

#Bucket details
```
accessKey=<<Your Access Key>>
secretKey=<<Your Secret Key>>
```

## Software required to build and run
```
Java: JDK 1.8+
Maven: Apache Maven 3.6.3+
```


## Running Locally

To build and run with maven do the following:

```
mvn clean install
mvn spring-boot:run
```

## Testing Locally
http://localhost:8080
