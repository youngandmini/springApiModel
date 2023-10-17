package goorm.apimodel.web.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    BAD_REQUEST_JSON_ERROR(4001, "JSON의 형식이 잘못되었습니다."),
    DISALLOWED_GRADE_RANGE_ERROR(4002, "grade의 범위는 1~6입니다."),
    UNAVAILABLE_GRADE_FORMAT_ERROR(4003, "grade는 숫자를 입력해야합니다."),
    NON_EXISTENT_URL_ERROR(4040,"존재하지 않는 URL입니다."),
    INTERNAL_SERVER_ERROR(5000, "서버에서 알 수 없는 에러가 발생하였습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
