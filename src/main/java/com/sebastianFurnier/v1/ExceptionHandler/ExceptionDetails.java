package com.sebastianFurnier.v1.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ExceptionDetails {
    private String userMessage;
    private String severity;
}
