package mins.study.config;

import com.mongodb.connection.ServerDescription;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Operators;

import java.util.List;

@Slf4j
@SpringBootTest
@ActiveProfiles("core")
class ReactiveMongoConfigurationTest {

    @Test
    void getMongoClient(@Autowired MongoClient mongoClient) {
        List<ServerDescription> serverDescriptions = mongoClient.getClusterDescription().getServerDescriptions();
        Assertions.assertTrue(serverDescriptions.size() >= 1);

        for (ServerDescription serverDescription : mongoClient.getClusterDescription().getServerDescriptions()) {
            log.info(serverDescription.toString());
        }
    }

    @Test
    void getMongoDatabase(@Autowired MongoDatabase mongoDatabase, @Autowired MongoClient mongoClient) {

        // give
        String collectionNameForTest = "testCollection";

        // when
        Publisher<Void> collection = mongoDatabase.createCollection(collectionNameForTest);
        collection.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Void aVoid) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                log.info("test collection create success");
            }
        });

        // then
        MongoCollection<Document> createdCollection = mongoDatabase.getCollection(collectionNameForTest);
        Assertions.assertNotNull(createdCollection);
        createdCollection.drop().subscribe(new Subscriber<Void>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Void aVoid) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                log.info("Drop test collection success");
            }
        });
    }

    @SpringBootApplication
    static class MainTest {
        public static void main(String[] args) {

        }
    }
}