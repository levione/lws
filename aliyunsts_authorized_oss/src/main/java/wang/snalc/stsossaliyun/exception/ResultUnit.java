package wang.snalc.stsossaliyun.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ResultUnit {
    private LocalDateTime timestamp;
    private String code;
    private Object data;
    private String message;
    private String path;

    @Override
    public String toString() {
        return "{" +
                "\"timestamp\":\"" + timestamp + "\"" +
                ", \"code\":\"" + code + "\"" +
                ", \"data\":\"" + data + "\"" +
                ", \"message\":\"" + message + "\"" +
                ", \"path\":\"" + path + "\"" +
                '}';
    }

    public static ResultUnit ok(Object data, String path) {
        return ResultUnit.builder().code("200").message("ok").timestamp(LocalDateTime.now()).data(data).path(path).build();
    }

}
