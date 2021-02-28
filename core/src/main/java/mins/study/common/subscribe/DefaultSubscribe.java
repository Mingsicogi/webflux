package mins.study.common.subscribe;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class DefaultSubscribe<T> implements Subscriber<T> {
    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        log.info("DefaultSubscribe onNext : {}", t);
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {
        log.info("### default subscriber work completed. ###");
    }
}
