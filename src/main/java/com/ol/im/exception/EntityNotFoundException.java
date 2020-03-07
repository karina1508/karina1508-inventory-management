package com.ol.im.exception;

import lombok.AllArgsConstructor;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
