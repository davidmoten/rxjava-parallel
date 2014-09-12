package rx;

import static org.junit.Assert.assertEquals;
import static rx.ParallelObservable.parallel;

import org.junit.Test;

public class ParallelObservableTest {

	@Test
	public void test() {
		assertEquals(1000,(int) parallel(Observable.range(1, 1000))
				.flatten().count().toBlocking().single());
	}

}
