package com.luizalabs.fileconverter.infrastructure.entrypoint.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MensageResponseVO {
    private LocalDateTime time;
    private String message;

}
