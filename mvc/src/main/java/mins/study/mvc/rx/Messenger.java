package mins.study.mvc.rx;

import lombok.SneakyThrows;
import rx.Observable;
import rx.Observer;
import rx.observables.AsyncOnSubscribe;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class Messenger {

    public Observable<Message> sendMessage(String message, String fromId) {
        return Observable.create(new AsyncOnSubscribe<>() {
            @Override
            protected Object generateState() {
                return Boolean.TRUE;
            }

            @Override
            protected Object next(Object state, long requested, Observer<Observable<? extends Message>> observer) {
                if(state.equals(Boolean.TRUE)) {
                    observer.onNext(Observable.just(new Message(UUID.randomUUID().toString(), message, fromId, LocalDateTime.now())));
                    observer.onCompleted();
                } else {
                    observer.onError(new RuntimeException("ERROR!! Connection Fail!!!"));
                }

                return Boolean.TRUE;
            }
        });
    }

    public Observer<Message> listener(String receiverId) {
        return new Observer<>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("ERROR : " + e.getMessage());
            }

            @SneakyThrows
            @Override
            public void onNext(Message message) {
                if("haha".equals(message.getFromId())) {
                    Thread.sleep(5000);
                }
                System.out.println("[ " + receiverId + " ] : " + message.toString());
            }
        };
    }
}
