package service.exception;

/**
 * @program: homeplus
 * @description: 内容异常
 * @author: XHQ
 * @create: 2019-08-31 14:09
 **/
public class ContentException extends ServiceException {

    public ContentException() {
        super();
    }

    public ContentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentException(String message) {
        super(message);
    }

    public ContentException(Throwable cause) {
        super(cause);
    }
}
