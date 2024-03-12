package com.volka.eventdriven.core.advice;

import com.volka.eventdriven.core.constant.ResultCode;
import com.volka.eventdriven.core.dto.StandardResponseDTO;
import com.volka.eventdriven.core.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 예외 처리 컨트롤러 어드바이스
 *
 * @author volka
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler({BizException.class, Exception.class})
    public StandardResponseDTO<?> handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("#### Exception :: {}", e);
        log.error("#### Message :: {}", e.getMessage());

        if (e.getCause() != null) {
            log.error("#### Cause :: {}", e.getCause());
            log.error("#### Cause Message :: {}", e.getCause().getLocalizedMessage());
        }

        if (e instanceof BizException) {
            try {
                BizException bizException = (BizException) e;
                return StandardResponseDTO.fail(bizException.getErrorCode(), messageSource.getMessage(bizException.getErrorCode(), null, LocaleContextHolder.getLocale()));
            } catch (NoSuchMessageException ex1) {
                log.error("undefined this error code in this language pack");
            } catch (Exception ex) {
                log.error("exception handler error :: {}", ex);
            }
        } else if (e instanceof MethodArgumentNotValidException) {
            Map<String, String> errMap = new HashMap<>();

            try {
                MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
                validException.getBindingResult().getAllErrors().forEach(err -> errMap.put(((FieldError) err).getField(), err.getDefaultMessage()));

                return StandardResponseDTO.fail("COE00001", messageSource.getMessage("COE00001", null, LocaleContextHolder.getLocale()), errMap);

//                return StandardResponseDTO.fail("COE00001", messageSource.getMessage("COE00001", null, LocaleContextHolder.getLocale()));
            } catch (NoSuchMessageException ex1) {
                log.error("undefined this error code in this language pack");
            } catch (Exception ex2) {
                log.error("valid exception handler error :: {}", ex2);
            }
        } else if (e instanceof ConstraintViolationException) {
            try {
                ConstraintViolationException validException = (ConstraintViolationException) e;
                return StandardResponseDTO.fail("COE00001", messageSource.getMessage("COE00001", null, LocaleContextHolder.getLocale()), validException.getMessage());
            } catch (Exception ex) {
                log.error("valid exception handler error :: {}", ex);
            }
        } else if (e instanceof BindException) { //바인드 예외 추가
            Map<String, String> errMap = new HashMap<>();

            try {
                BindException bindException = (BindException) e;
                bindException.getBindingResult().getAllErrors().forEach(err -> errMap.put(((FieldError) err).getField(), err.getDefaultMessage()));

                return StandardResponseDTO.fail("COE00001", messageSource.getMessage("COE00001", null, LocaleContextHolder.getLocale()), errMap);
            } catch (Exception ex) {
                log.error("valid exception handler error bind Exception :: {}", ex);
            }
        } else if (e instanceof JsonParseException) {
            JsonParseException parseException = (JsonParseException) e;
            return StandardResponseDTO.fail("COE00002", messageSource.getMessage("COE00003", null, LocaleContextHolder.getLocale()), parseException.getMessage());
        }

        return StandardResponseDTO.fail(ResultCode.FAIL);
    }

}
