package me.dimalimonov.app.service;


import lombok.extern.slf4j.Slf4j;
import me.dimalimonov.app.model.WBucket;
import me.dimalimonov.app.repo.BucketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BucketService {

    @Autowired
    private BucketRepo bucketRepo;

    @Value("${welkin.bucket.name.prefix}")
    private String bucketPrefix;

    public List<WBucket> listBuckets() {

        long start = System.currentTimeMillis();
        log.info("Begin AWS Buckets processing " + new Date(start));
        List<WBucket> bucketList = bucketRepo.listBuckets();

        for (WBucket wb : bucketList) {
            if (wb.getName().contains(bucketPrefix)) {
                String providerId = wb.getName().split(bucketPrefix)[1];
                wb.setProviderId(providerId);
            }
        }

        long finish = System.currentTimeMillis();
        log.info("Finish AWS Buckets  " + new Date(finish));

        double total = (finish - start) * 1.0 / 1000;

        log.info("Total processing time AWS Buckets seconds  " + total);

        return bucketList;

    }


}
