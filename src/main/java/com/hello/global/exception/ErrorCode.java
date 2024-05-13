package com.hello.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //global
    INTERNAL_SERVER_ERROR("G001", "Internal Server Error"),
    INVALID_INPUT_VALUE_ERROR("G002", "유효하지 않은 입력값입니다."),
    INVALID_METHOD_ERROR("G003", "Method Argument가 적절하지 않습니다."),
    REQUEST_BODY_MISSING_ERROR("G004", "RequestBody에 데이터가 존재하지 않습니다."),
    REQUEST_PARAM_MISSING_ERROR("G005", "RequestParam에 데이터가 전달되지 않았습니다."),
    INVALID_TYPE_VALUE_ERROR("G006", "타입이 유효하지 않습니다."),
    NOT_FOUND_ENTITY("G007", "엔티티를 찾을 수 없습니다."),
    UTIL_NOT_CONSTRUCTOR("G008", "유틸클래스는 생성자를 호출할 수 없습니다."),

    ILLEGAL_ARGUMENT_ERROR("G009", "잘못된 입력입니다."),
    ILLEGAL_STATE_ERROR("G010", "잘못된 상태입니다"),

    // stt
    STT_IO_ERROR("S001", "stt 해당 파일이 존재하지 않거나 경로가 잘못되었습니다."),
    STT_EXECUTION_ERROR("S001", "stt 응답을 받을 때 관련 설정 파일에 오류가 있습니다."),
    STT_CREDENTIAL_ERROR("S002","stt의 credential이 유효하지 않습니다."),
    STT_SPEECH_ERROR("S003","stt speech setting 중에 오류가 발생하였습니다."),

    //bucket
    FILE_UPLOAD_ERROR("F001", "Internal Server Error"),

    //translation
    TRANSLATE_CREDENTIAL_ERROR("T001", "번역 관련 credential 파일이 존재하지 않거나 경로가 잘못되었습니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
