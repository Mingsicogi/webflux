package mins.study.config;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import mins.study.common.ReactiveMongoCommonService;
import mins.study.entity.LogEntity;
import mins.study.repository.LogMongoRepository;
import org.bson.BsonValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@Slf4j
@SpringBootTest
@ActiveProfiles("core")
public class CRUDTest {

    @Test
    void insertDataTestBySpringDataMethod(@Autowired LogMongoRepository logMongoRepository) {
        String requestParameter = UUID.randomUUID().toString();
        logMongoRepository.insert(new LogEntity("127.0.0.1", requestParameter, "Post")).subscribe();

        logMongoRepository.findAll().any(logData -> requestParameter.equals(logData.getRequestParameter())).subscribe(result -> {
            Assertions.assertEquals(Boolean.TRUE, result);
        });
    }

    @Test
    void insertDataTestByCustomMethod(@Autowired ReactiveMongoCommonService reactiveMongoCommonService) {
        String requestParameter = UUID.randomUUID().toString();
        String testCollectionName = "CommonLog";

        reactiveMongoCommonService.insert(testCollectionName, new LogEntity("127.0.0.1", requestParameter, "Post"), LogEntity.class).subscribe(new Subscriber<InsertOneResult>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                this.subscription = s;
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(InsertOneResult insertOneResult) {
                BsonValue insertedId = insertOneResult.getInsertedId();
                log.info("### insertedId : {}", insertedId);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                log.info("### insert finish ###");
            }
        });

        reactiveMongoCommonService.selectOneByPk(testCollectionName, requestParameter, LogEntity.class).subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(LogEntity t) {
                log.info("{}", t);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                log.info("### Found Data ###");
            }
        });
    }

    @SpringBootApplication(scanBasePackages = {"mins.study"})
    static class MainTest {
        public static void main(String[] args) {

        }
    }
}
