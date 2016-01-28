package deepblue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.codahale.metrics.RatioGauge;

/**
 * @author deepblue.kblee on 2016. 1. 28..
 */
public class RatioTest {
	@Test
	public void test() {
		RatioGauge.Ratio ratio = RatioGauge.Ratio.of(100, 20);
		assertThat(ratio.getValue(), is(5.0));
		System.out.println(ratio.getValue());
	}
}
