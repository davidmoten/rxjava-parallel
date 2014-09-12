package rx;

import static rx.functions.Functions.alwaysFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import rx.Observable.OnSubscribe;
import rx.Observable.Operator;
import rx.Observable.Transformer;
import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorNotImplementedException;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.Func4;
import rx.functions.Func5;
import rx.functions.Func6;
import rx.functions.Func7;
import rx.functions.Func8;
import rx.functions.Func9;
import rx.functions.FuncN;
import rx.functions.Functions;
import rx.internal.operators.OnSubscribeCache;
import rx.internal.operators.OnSubscribeDelay;
import rx.internal.operators.OnSubscribeDelaySubscription;
import rx.internal.operators.OnSubscribeDelayWithSelector;
import rx.internal.operators.OnSubscribeGroupJoin;
import rx.internal.operators.OnSubscribeJoin;
import rx.internal.operators.OnSubscribeMulticastSelector;
import rx.internal.operators.OnSubscribeRange;
import rx.internal.operators.OnSubscribeRedo;
import rx.internal.operators.OnSubscribeTimerOnce;
import rx.internal.operators.OnSubscribeTimerPeriodically;
import rx.internal.operators.OnSubscribeUsing;
import rx.internal.operators.OperatorAll;
import rx.internal.operators.OperatorAny;
import rx.internal.operators.OperatorAsObservable;
import rx.internal.operators.OperatorBufferWithSingleObservable;
import rx.internal.operators.OperatorBufferWithSize;
import rx.internal.operators.OperatorBufferWithStartEndObservable;
import rx.internal.operators.OperatorBufferWithTime;
import rx.internal.operators.OperatorCast;
import rx.internal.operators.OperatorDebounceWithSelector;
import rx.internal.operators.OperatorDebounceWithTime;
import rx.internal.operators.OperatorDefaultIfEmpty;
import rx.internal.operators.OperatorDematerialize;
import rx.internal.operators.OperatorDistinct;
import rx.internal.operators.OperatorDistinctUntilChanged;
import rx.internal.operators.OperatorDoOnEach;
import rx.internal.operators.OperatorDoOnSubscribe;
import rx.internal.operators.OperatorDoOnUnsubscribe;
import rx.internal.operators.OperatorElementAt;
import rx.internal.operators.OperatorFilter;
import rx.internal.operators.OperatorFinally;
import rx.internal.operators.OperatorGroupBy;
import rx.internal.operators.OperatorGroupByUntil;
import rx.internal.operators.OperatorMap;
import rx.internal.operators.OperatorMapNotification;
import rx.internal.operators.OperatorMapPair;
import rx.internal.operators.OperatorMaterialize;
import rx.internal.operators.OperatorMulticast;
import rx.internal.operators.OperatorObserveOn;
import rx.internal.operators.OperatorOnBackpressureBuffer;
import rx.internal.operators.OperatorOnBackpressureDrop;
import rx.internal.operators.OperatorOnErrorFlatMap;
import rx.internal.operators.OperatorOnErrorResumeNextViaFunction;
import rx.internal.operators.OperatorOnErrorResumeNextViaObservable;
import rx.internal.operators.OperatorOnErrorReturn;
import rx.internal.operators.OperatorOnExceptionResumeNextViaObservable;
import rx.internal.operators.OperatorParallel;
import rx.internal.operators.OperatorParallelMerge;
import rx.internal.operators.OperatorReplay;
import rx.internal.operators.OperatorRetryWithPredicate;
import rx.internal.operators.OperatorSampleWithObservable;
import rx.internal.operators.OperatorSampleWithTime;
import rx.internal.operators.OperatorScan;
import rx.internal.operators.OperatorSequenceEqual;
import rx.internal.operators.OperatorSerialize;
import rx.internal.operators.OperatorSingle;
import rx.internal.operators.OperatorSkip;
import rx.internal.operators.OperatorSkipLast;
import rx.internal.operators.OperatorSkipLastTimed;
import rx.internal.operators.OperatorSkipTimed;
import rx.internal.operators.OperatorSkipUntil;
import rx.internal.operators.OperatorSkipWhile;
import rx.internal.operators.OperatorSubscribeOn;
import rx.internal.operators.OperatorSwitch;
import rx.internal.operators.OperatorTake;
import rx.internal.operators.OperatorTakeLast;
import rx.internal.operators.OperatorTakeLastTimed;
import rx.internal.operators.OperatorTakeTimed;
import rx.internal.operators.OperatorTakeUntil;
import rx.internal.operators.OperatorTakeWhile;
import rx.internal.operators.OperatorThrottleFirst;
import rx.internal.operators.OperatorTimeInterval;
import rx.internal.operators.OperatorTimeout;
import rx.internal.operators.OperatorTimeoutWithSelector;
import rx.internal.operators.OperatorTimestamp;
import rx.internal.operators.OperatorToMap;
import rx.internal.operators.OperatorToMultimap;
import rx.internal.operators.OperatorToObservableList;
import rx.internal.operators.OperatorToObservableSortedList;
import rx.internal.operators.OperatorUnsubscribeOn;
import rx.internal.operators.OperatorWindowWithObservable;
import rx.internal.operators.OperatorWindowWithSize;
import rx.internal.operators.OperatorWindowWithStartEndObservable;
import rx.internal.operators.OperatorWindowWithTime;
import rx.internal.operators.OperatorZip;
import rx.internal.operators.OperatorZipIterable;
import rx.observables.BlockingObservable;
import rx.observables.ConnectableObservable;
import rx.observables.GroupedObservable;
import rx.observers.SafeSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

/**
 * The Observable class that implements the Reactive Pattern.
 * <p>
 * This class provides methods for subscribing to the Observable as well as delegate methods to the various
 * Observers.
 * <p>
 * The documentation for this class makes use of marble diagrams. The following legend explains these diagrams:
 * <p>
 * <img width="640" height="301" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/legend.png" alt="">
 * <p>
 * For more information see the <a href="https://github.com/Netflix/RxJava/wiki/Observable">RxJava wiki</a>
 * 
 * @param <T>
 *            the type of the items emitted by the Observable
 */
public class ParallelObservable<T> {
    
    private final Observable<Observable<T>> source;

    private ParallelObservable(Observable<Observable<T>> source) {
        this.source = source;
    }

    public static final <T> ParallelObservable<T> create(
            Observable<Observable<T>> source) {
        return new ParallelObservable<T>(source);
    }

    private <R> ParallelObservable<R> create(
            Func1<Observable<T>, Observable<R>> f) {
        return create(source.map(f));
    }

    /**
     * As per {@link Observable#lift(Operator)} applied to each of the parallel streams.
     * 
     * @param lift the Operator that implements the Observable-operating function to be applied to the source
     *             Observable
     * @return an Observable that is the result of applying the lifted Operator to the source Observable
     * @see <a href="https://github.com/Netflix/RxJava/wiki/Implementing-Your-Own-Operators">RxJava wiki: Implementing Your Own Operators</a>
     * @since 0.17
     */
    public final <R> ParallelObservable<R> lift(final Operator<? extends R, ? super T> lift) {
        return create(new Func1<Observable<T>, Observable<R>>() {

            @Override
            public Observable<R> call(Observable<T> o) {
                return o.lift(lift);
            }
        });
    }
    
    
    /**
     ** As per {@link Observable#compose(Transformer)} applied to each of the parallel streams.
     * 
     * @param transformer implements the function that transforms the source Observable
     * @return the source Observable, transformed by the transformer function
     * @see <a href="https://github.com/Netflix/RxJava/wiki/Implementing-Your-Own-Operators">RxJava wiki: Implementing Your Own Operators</a>
     * @since 0.20
     */
    public <R> ParallelObservable<R> compose(final Transformer<? super T, R> transformer) {
        return create(new Func1<Observable<T>, Observable<R>>() {

            @Override
            public Observable<R> call(Observable<T> o) {
                return o.compose(transformer);
            }
        });
    }
    

    /* *********************************************************************************************************
     * Operators Below Here
     * *********************************************************************************************************
     */

    /**
     * Converts the source {@code Observable<T>} into an {@code Observable<Observable<T>>} that emits the
     * source Observable as its single emission.
     * <p>
     * <img width="640" height="350" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/nest.png" alt="">
     * <dl>
     *  <dt><b>Scheduler:</b></dt>
     *  <dd>{@code nest} does not operate by default on a particular {@link Scheduler}.</dd>
     * </dl>
     * 
     * @return an Observable that emits a single item: the source Observable
     * @since 0.17
     */
    public final ParallelObservable<Observable<T>> nest() {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
            @Override
            public Observable<Observable<T>> call(Observable<T> o) {
                return o.nest();
            }
        });
    }

    public final ParallelObservable<Boolean> all(final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> call(Observable<T> o) {
                                return o.all(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> ambWith(final Observable<? extends T> t1) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.ambWith(t1);
                            }
                        });
    }
    public final ParallelObservable<T> asObservable() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.asObservable();
                            }
                        });
    }
    public final <TClosing> ParallelObservable<List<T>> buffer(final Func0<? extends Observable<? extends TClosing>> bufferClosingSelector) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(bufferClosingSelector);
                            }
                        });
    }
    public final ParallelObservable<List<T>> buffer(final int count) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(count);
                            }
                        });
    }
    public final ParallelObservable<List<T>> buffer(final int count, final int skip) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(count, skip);
                            }
                        });
    }
    public final ParallelObservable<List<T>> buffer(final long timespan, final long timeshift, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(timespan, timeshift, unit);
                            }
                        });
    }
    public final ParallelObservable<List<T>> buffer(final long timespan, final long timeshift, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(timespan, timeshift, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<List<T>> buffer(final long timespan, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(timespan, unit);
                            }
                        });
    }
    public final ParallelObservable<List<T>> buffer(final long timespan, final TimeUnit unit, final int count) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(timespan, unit, count);
                            }
                        });
    }
    public final ParallelObservable<List<T>> buffer(final long timespan, final TimeUnit unit, final int count, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(timespan, unit, count, scheduler);
                            }
                        });
    }
    public final ParallelObservable<List<T>> buffer(final long timespan, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(timespan, unit, scheduler);
                            }
                        });
    }
    public final <TOpening, TClosing> ParallelObservable<List<T>> buffer(final Observable<? extends TOpening> bufferOpenings, final Func1<? super TOpening, ? extends Observable<? extends TClosing>> bufferClosingSelector) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(bufferOpenings, bufferClosingSelector);
                            }
                        });
    }
    public final <B> ParallelObservable<List<T>> buffer(final Observable<B> boundary) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(boundary);
                            }
                        });
    }
    public final <B> ParallelObservable<List<T>> buffer(final Observable<B> boundary, final int initialCapacity) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.buffer(boundary, initialCapacity);
                            }
                        });
    }
    public final ParallelObservable<T> cache() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.cache();
                            }
                        });
    }
    public final ParallelObservable<T> cache(final int capacity) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.cache(capacity);
                            }
                        });
    }
    public final <R> ParallelObservable<R> cast(final Class<R> klass) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.cast(klass);
                            }
                        });
    }
    public final <R> ParallelObservable<R> collect(final R state, final Action2<R, ? super T> collector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.collect(state, collector);
                            }
                        });
    }
    public final <R> ParallelObservable<R> concatMap(final Func1<? super T, ? extends Observable<? extends R>> func) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.concatMap(func);
                            }
                        });
    }
    public final ParallelObservable<T> concatWith(final Observable<? extends T> t1) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.concatWith(t1);
                            }
                        });
    }
    public final ParallelObservable<Boolean> contains(final Object element) {
        return create(new Func1<Observable<T>, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> call(Observable<T> o) {
                                return o.contains(element);
                            }
                        });
    }
    public final ParallelObservable<Integer> count() {
        return create(new Func1<Observable<T>, Observable<Integer>>() {
                            @Override
                            public Observable<Integer> call(Observable<T> o) {
                                return o.count();
                            }
                        });
    }
    public final <U> ParallelObservable<T> debounce(final Func1<? super T, ? extends Observable<U>> debounceSelector) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.debounce(debounceSelector);
                            }
                        });
    }
    public final ParallelObservable<T> debounce(final long timeout, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.debounce(timeout, unit);
                            }
                        });
    }
    public final ParallelObservable<T> debounce(final long timeout, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.debounce(timeout, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> defaultIfEmpty(final T defaultValue) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.defaultIfEmpty(defaultValue);
                            }
                        });
    }
    public final <U> ParallelObservable<T> delay(final Func1<? super T, ? extends Observable<U>> itemDelay) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.delay(itemDelay);
                            }
                        });
    }
    public final ParallelObservable<T> delay(final long delay, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.delay(delay, unit);
                            }
                        });
    }
    public final ParallelObservable<T> delay(final long delay, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.delay(delay, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> delaySubscription(final long delay, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.delaySubscription(delay, unit);
                            }
                        });
    }
    public final ParallelObservable<T> delaySubscription(final long delay, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.delaySubscription(delay, unit, scheduler);
                            }
                        });
    }
    public final <T2> ParallelObservable<T2> dematerialize() {
        return create(new Func1<Observable<T>, Observable<T2>>() {
                            @Override
                            public Observable<T2> call(Observable<T> o) {
                                return o.dematerialize();
                            }
                        });
    }
    public final ParallelObservable<T> distinct() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.distinct();
                            }
                        });
    }
    public final <U> ParallelObservable<T> distinct(final Func1<? super T, ? extends U> keySelector) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.distinct(keySelector);
                            }
                        });
    }
    public final ParallelObservable<T> distinctUntilChanged() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.distinctUntilChanged();
                            }
                        });
    }
    public final <U> ParallelObservable<T> distinctUntilChanged(final Func1<? super T, ? extends U> keySelector) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.distinctUntilChanged(keySelector);
                            }
                        });
    }
    public final ParallelObservable<T> doOnCompleted(final Action0 onCompleted) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.doOnCompleted(onCompleted);
                            }
                        });
    }
    public final ParallelObservable<T> doOnEach(final Action1<Notification<? super T>> onNotification) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.doOnEach(onNotification);
                            }
                        });
    }
    public final ParallelObservable<T> doOnEach(final Observer<? super T> observer) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.doOnEach(observer);
                            }
                        });
    }
    public final ParallelObservable<T> doOnError(final Action1<Throwable> onError) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.doOnError(onError);
                            }
                        });
    }
    public final ParallelObservable<T> doOnNext(final Action1<? super T> onNext) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.doOnNext(onNext);
                            }
                        });
    }
    public final ParallelObservable<T> doOnSubscribe(final Action0 subscribe) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.doOnSubscribe(subscribe);
                            }
                        });
    }
    public final ParallelObservable<T> doOnTerminate(final Action0 onTerminate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.doOnTerminate(onTerminate);
                            }
                        });
    }
    public final ParallelObservable<T> doOnUnsubscribe(final Action0 unsubscribe) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.doOnUnsubscribe(unsubscribe);
                            }
                        });
    }
    public final ParallelObservable<T> elementAt(final int index) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.elementAt(index);
                            }
                        });
    }
    public final ParallelObservable<T> elementAtOrDefault(final int index, final T defaultValue) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.elementAtOrDefault(index, defaultValue);
                            }
                        });
    }
    public final ParallelObservable<Boolean> exists(final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> call(Observable<T> o) {
                                return o.exists(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> filter(final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.filter(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> finallyDo(final Action0 action) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.finallyDo(action);
                            }
                        });
    }
    public final ParallelObservable<T> first() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.first();
                            }
                        });
    }
    public final ParallelObservable<T> first(final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.first(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> firstOrDefault(final T defaultValue) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.firstOrDefault(defaultValue);
                            }
                        });
    }
    public final ParallelObservable<T> firstOrDefault(final T defaultValue, final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.firstOrDefault(defaultValue, predicate);
                            }
                        });
    }
    public final <R> ParallelObservable<R> flatMap(final Func1<? super T, ? extends Observable<? extends R>> func) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.flatMap(func);
                            }
                        });
    }
    public final <R> ParallelObservable<R> flatMap(final Func1<? super T, ? extends Observable<? extends R>> onNext, final Func1<? super Throwable, ? extends Observable<? extends R>> onError, final Func0<? extends Observable<? extends R>> onCompleted) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.flatMap(onNext, onError, onCompleted);
                            }
                        });
    }
    public final <U, R> ParallelObservable<R> flatMap(final Func1<? super T, ? extends Observable<? extends U>> collectionSelector, final Func2<? super T, ? super U, ? extends R> resultSelector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.flatMap(collectionSelector, resultSelector);
                            }
                        });
    }
    public final <R> ParallelObservable<R> flatMapIterable(final Func1<? super T, ? extends Iterable<? extends R>> collectionSelector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.flatMapIterable(collectionSelector);
                            }
                        });
    }
    public final <U, R> ParallelObservable<R> flatMapIterable(final Func1<? super T, ? extends Iterable<? extends U>> collectionSelector, final Func2<? super T, ? super U, ? extends R> resultSelector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.flatMapIterable(collectionSelector, resultSelector);
                            }
                        });
    }
    public final <K, R> ParallelObservable<GroupedObservable<K, R>> groupBy(final Func1<? super T, ? extends K> keySelector, final Func1<? super T, ? extends R> elementSelector) {
        return create(new Func1<Observable<T>, Observable<GroupedObservable<K, R>>>() {
                            @Override
                            public Observable<GroupedObservable<K, R>> call(Observable<T> o) {
                                return o.groupBy(keySelector, elementSelector);
                            }
                        });
    }
    public final <K> ParallelObservable<GroupedObservable<K, T>> groupBy(final Func1<? super T, ? extends K> keySelector) {
        return create(new Func1<Observable<T>, Observable<GroupedObservable<K, T>>>() {
                            @Override
                            public Observable<GroupedObservable<K, T>> call(Observable<T> o) {
                                return o.groupBy(keySelector);
                            }
                        });
    }
    public final <TKey, TDuration> ParallelObservable<GroupedObservable<TKey, T>> groupByUntil(final Func1<? super T, ? extends TKey> keySelector, final Func1<? super GroupedObservable<TKey, T>, ? extends Observable<? extends TDuration>> durationSelector) {
        return create(new Func1<Observable<T>, Observable<GroupedObservable<TKey, T>>>() {
                            @Override
                            public Observable<GroupedObservable<TKey, T>> call(Observable<T> o) {
                                return o.groupByUntil(keySelector, durationSelector);
                            }
                        });
    }
    public final <TKey, TValue, TDuration> ParallelObservable<GroupedObservable<TKey, TValue>> groupByUntil(final Func1<? super T, ? extends TKey> keySelector, final Func1<? super T, ? extends TValue> valueSelector, final Func1<? super GroupedObservable<TKey, TValue>, ? extends Observable<? extends TDuration>> durationSelector) {
        return create(new Func1<Observable<T>, Observable<GroupedObservable<TKey, TValue>>>() {
                            @Override
                            public Observable<GroupedObservable<TKey, TValue>> call(Observable<T> o) {
                                return o.groupByUntil(keySelector, valueSelector, durationSelector);
                            }
                        });
    }
    public final <T2, D1, D2, R> ParallelObservable<R> groupJoin(final Observable<T2> right, final Func1<? super T, ? extends Observable<D1>> leftDuration, final Func1<? super T2, ? extends Observable<D2>> rightDuration, final Func2<? super T, ? super Observable<T2>, ? extends R> resultSelector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.groupJoin(right, leftDuration, rightDuration, resultSelector);
                            }
                        });
    }
    public final ParallelObservable<T> ignoreElements() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.ignoreElements();
                            }
                        });
    }
    public final ParallelObservable<Boolean> isEmpty() {
        return create(new Func1<Observable<T>, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> call(Observable<T> o) {
                                return o.isEmpty();
                            }
                        });
    }
    public final <TRight, TLeftDuration, TRightDuration, R> ParallelObservable<R> join(final Observable<TRight> right, final Func1<T, Observable<TLeftDuration>> leftDurationSelector, final Func1<TRight, Observable<TRightDuration>> rightDurationSelector, final Func2<T, TRight, R> resultSelector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.join(right, leftDurationSelector, rightDurationSelector, resultSelector);
                            }
                        });
    }
    public final ParallelObservable<T> last() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.last();
                            }
                        });
    }
    public final ParallelObservable<T> last(final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.last(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> lastOrDefault(final T defaultValue) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.lastOrDefault(defaultValue);
                            }
                        });
    }
    public final ParallelObservable<T> lastOrDefault(final T defaultValue, final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.lastOrDefault(defaultValue, predicate);
                            }
                        });
    }
    public final ParallelObservable<T> limit(final int num) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.limit(num);
                            }
                        });
    }
    public final ParallelObservable<Long> longCount() {
        return create(new Func1<Observable<T>, Observable<Long>>() {
                            @Override
                            public Observable<Long> call(Observable<T> o) {
                                return o.longCount();
                            }
                        });
    }
    public final <R> ParallelObservable<R> map(final Func1<? super T, ? extends R> func) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.map(func);
                            }
                        });
    }
    public final ParallelObservable<Notification<T>> materialize() {
        return create(new Func1<Observable<T>, Observable<Notification<T>>>() {
                            @Override
                            public Observable<Notification<T>> call(Observable<T> o) {
                                return o.materialize();
                            }
                        });
    }
    public final <R> ParallelObservable<R> mergeMap(final Func1<? super T, ? extends Observable<? extends R>> func) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.mergeMap(func);
                            }
                        });
    }
    public final <R> ParallelObservable<R> mergeMap(final Func1<? super T, ? extends Observable<? extends R>> onNext, final Func1<? super Throwable, ? extends Observable<? extends R>> onError, final Func0<? extends Observable<? extends R>> onCompleted) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.mergeMap(onNext, onError, onCompleted);
                            }
                        });
    }
    public final <U, R> ParallelObservable<R> mergeMap(final Func1<? super T, ? extends Observable<? extends U>> collectionSelector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.mergeMap(collectionSelector);
                            }
                        });
    }
    public final <R> ParallelObservable<R> mergeMapIterable(final Func1<? super T, ? extends Iterable<? extends R>> collectionSelector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.mergeMapIterable(collectionSelector);
                            }
                        });
    }
    public final <U, R> ParallelObservable<R> mergeMapIterable(final Observable<R> mergeMap, final Func1<? super T, ? extends Observable<? extends U>> collectionSelector, final Func2<? super T, ? super U, ? extends R> resultSelector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.mergeMapIterable(mergeMap, collectionSelector, resultSelector);
                            }
                        });
    }
    public final ParallelObservable<T> mergeWith(final Observable<? extends T> t1) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.mergeWith(t1);
                            }
                        });
    }
    public final <TIntermediate, TResult> ParallelObservable<TResult> multicast(final Func0<? extends Subject<? super T, ? extends TIntermediate>> subjectFactory, final Func1<? super Observable<TIntermediate>, ? extends Observable<TResult>> selector) {
        return create(new Func1<Observable<T>, Observable<TResult>>() {
                            @Override
                            public Observable<TResult> call(Observable<T> o) {
                                return o.multicast(subjectFactory, selector);
                            }
                        });
    }
    public final <R> ParallelConnectableObservable<R> multicast(final Subject<? super T, ? extends R> subject) {
        return create(new Func1<Observable<T>, ConnectableObservable<R>>() {
                            @Override
                            public ConnectableObservable<R> call(Observable<T> o) {
                                return o.multicast(subject);
                            }
                        });
    }
    public final <R> ParallelConnectableObservable<R> multicast(final Func0<? extends Subject<? super T, ? extends R>> subjectFactory) {
        return create(new Func1<Observable<T>, ConnectableObservable<R>>() {
                            @Override
                            public ConnectableObservable<R> call(Observable<T> o) {
                                return o.multicast(subjectFactory);
                            }
                        });
    }
    public final ParallelObservable<T> observeOn(final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.observeOn(scheduler);
                            }
                        });
    }
    public final <R> ParallelObservable<R> ofType(final Class<R> klass) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.ofType(klass);
                            }
                        });
    }
    public final ParallelObservable<T> onBackpressureBuffer() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.onBackpressureBuffer();
                            }
                        });
    }
    public final ParallelObservable<T> onBackpressureDrop() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.onBackpressureDrop();
                            }
                        });
    }
    public final ParallelObservable<T> onErrorResumeNext(final Func1<Throwable, ? extends Observable<? extends T>> resumeFunction) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.onErrorResumeNext(resumeFunction);
                            }
                        });
    }
    public final ParallelObservable<T> onErrorResumeNext(final Observable<? extends T> resumeSequence) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.onErrorResumeNext(resumeSequence);
                            }
                        });
    }
    public final ParallelObservable<T> onErrorReturn(final Func1<Throwable, ? extends T> resumeFunction) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.onErrorReturn(resumeFunction);
                            }
                        });
    }
    public final ParallelObservable<T> onErrorFlatMap(final Func1<OnErrorThrowable, ? extends Observable<? extends T>> resumeFunction) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.onErrorFlatMap(resumeFunction);
                            }
                        });
    }
    public final ParallelObservable<T> onExceptionResumeNext(final Observable<? extends T> resumeSequence) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.onExceptionResumeNext(resumeSequence);
                            }
                        });
    }
    public final <R> ParallelObservable<R> parallel(final Func1<Observable<T>, Observable<R>> f) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.parallel(f);
                            }
                        });
    }
    public final <R> ParallelObservable<R> parallel(final Func1<Observable<T>, Observable<R>> f, final Scheduler s) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.parallel(f, s);
                            }
                        });
    }
    public final ParallelConnectableObservable<T> publish() {
        return create(new Func1<Observable<T>, ConnectableObservable<T>>() {
                            @Override
                            public ConnectableObservable<T> call(Observable<T> o) {
                                return o.publish();
                            }
                        });
    }
    public final <R> ParallelObservable<R> publish(final Func1<? super Observable<T>, ? extends Observable<R>> selector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.publish(selector);
                            }
                        });
    }
    public final <R> ParallelObservable<R> publish(final Func1<? super Observable<T>, ? extends Observable<R>> selector, final T initialValue) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.publish(selector, initialValue);
                            }
                        });
    }
    public final ParallelConnectableObservable<T> publish(final T initialValue) {
        return create(new Func1<Observable<T>, ConnectableObservable<T>>() {
                            @Override
                            public ConnectableObservable<T> call(Observable<T> o) {
                                return o.publish(initialValue);
                            }
                        });
    }
    public final ParallelConnectableObservable<T> publishLast() {
        return create(new Func1<Observable<T>, ConnectableObservable<T>>() {
                            @Override
                            public ConnectableObservable<T> call(Observable<T> o) {
                                return o.publishLast();
                            }
                        });
    }
    public final <R> ParallelObservable<R> publishLast(final Func1<? super Observable<T>, ? extends Observable<R>> selector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.publishLast(selector);
                            }
                        });
    }
    public final ParallelObservable<T> reduce(final Func2<T, T, T> accumulator) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.reduce(accumulator);
                            }
                        });
    }
    public final <R> ParallelObservable<R> reduce(final R initialValue, final Func2<R, ? super T, R> accumulator) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.reduce(initialValue, accumulator);
                            }
                        });
    }
    public final ParallelObservable<T> repeat() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.repeat();
                            }
                        });
    }
    public final ParallelObservable<T> repeat(final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.repeat(scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> repeat(final long count) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.repeat(count);
                            }
                        });
    }
    public final ParallelObservable<T> repeat(final long count, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.repeat(count, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> repeatWhen(final Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>> notificationHandler, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.repeatWhen(notificationHandler, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> repeatWhen(final Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>> notificationHandler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.repeatWhen(notificationHandler);
                            }
                        });
    }
    public final ParallelConnectableObservable<T> replay() {
        return create(new Func1<Observable<T>, ConnectableObservable<T>>() {
                            @Override
                            public ConnectableObservable<T> call(Observable<T> o) {
                                return o.replay();
                            }
                        });
    }
    public final <R> ParallelObservable<R> replay(final Func1<? super Observable<T>, ? extends Observable<R>> selector) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.replay(selector);
                            }
                        });
    }
    public final <R> ParallelObservable<R> replay(final Func1<? super Observable<T>, ? extends Observable<R>> selector, final int bufferSize) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.replay(selector, bufferSize);
                            }
                        });
    }
    public final <R> ParallelObservable<R> replay(final Func1<? super Observable<T>, ? extends Observable<R>> selector, final int bufferSize, final long time, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.replay(selector, bufferSize, time, unit);
                            }
                        });
    }
    public final <R> ParallelObservable<R> replay(final Func1<? super Observable<T>, ? extends Observable<R>> selector, final int bufferSize, final long time, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.replay(selector, bufferSize, time, unit, scheduler);
                            }
                        });
    }
    public final <R> ParallelObservable<R> replay(final Func1<? super Observable<T>, ? extends Observable<R>> selector, final int bufferSize, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.replay(selector, bufferSize, scheduler);
                            }
                        });
    }
    public final <R> ParallelObservable<R> replay(final Func1<? super Observable<T>, ? extends Observable<R>> selector, final long time, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.replay(selector, time, unit);
                            }
                        });
    }
    public final <R> ParallelObservable<R> replay(final Func1<? super Observable<T>, ? extends Observable<R>> selector, final long time, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.replay(selector, time, unit, scheduler);
                            }
                        });
    }
    public final <R> ParallelObservable<R> replay(final Func1<? super Observable<T>, ? extends Observable<R>> selector, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.replay(selector, scheduler);
                            }
                        });
    }
    public final ParallelConnectableObservable<T> replay(final int bufferSize) {
        return create(new Func1<Observable<T>, ConnectableObservable<T>>() {
                            @Override
                            public ConnectableObservable<T> call(Observable<T> o) {
                                return o.replay(bufferSize);
                            }
                        });
    }
    public final ParallelConnectableObservable<T> replay(final int bufferSize, final long time, final TimeUnit unit) {
        return create(new Func1<Observable<T>, ConnectableObservable<T>>() {
                            @Override
                            public ConnectableObservable<T> call(Observable<T> o) {
                                return o.replay(bufferSize, time, unit);
                            }
                        });
    }
    public final ParallelConnectableObservable<T> replay(final int bufferSize, final long time, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, ConnectableObservable<T>>() {
                            @Override
                            public ConnectableObservable<T> call(Observable<T> o) {
                                return o.replay(bufferSize, time, unit, scheduler);
                            }
                        });
    }
    public final ParallelConnectableObservable<T> replay(final int bufferSize, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, ConnectableObservable<T>>() {
                            @Override
                            public ConnectableObservable<T> call(Observable<T> o) {
                                return o.replay(bufferSize, scheduler);
                            }
                        });
    }
    public final ParallelConnectableObservable<T> replay(final long time, final TimeUnit unit) {
        return create(new Func1<Observable<T>, ConnectableObservable<T>>() {
                            @Override
                            public ConnectableObservable<T> call(Observable<T> o) {
                                return o.replay(time, unit);
                            }
                        });
    }
    public final ParallelConnectableObservable<T> replay(final long time, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, ConnectableObservable<T>>() {
                            @Override
                            public ConnectableObservable<T> call(Observable<T> o) {
                                return o.replay(time, unit, scheduler);
                            }
                        });
    }
    public final ParallelConnectableObservable<T> replay(final Scheduler scheduler) {
        return create(new Func1<Observable<T>, ConnectableObservable<T>>() {
                            @Override
                            public ConnectableObservable<T> call(Observable<T> o) {
                                return o.replay(scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> retry() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.retry();
                            }
                        });
    }
    public final ParallelObservable<T> retry(final long count) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.retry(count);
                            }
                        });
    }
    public final ParallelObservable<T> retry(final Func2<Integer, Throwable, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.retry(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> retryWhen(final Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>> notificationHandler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.retryWhen(notificationHandler);
                            }
                        });
    }
    public final ParallelObservable<T> retryWhen(final Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>> notificationHandler, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.retryWhen(notificationHandler, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> sample(final long period, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.sample(period, unit);
                            }
                        });
    }
    public final ParallelObservable<T> sample(final long period, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.sample(period, unit, scheduler);
                            }
                        });
    }
    public final <U> ParallelObservable<T> sample(final Observable<U> sampler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.sample(sampler);
                            }
                        });
    }
    public final ParallelObservable<T> scan(final Func2<T, T, T> accumulator) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.scan(accumulator);
                            }
                        });
    }
    public final <R> ParallelObservable<R> scan(final R initialValue, final Func2<R, ? super T, R> accumulator) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.scan(initialValue, accumulator);
                            }
                        });
    }
    public final ParallelObservable<T> serialize() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.serialize();
                            }
                        });
    }
    public final ParallelObservable<T> share() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.share();
                            }
                        });
    }
    public final ParallelObservable<T> single() {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.single();
                            }
                        });
    }
    public final ParallelObservable<T> single(final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.single(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> singleOrDefault(final T defaultValue) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.singleOrDefault(defaultValue);
                            }
                        });
    }
    public final ParallelObservable<T> singleOrDefault(final T defaultValue, final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.singleOrDefault(defaultValue, predicate);
                            }
                        });
    }
    public final ParallelObservable<T> skip(final int num) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.skip(num);
                            }
                        });
    }
    public final ParallelObservable<T> skip(final long time, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.skip(time, unit);
                            }
                        });
    }
    public final ParallelObservable<T> skip(final long time, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.skip(time, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> skipLast(final int count) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.skipLast(count);
                            }
                        });
    }
    public final ParallelObservable<T> skipLast(final long time, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.skipLast(time, unit);
                            }
                        });
    }
    public final ParallelObservable<T> skipLast(final long time, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.skipLast(time, unit, scheduler);
                            }
                        });
    }
    public final <U> ParallelObservable<T> skipUntil(final Observable<U> other) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.skipUntil(other);
                            }
                        });
    }
    public final ParallelObservable<T> skipWhile(final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.skipWhile(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> skipWhileWithIndex(final Func2<? super T, Integer, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.skipWhileWithIndex(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final Observable<T> values) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(values);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final Iterable<T> values) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(values);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final Iterable<T> values, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(values, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final T t1) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(t1);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final T t1, final T t2) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(t1, t2);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final T t1, final T t2, final T t3) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(t1, t2, t3);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final T t1, final T t2, final T t3, final T t4) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(t1, t2, t3, t4);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final T t1, final T t2, final T t3, final T t4, final T t5) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(t1, t2, t3, t4, t5);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final T t1, final T t2, final T t3, final T t4, final T t5, final T t6) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(t1, t2, t3, t4, t5, t6);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final T t1, final T t2, final T t3, final T t4, final T t5, final T t6, final T t7) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(t1, t2, t3, t4, t5, t6, t7);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final T t1, final T t2, final T t3, final T t4, final T t5, final T t6, final T t7, final T t8) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(t1, t2, t3, t4, t5, t6, t7, t8);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final T t1, final T t2, final T t3, final T t4, final T t5, final T t6, final T t7, final T t8, final T t9) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(t1, t2, t3, t4, t5, t6, t7, t8, t9);
                            }
                        });
    }
    public final ParallelObservable<T> startWith(final T[] values, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.startWith(values, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> subscribeOn(final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.subscribeOn(scheduler);
                            }
                        });
    }
    public final <R> ParallelObservable<R> switchMap(final Func1<? super T, ? extends Observable<? extends R>> func) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.switchMap(func);
                            }
                        });
    }
    public final ParallelObservable<T> take(final int num) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.take(num);
                            }
                        });
    }
    public final ParallelObservable<T> take(final long time, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.take(time, unit);
                            }
                        });
    }
    public final ParallelObservable<T> take(final long time, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.take(time, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> takeFirst(final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.takeFirst(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> takeLast(final int count) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.takeLast(count);
                            }
                        });
    }
    public final ParallelObservable<T> takeLast(final int count, final long time, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.takeLast(count, time, unit);
                            }
                        });
    }
    public final ParallelObservable<T> takeLast(final int count, final long time, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.takeLast(count, time, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> takeLast(final long time, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.takeLast(time, unit);
                            }
                        });
    }
    public final ParallelObservable<T> takeLast(final long time, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.takeLast(time, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<List<T>> takeLastBuffer(final int count) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.takeLastBuffer(count);
                            }
                        });
    }
    public final ParallelObservable<List<T>> takeLastBuffer(final int count, final long time, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.takeLastBuffer(count, time, unit);
                            }
                        });
    }
    public final ParallelObservable<List<T>> takeLastBuffer(final int count, final long time, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.takeLastBuffer(count, time, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<List<T>> takeLastBuffer(final long time, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.takeLastBuffer(time, unit);
                            }
                        });
    }
    public final ParallelObservable<List<T>> takeLastBuffer(final long time, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.takeLastBuffer(time, unit, scheduler);
                            }
                        });
    }
    public final <E> ParallelObservable<T> takeUntil(final Observable<? extends E> other) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.takeUntil(other);
                            }
                        });
    }
    public final ParallelObservable<T> takeWhile(final Func1<? super T, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.takeWhile(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> takeWhileWithIndex(final Func2<? super T, ? super Integer, Boolean> predicate) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.takeWhileWithIndex(predicate);
                            }
                        });
    }
    public final ParallelObservable<T> throttleFirst(final long windowDuration, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.throttleFirst(windowDuration, unit);
                            }
                        });
    }
    public final ParallelObservable<T> throttleFirst(final long skipDuration, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.throttleFirst(skipDuration, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> throttleLast(final long intervalDuration, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.throttleLast(intervalDuration, unit);
                            }
                        });
    }
    public final ParallelObservable<T> throttleLast(final long intervalDuration, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.throttleLast(intervalDuration, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> throttleWithTimeout(final long timeout, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.throttleWithTimeout(timeout, unit);
                            }
                        });
    }
    public final ParallelObservable<T> throttleWithTimeout(final long timeout, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.throttleWithTimeout(timeout, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<TimeInterval<T>> timeInterval() {
        return create(new Func1<Observable<T>, Observable<TimeInterval<T>>>() {
                            @Override
                            public Observable<TimeInterval<T>> call(Observable<T> o) {
                                return o.timeInterval();
                            }
                        });
    }
    public final ParallelObservable<TimeInterval<T>> timeInterval(final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<TimeInterval<T>>>() {
                            @Override
                            public Observable<TimeInterval<T>> call(Observable<T> o) {
                                return o.timeInterval(scheduler);
                            }
                        });
    }
    public final <U, V> ParallelObservable<T> timeout(final Func0<? extends Observable<U>> firstTimeoutSelector, final Func1<? super T, ? extends Observable<V>> timeoutSelector) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.timeout(firstTimeoutSelector, timeoutSelector);
                            }
                        });
    }
    public final <U, V> ParallelObservable<T> timeout(final Func0<? extends Observable<U>> firstTimeoutSelector, final Func1<? super T, ? extends Observable<V>> timeoutSelector, final Observable<? extends T> other) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.timeout(firstTimeoutSelector, timeoutSelector, other);
                            }
                        });
    }
    public final <V> ParallelObservable<T> timeout(final Func1<? super T, ? extends Observable<V>> timeoutSelector) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.timeout(timeoutSelector);
                            }
                        });
    }
    public final <V> ParallelObservable<T> timeout(final Func1<? super T, ? extends Observable<V>> timeoutSelector, final Observable<? extends T> other) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.timeout(timeoutSelector, other);
                            }
                        });
    }
    public final ParallelObservable<T> timeout(final long timeout, final TimeUnit timeUnit) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.timeout(timeout, timeUnit);
                            }
                        });
    }
    public final ParallelObservable<T> timeout(final long timeout, final TimeUnit timeUnit, final Observable<? extends T> other) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.timeout(timeout, timeUnit, other);
                            }
                        });
    }
    public final ParallelObservable<T> timeout(final long timeout, final TimeUnit timeUnit, final Observable<? extends T> other, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.timeout(timeout, timeUnit, other, scheduler);
                            }
                        });
    }
    public final ParallelObservable<T> timeout(final long timeout, final TimeUnit timeUnit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.timeout(timeout, timeUnit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<Timestamped<T>> timestamp() {
        return create(new Func1<Observable<T>, Observable<Timestamped<T>>>() {
                            @Override
                            public Observable<Timestamped<T>> call(Observable<T> o) {
                                return o.timestamp();
                            }
                        });
    }
    public final ParallelObservable<Timestamped<T>> timestamp(final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<Timestamped<T>>>() {
                            @Override
                            public Observable<Timestamped<T>> call(Observable<T> o) {
                                return o.timestamp(scheduler);
                            }
                        });
    }
    public final ParallelBlockingObservable<T> toBlockingObservable() {
        return create(new Func1<Observable<T>, BlockingObservable<T>>() {
                            @Override
                            public BlockingObservable<T> call(Observable<T> o) {
                                return o.toBlockingObservable();
                            }
                        });
    }
    public final ParallelBlockingObservable<T> toBlocking() {
        return create(new Func1<Observable<T>, BlockingObservable<T>>() {
                            @Override
                            public BlockingObservable<T> call(Observable<T> o) {
                                return o.toBlocking();
                            }
                        });
    }
    public final ParallelObservable<List<T>> toList() {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.toList();
                            }
                        });
    }
    public final <K> ParallelObservable<Map<K, T>> toMap(final Func1<? super T, ? extends K> keySelector) {
        return create(new Func1<Observable<T>, Observable<Map<K, T>>>() {
                            @Override
                            public Observable<Map<K, T>> call(Observable<T> o) {
                                return o.toMap(keySelector);
                            }
                        });
    }
    public final <K, V> ParallelObservable<Map<K, V>> toMap(final Func1<? super T, ? extends K> keySelector, final Func1<? super T, ? extends V> valueSelector) {
        return create(new Func1<Observable<T>, Observable<Map<K, V>>>() {
                            @Override
                            public Observable<Map<K, V>> call(Observable<T> o) {
                                return o.toMap(keySelector, valueSelector);
                            }
                        });
    }
    public final <K, V> ParallelObservable<Map<K, V>> toMap(final Func1<? super T, ? extends K> keySelector, final Func1<? super T, ? extends V> valueSelector, final Func0<? extends Map<K, V>> mapFactory) {
        return create(new Func1<Observable<T>, Observable<Map<K, V>>>() {
                            @Override
                            public Observable<Map<K, V>> call(Observable<T> o) {
                                return o.toMap(keySelector, valueSelector, mapFactory);
                            }
                        });
    }
    public final <K> ParallelObservable<Map<K, Collection<T>>> toMultimap(final Func1<? super T, ? extends K> keySelector) {
        return create(new Func1<Observable<T>, Observable<Map<K, Collection<T>>>>() {
                            @Override
                            public Observable<Map<K, Collection<T>>> call(Observable<T> o) {
                                return o.toMultimap(keySelector);
                            }
                        });
    }
    public final <K, V> ParallelObservable<Map<K, Collection<V>>> toMultimap(final Func1<? super T, ? extends K> keySelector, final Func1<? super T, ? extends V> valueSelector) {
        return create(new Func1<Observable<T>, Observable<Map<K, Collection<V>>>>() {
                            @Override
                            public Observable<Map<K, Collection<V>>> call(Observable<T> o) {
                                return o.toMultimap(keySelector, valueSelector);
                            }
                        });
    }
    public final <K, V> ParallelObservable<Map<K, Collection<V>>> toMultimap(final Func1<? super T, ? extends K> keySelector, final Func1<? super T, ? extends V> valueSelector, final Func0<? extends Map<K, Collection<V>>> mapFactory) {
        return create(new Func1<Observable<T>, Observable<Map<K, Collection<V>>>>() {
                            @Override
                            public Observable<Map<K, Collection<V>>> call(Observable<T> o) {
                                return o.toMultimap(keySelector, valueSelector, mapFactory);
                            }
                        });
    }
    public final <K, V> ParallelObservable<Map<K, Collection<V>>> toMultimap(final Func1<? super T, ? extends K> keySelector, final Func1<? super T, ? extends V> valueSelector, final Func0<? extends Map<K, Collection<V>>> mapFactory, final Func1<? super K, ? extends Collection<V>> collectionFactory) {
        return create(new Func1<Observable<T>, Observable<Map<K, Collection<V>>>>() {
                            @Override
                            public Observable<Map<K, Collection<V>>> call(Observable<T> o) {
                                return o.toMultimap(keySelector, valueSelector, mapFactory, collectionFactory);
                            }
                        });
    }
    public final ParallelObservable<List<T>> toSortedList() {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.toSortedList();
                            }
                        });
    }
    public final ParallelObservable<List<T>> toSortedList(final Func2<? super T, ? super T, Integer> sortFunction) {
        return create(new Func1<Observable<T>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(Observable<T> o) {
                                return o.toSortedList(sortFunction);
                            }
                        });
    }
    public final ParallelObservable<T> unsubscribeOn(final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Observable<T> o) {
                                return o.unsubscribeOn(scheduler);
                            }
                        });
    }
    public final <TClosing> ParallelObservable<Observable<T>> window(final Func0<? extends Observable<? extends TClosing>> closingSelector) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(closingSelector);
                            }
                        });
    }
    public final ParallelObservable<Observable<T>> window(final int count) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(count);
                            }
                        });
    }
    public final ParallelObservable<Observable<T>> window(final int count, final int skip) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(count, skip);
                            }
                        });
    }
    public final ParallelObservable<Observable<T>> window(final long timespan, final long timeshift, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(timespan, timeshift, unit);
                            }
                        });
    }
    public final ParallelObservable<Observable<T>> window(final long timespan, final long timeshift, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(timespan, timeshift, unit, scheduler);
                            }
                        });
    }
    public final ParallelObservable<Observable<T>> window(final long timespan, final long timeshift, final TimeUnit unit, final int count, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(timespan, timeshift, unit, count, scheduler);
                            }
                        });
    }
    public final ParallelObservable<Observable<T>> window(final long timespan, final TimeUnit unit) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(timespan, unit);
                            }
                        });
    }
    public final ParallelObservable<Observable<T>> window(final long timespan, final TimeUnit unit, final int count) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(timespan, unit, count);
                            }
                        });
    }
    public final ParallelObservable<Observable<T>> window(final long timespan, final TimeUnit unit, final int count, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(timespan, unit, count, scheduler);
                            }
                        });
    }
    public final ParallelObservable<Observable<T>> window(final long timespan, final TimeUnit unit, final Scheduler scheduler) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(timespan, unit, scheduler);
                            }
                        });
    }
    public final <TOpening, TClosing> ParallelObservable<Observable<T>> window(final Observable<? extends TOpening> windowOpenings, final Func1<? super TOpening, ? extends Observable<? extends TClosing>> closingSelector) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(windowOpenings, closingSelector);
                            }
                        });
    }
    public final <U> ParallelObservable<Observable<T>> window(final Observable<U> boundary) {
        return create(new Func1<Observable<T>, Observable<Observable<T>>>() {
                            @Override
                            public Observable<Observable<T>> call(Observable<T> o) {
                                return o.window(boundary);
                            }
                        });
    }
    public final <T2, R> ParallelObservable<R> zip(final Iterable<? extends T2> other, final Func2<? super T, ? super T2, ? extends R> zipFunction) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.zip(other, zipFunction);
                            }
                        });
    }
    public final <T2, R> ParallelObservable<R> zipWith(final Iterable<? extends T2> other, final Func2<? super T, ? super T2, ? extends R> zipFunction) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.zipWith(other, zipFunction);
                            }
                        });
    }
    public final <T2, R> ParallelObservable<R> zip(final Observable<? extends T2> other, final Func2<? super T, ? super T2, ? extends R> zipFunction) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.zip(other, zipFunction);
                            }
                        });
    }
    public final <T2, R> ParallelObservable<R> zipWith(final Observable<? extends T2> other, final Func2<? super T, ? super T2, ? extends R> zipFunction) {
        return create(new Func1<Observable<T>, Observable<R>>() {
                            @Override
                            public Observable<R> call(Observable<T> o) {
                                return o.zipWith(other, zipFunction);
                            }
                        });
    }

    

}
