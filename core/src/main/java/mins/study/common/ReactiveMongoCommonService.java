package mins.study.common;

import com.mongodb.client.result.InsertOneResult;
import org.reactivestreams.Publisher;

public interface ReactiveMongoCommonService {

    <T> Publisher<InsertOneResult> insert(String collectionName, T data, Class<T> clz);

    <T> Publisher<T> selectOneByPk(String collectionName, String key, Class<T> clz);
}
