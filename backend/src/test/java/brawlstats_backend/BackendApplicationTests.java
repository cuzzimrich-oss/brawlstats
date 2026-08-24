package brawlstats_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"brawlstars.api.url=https://api.brawlstars.com/v1",
		"brawlstars.api.key=dummy-key"
})
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}
}