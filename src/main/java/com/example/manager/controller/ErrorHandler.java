package com.example.manager.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ErrorHandler {

    protected static void checkForErrors(BindingResult bindingResult, String context) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append(context).append(" = ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }
            throw new RuntimeException(errorMessage.toString());
        }
    }

}
