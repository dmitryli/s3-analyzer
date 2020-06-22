package me.dimalimonov.service;

import me.dimalimonov.app.BucketAnalyzerApplication;
import me.dimalimonov.app.model.WBucket;
import me.dimalimonov.app.service.BucketService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = BucketAnalyzerApplication.class)
class BucketServiceTest {

	@Autowired
	BucketService service;

	@Test
	void listBuckets() {

		List<WBucket> wBuckets = service.listBuckets();
		wBuckets.forEach(w -> {
			System.out.println(w);
			System.out.println(w.getDisplaySize());
		});
	}



}
