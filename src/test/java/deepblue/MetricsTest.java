package deepblue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;
import com.codahale.metrics.Timer;

/**
 * @author deepblue.kblee on 2016. 1. 28..
 */
public class MetricsTest {
	private TestContext testContext = new TestContext();
	private final ExecutorService executorService = Executors.newFixedThreadPool(1);
	private volatile boolean bRun;
	private Future f;
	private int num;

	@Before
	public void before() {
		testContext.init();
		startNumThread();
	}

	private void startNumThread() {
		bRun = true;
		num = 0;
		f = executorService.submit(new Runnable() {
			public void run() {
				while (bRun) {
					num++;
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {}
				}
			}
		});
	}

	private void stopNumThread() throws Exception {
		bRun = false;
		if (f != null) {
			f.get();
		}
	}

	@After
	public void after() throws Exception {
		stopNumThread();
	}

	@Test
	public void test() {
		MetricRegistry metricRegistry = testContext.getMetricRegistry();

		// Counter
		Counter counter = metricRegistry.counter(MetricRegistry.name(MetricsTest.class, "counter"));
		counter.inc();

		// Gauge
		Gauge gauge = new Gauge<Integer>() {
			public Integer getValue() {
				return num;
			}
		};
		metricRegistry.register(MetricRegistry.name(MetricsTest.class, "gauge"), gauge);

		// RatioGauge
		final Counter cntr = counter;
		RatioGauge ratioGauge = new RatioGauge() {
			@Override
			protected Ratio getRatio() {
				return Ratio.of(cntr.getCount(), num);
			}
		};
		metricRegistry.register(MetricRegistry.name(MetricsTest.class, "ratioGauge"), ratioGauge);

		// Timer
		Timer timer = metricRegistry.timer(MetricRegistry.name(MetricsTest.class, "timer"));

		Timer.Context timerContext = timer.time();
		sleep(2000);
		timerContext.stop();
		counter.inc();

		timerContext = timer.time();
		sleep(1000);
		timerContext.stop();
		counter.inc(2);

		sleep(3000);
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}
}
