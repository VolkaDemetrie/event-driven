package com.volka.eventdriven.core.dto;

import com.volka.eventdriven.core.constant.ResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class StandardResponseDTO<T> {

    private String code;
    private String message;
    private T content;

    public static <T> StandardResponseDTO<T> success() {
        return new StandardResponseDTO<>(ResultCode.OK);
    }

    public static <T> StandardResponseDTO<T> success(ResultCode resultCode) {
        log.info("Api Server Success ResponseData :: {}", resultCode);
        return new StandardResponseDTO<>(resultCode);
    }

    public static <T> StandardResponseDTO<T> success(T responseData) {
        log.info("Api Server Success ResponseData :: {}", responseData);
        return new StandardResponseDTO<>(ResultCode.OK, responseData);
    }

    public static <T> StandardResponseDTO<T> success(T responseData, boolean logging) {
        if (logging) {
            log.info("Api Server Success ResponseData :: {}", responseData);
        } else {
            log.info("Api Server Success ResponseData :: truncated");
        }
        return new StandardResponseDTO<>(ResultCode.OK, responseData);
    }


    public static <T> StandardResponseDTO<T> fail(ResultCode resultCode) {
        log.info("Api Server fail responseCode :: {}", resultCode);
        return new StandardResponseDTO<>(resultCode);
    }

    public static <T> StandardResponseDTO<T> fail(String responseCode, String errorMessageDetail) {
        log.info("Api Server fail responseCode :: {}", responseCode);
        return new StandardResponseDTO<>(responseCode, errorMessageDetail);
    }

    public static <T> StandardResponseDTO<T> fail(String responseCode, String errorMessageDetail, Throwable e) {
        log.info("Api Server fail responseCode :: {}", responseCode);
        return new StandardResponseDTO<>(responseCode, errorMessageDetail);
    }

    public static <T> StandardResponseDTO<T> fail(String responseCode, String errorMessageDetail, T responseData) {
        log.info("Api Server fail responseCode :: {}, errorMessageDetail :: {}, ResponseData:: {}", responseCode, errorMessageDetail, responseData);
        return new StandardResponseDTO<>(responseCode, errorMessageDetail, responseData);
    }

    @SuppressWarnings(value = "unchecked")
    StandardResponseDTO(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.content = (T) resultCode.getCode();
    }

    StandardResponseDTO(ResultCode resultCode, T content) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.content = content;
    }

    StandardResponseDTO(String responseCode, String responseMessage) {
        this.code = responseCode;
        this.message = responseMessage;
    }

    StandardResponseDTO(String responseCode, String responseMessage, T content) {
        this.code = responseCode;
        this.message = responseMessage;
        this.content = content;
    }

}
