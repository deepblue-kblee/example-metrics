package deepblue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.ClassLoadingGaugeSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;

/**
 * @author deepblue.kblee on 2016. 1. 28..
 */
public class JvmMetricsTest {
	private TestContext testContext = new TestContext();

	@Before
	public void before() {
		testContext.init();
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void test() {
		MetricRegistry metricRegistry = testContext.getMetricRegistry();

		metricRegistry.registerAll(new GarbageCollectorMetricSet());
		metricRegistry.registerAll(new MemoryUsageGaugeSet());
		metricRegistry.registerAll(new ClassLoadingGaugeSet());

		sleep(2000);
		for (int i=0; i<1000; i++) {
			byte b[] = new byte[100000];
		}
		sleep(2000);
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}
}
