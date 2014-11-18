package rx.observables;

import static org.junit.Assert.assertEquals;
import static rx.observables.ParallelObservable.parallel;

import org.junit.Test;

import rx.Observable;

public class ParallelObservableTest {

	@Test
	public void test() {
		assertEquals(1000,(int) parallel(Observable.range(1, 1000)).buffer(2,1)
				.flatten().count().toBlocking().single());
	}
	
}
