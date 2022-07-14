package com.fandf.user.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fandongfeng
 */
@Slf4j
@Configuration
public class AwsConfig {

    /**
     * AWS 访问密钥
     */
    @Value("${s3.access_key}")
    private String accessKey;

    /**
     * AWS SECRET_KEY
     */
    @Value("${s3.secret_key}")
    private String secretKey;
    /**
     * AWS region
     */
    @Value("${s3.region}")
    private String region;

    /**
     * 初始化生成AmazonS3 客户端配置
     *
     * @return
     */
    @Bean
    public AmazonS3 amazonS3() {
        log.info("start create s3Client");
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTPS);

        AmazonS3 S3client = AmazonS3Client.builder()
                .withClientConfiguration(clientConfig)
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(true)
                .withForceGlobalBucketAccessEnabled(true)
                .withRegion(region)
                .build();
        log.info("create s3Client success");

        return S3client;

    }
}


