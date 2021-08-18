package sample;

import javax.cache.CacheManager;

import com.hazelcast.cache.HazelcastCacheManager;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HazelcastSampleApplicationTests {

	@Test
	void contextLoads(ApplicationContext context) {
		// assert that HazelcastInstance is configured
		HazelcastInstance hazelcastInstance = context.getBean(HazelcastInstance.class);
		assertThat(hazelcastInstance).isNotNull();
		// assert that Spring cache manager delegates to Hazelcast JCache cache manager
		JCacheCacheManager springCacheManager = context.getBean(JCacheCacheManager.class);
		assertThat(springCacheManager).isNotNull();
		CacheManager jcacheCacheManager = springCacheManager.getCacheManager();
		assertThat(jcacheCacheManager).isInstanceOf(HazelcastCacheManager.class);
		// assert that Hazelcast JCache cache manager uses default HazelcastInstance
		assertThat(jcacheCacheManager).extracting("hazelcastInstance").isEqualTo(hazelcastInstance);
		// assert that Hazelcast session repository is configured and uses default HazelcastInstance
		Hazelcast4IndexedSessionRepository sessionRepository = context.getBean(Hazelcast4IndexedSessionRepository.class);
		assertThat(sessionRepository).isNotNull();
		assertThat(sessionRepository).extracting("hazelcastInstance").isEqualTo(hazelcastInstance);
	}

}
