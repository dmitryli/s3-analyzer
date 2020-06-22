package me.dimalimonov.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class BucketsConfiguration {

    @Bean
    public S3Client s3Client() {
        Region region = Region.US_EAST_2;
         AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);


        S3Client client = S3Client.builder().credentialsProvider(credentialsProvider).region(region).build();

        return client;

    }
}
