package com.generation.stockybackend.model.dtos.intercom;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IntercomMessageOutputDto {
    private Long id;
    private String emailSender;
    private String emailReceiver;
    private boolean archiviato;
    private boolean read;
    private LocalDateTime timestamp;
    private String title;
    private String content;
    private boolean eliminato;

}
