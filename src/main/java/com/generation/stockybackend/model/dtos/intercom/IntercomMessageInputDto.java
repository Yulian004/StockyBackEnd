package com.generation.stockybackend.model.dtos.intercom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntercomMessageInputDto {
    private String emailSender;
    private String emailReceiver;
    private String title;
    private String content;
}
