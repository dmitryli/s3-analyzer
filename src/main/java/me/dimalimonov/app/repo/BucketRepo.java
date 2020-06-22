package me.dimalimonov.app.repo;


import lombok.extern.slf4j.Slf4j;
import me.dimalimonov.app.model.WBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BucketRepo {

    @Autowired
    private S3Client client;

    public List<WBucket> listBuckets() {

        List<WBucket> bucketList = getBucketsFromAws();

        bucketList = getAdditionalMetadataFromAws(bucketList);

        return bucketList;

    }

    private List<WBucket> getAdditionalMetadataFromAws(List<WBucket> bucketList) {
        for (WBucket wb : bucketList) {
            ListObjectsRequest request = ListObjectsRequest.builder().bucket(wb.getName()).build();

            log.info("Start fetch extra info from AWS bucket " + wb.getName() + " " + new Date(System.currentTimeMillis()));

            ListObjectsResponse response = client.listObjects(request);

            List<S3Object> listOfObjects = response.contents();


            if (listOfObjects.size() > 0) {
                wb.setObjectCount(listOfObjects.size());

                Instant lastModified = Instant.MIN;

                Long bucketSize = Long.valueOf(0);
                for (S3Object o : listOfObjects) {
                    Instant objectModified = o.lastModified();
                    lastModified = objectModified.compareTo(lastModified) < 1 ? lastModified : objectModified;
                    bucketSize += o.size();
                }
                wb.setLastModified(lastModified.toString());
                wb.setSize(bucketSize);
            }
            log.info("Finish fetch extra info from AWS bucket " + wb.getName() + " " + new Date(System.currentTimeMillis()));

        }
        return bucketList;

    }

    private List<WBucket> getBucketsFromAws() {

        List<WBucket> bucketList = new ArrayList<>();

        // List buckets
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        log.info("Start fetch list buckets from AWS " + new Date(System.currentTimeMillis()));
        ListBucketsResponse listBucketsResponse = client.listBuckets(listBucketsRequest);
        log.info("Finish fetch list buckets from AWS " + new Date(System.currentTimeMillis()));
        listBucketsResponse.buckets().stream().forEach(x -> {
                    WBucket b = new WBucket();
                    b.setName(x.name());
                    bucketList.add(b);
                }
        );

        return bucketList;

    }

    public Integer createBuckets(String bucketPrefix, int count) {
        List<String> locations = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String bucketName = bucketPrefix + UUID.randomUUID();
            CreateBucketRequest request = CreateBucketRequest.builder().bucket(bucketName).build();
            CreateBucketResponse bucketResponse = client.createBucket(request);
            locations.add(bucketResponse.location());


        }

        locations.forEach(s -> System.out.println("Bucket created: " + s));

        return locations.size();
    }
}
