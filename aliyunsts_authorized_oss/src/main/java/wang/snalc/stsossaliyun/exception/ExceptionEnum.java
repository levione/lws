package wang.snalc.stsossaliyun.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuppressWarnings({"unused"})
public enum ExceptionEnum {

    //3XX
    PUBLIC_NOT_FOUND("301", "所请求的数据不存在"),
    ALREADY_EXIT("302", "要创建的数据已经存在"),
    //1XX
    PUBLIC_BAD_REQUEST("101", "必要参数错误"),
    NULL_VALUE("101", "必要参数为空"),
    //4XX
    PUBLIC_FORBIDDEN("401", "请求被拒绝，可能你的账户没有权限或者登陆已经超时"),
    PUBLIC_UNAUTHORIZED("403", "没有访问权限"),
    TIME_IS_TOO_SHORT("401", "发送验证码请求间隔太短，至少一分钟"),
    WRONG_CODE("401", "错误的验证码"),
    //5XX
    PUBLIC_SERVICE_ERROR("501", "访问远程接口数据出错"),
    AUDIO_CHANGE_ERROR("501", "音频转码失败");


    /**
     * 200
     * 使用:
     * ResponseEntity.ok("msg" or Object);
     */

    //400
    public static ResponseEntity Bad(Object o) {
        return new ResponseEntity<>(o, HttpStatus.BAD_REQUEST);
    }

    // 成员变量
    private final String code;

    private final String message;

    // 构造方法
    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
