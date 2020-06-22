package me.dimalimonov.app.controller;

import me.dimalimonov.app.model.WBucket;
import me.dimalimonov.app.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class BucketController {

    @Autowired
    private BucketService service;

    @RequestMapping("/")
    public String root() {
        return String.format("Service Running: " + new Date(System.currentTimeMillis()));
    }

    @RequestMapping("/buckets")
    public List<WBucket> listBuckets() {
        return service.listBuckets();
    }
}
