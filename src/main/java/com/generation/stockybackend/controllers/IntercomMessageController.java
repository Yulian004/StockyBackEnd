package com.generation.stockybackend.controllers;

import com.generation.stockybackend.exceptions.UserNotFound;
import com.generation.stockybackend.model.dtos.intercom.IntercomMessageInputDto;
import com.generation.stockybackend.model.dtos.intercom.IntercomMessageOutputDto;
import com.generation.stockybackend.services.IntercomMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class IntercomMessageController {
    @Autowired
    IntercomMessageService serv;

    @PostMapping
    public ResponseEntity<Map<String,String>> inserisci(@RequestBody IntercomMessageInputDto dto) {
        try {
            serv.convertToMessageAndSave(dto);
            return ResponseEntity.ok(Map.of("message", "Messaggio inviato con successo"));
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Errore durante l'invio del messaggio: " + e.getMessage()));
        }
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
