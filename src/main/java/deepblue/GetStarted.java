package deepblue;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

/**
 * Get Started.
 *
 * http://metrics.dropwizard.io/3.1.0/getting-started/
 */
public class GetStarted {
	static final MetricRegistry registry = new MetricRegistry();

	public GetStarted() {
	}

	public void startReport() {
		ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
			.convertRatesTo(TimeUnit.SECONDS)
			.convertDurationsTo(TimeUnit.MILLISECONDS)
			.build();
		reporter.start(1, TimeUnit.SECONDS);
	}

	public static void main( String[] args ) {
		GetStarted gs = new GetStarted();
		gs.startReport();

		Meter requests = registry.meter("requests");
		requests.mark();
		wait5Seconds();
	}

	static void wait5Seconds() {
		try {
			Thread.sleep(5*1000);
		}
		catch(InterruptedException e) {}
	}
}
