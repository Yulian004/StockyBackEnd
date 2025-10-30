package com.generation.stockybackend.model.dtos.intercom;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IntercomMessageOutputDto {
    private String emailSender;
    private boolean archiviato;
    private LocalDateTime timestamp;
    private String title;
    private String content;

}
