package me.dimalimonov.app.controller;

import lombok.extern.slf4j.Slf4j;
import me.dimalimonov.app.model.WBucket;
import me.dimalimonov.app.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class BucketViewController {

    @Autowired
    private BucketService service;


    @GetMapping("/view/buckets")
    public String greeting(Model model) {
        long start = System.currentTimeMillis();
        log.info("Begin AWS Buckets processing " + new Date(start));

        List<WBucket> wBuckets = service.listBuckets();
        long finish = System.currentTimeMillis();
        log.info("Finish AWS Buckets  " + new Date(finish));

        double total = (finish - start) * 1.0 / 1000;

        log.info("Total processing time AWS Buckets seconds  " + total);

        model.addAttribute("totalTime", total);
        model.addAttribute("wbuckets", wBuckets);

        return "buckets";
    }
//    public List<WBucket> listBuckets() {
//        return service.listBuckets();
//    }
}
