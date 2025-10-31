package com.generation.stockybackend.controllers;

import com.generation.stockybackend.model.dtos.intercom.IntercomMessageInputDto;
import com.generation.stockybackend.model.dtos.intercom.IntercomMessageOutputDto;
import com.generation.stockybackend.services.IntercomMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class IntercomMessageController {
    @Autowired
    IntercomMessageService serv;

    @PostMapping
    public void inserisci(@RequestBody IntercomMessageInputDto dto)
    {
        serv.convertToMessageAndSave(dto);
    }

    @GetMapping("/received/{email}")
    public List<IntercomMessageOutputDto> receivedMessages(@PathVariable String email)
    {
        return serv.ReadMyMessages(email);
    }
    @GetMapping("/sent/{email}")
    public List<IntercomMessageOutputDto> sentMessages(@PathVariable String email)
    {
        return serv.ReadSentMessages(email);
    }

}
