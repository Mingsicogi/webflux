package mins.study.config;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories(basePackages = "mins.study.repository")
@EnableReactiveMongoAuditing
@Configuration
public class ReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public MongoDatabase mongoDatabase(@Autowired MongoClient mongoClient) {
        return mongoClient.getDatabase(database);
    }
}
