package mins.study.common;

import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveMongoCommonServiceImpl implements ReactiveMongoCommonService {

    private final MongoDatabase mongoDatabase;

    @Override
    public <T> Publisher<InsertOneResult> insert(String collectionName, T data, Class<T> clz) {

        MongoCollection<T> collection = mongoDatabase.getCollection(collectionName, clz);

        return collection.insertOne(data);
    }

    @Override
    public <T> Publisher<T> selectOneByPk(String collectionName, String key, Class<T> clz) {
        return mongoDatabase.getCollection(collectionName, clz).find(Filters.eq("_id", key));
    }
}
