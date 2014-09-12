package rx;

import static org.junit.Assert.assertEquals;
import static rx.ParallelObservable.parallel;

import org.junit.Test;

import rx.functions.Functions;

public class ParallelObservableTest {

	@Test
	public void test() {
		assertEquals(1000,(int) parallel(Observable.range(1, 1000)).map(Functions.identity()).buffer(2,1)
				.flatten().count().toBlocking().single());
	}
	
}
