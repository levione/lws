package wang.snalc.stsossaliyun.exception;


@SuppressWarnings("WeakerAccess")
public class PublicException extends Exception {
    private final ExceptionEnum exceptionEnum;
    private final String url;

    public PublicException(ExceptionEnum exceptionEnum, String url) {
        this.exceptionEnum = exceptionEnum;
        this.url = url;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }

    public String getUrl() {
        return url;
    }
}
