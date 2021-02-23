package mins.study.mvc.rx;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class Message {
    private String mid;
    private String message;
    private String fromId;
    private LocalDateTime sentDate;
}
