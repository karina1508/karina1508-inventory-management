package com.ol.im.service;

import com.ol.im.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Component
@RestControllerAdvice
public class HandlerException {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handlerException(Exception ex) {
        logger.error("Exception caught during request processing:" + ex.getMessage());
        return ResponseEntity.status(500).body(new ErrorDto(500, ex.getMessage()));

    }

}

