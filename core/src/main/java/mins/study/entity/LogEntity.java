package mins.study.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LogEntity {

    @MongoId
    private String id;

    private String requestIp;
    private String requestParameter;
    private String requestMethod;

    public LogEntity(String requestIp, String requestParameter, String requestMethod) {
        this.requestIp = requestIp;
        this.requestParameter = requestParameter;
        this.requestMethod = requestMethod;
    }
}
