package com.smallworld.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExceptionLogger {

    public void logException(String message, Throwable exception) {
        log.error(message, exception);
    }

}
