package mins.study.repository;

import mins.study.entity.LogEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LogMongoRepository extends ReactiveMongoRepository<LogEntity, String> {
}
