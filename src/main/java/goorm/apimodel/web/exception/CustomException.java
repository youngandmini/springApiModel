package goorm.apimodel.web.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    //    private ErrorCode errorCode;
    private final int code;
    private final String message;
    private String infoMessage = "";

    public CustomException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String infoMessage) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.infoMessage = infoMessage;
    }
}
