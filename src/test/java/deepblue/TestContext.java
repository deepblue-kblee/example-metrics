package deepblue;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;

/**
 * @author deepblue.kblee on 2016. 1. 28..
 */
public class TestContext {
	private final MetricRegistry metricRegistry = new MetricRegistry();
	private ConsoleReporter reporter;

	public void init() {
		if (reporter == null) {
			reporter = ConsoleReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
			reporter.start(1, TimeUnit.SECONDS);
		}
	}

	public MetricRegistry getMetricRegistry() {
		return metricRegistry;
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}
}
