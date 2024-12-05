package com.sebastianFurnier.v1.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BadUrlException extends Exception {
    private ExceptionDetails details;

    public BadUrlException(String message, ExceptionDetails details, Throwable e) {
        super(message, e);
        setDetails(details);
    }

    public BadUrlException(String message, ExceptionDetails details) {
        super(message);
        setDetails(details);
    }
}
