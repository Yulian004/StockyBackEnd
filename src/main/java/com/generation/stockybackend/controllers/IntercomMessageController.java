package com.generation.stockybackend.controllers;

import com.generation.stockybackend.exceptions.UserNotFound;
import com.generation.stockybackend.model.dtos.intercom.IntercomMessageInputDto;
import com.generation.stockybackend.model.dtos.intercom.IntercomMessageOutputDto;
import com.generation.stockybackend.services.IntercomMessageService;
import jakarta.persistence.EntityNotFoundException;
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

    // --- ENDPOINT NUOVI AGGIUNTI ---

    @GetMapping("/archived/{email}")
    public List<IntercomMessageOutputDto> archivedMessages(@PathVariable String email)
    {
        return serv.ReadArchivedMessages(email);
    }

    @GetMapping("/trash/{email}")
    public List<IntercomMessageOutputDto> deletedMessages(@PathVariable String email)
    {
        return serv.ReadDeletedMessages(email);
    }

    /**
     * Endpoint per archiviare un messaggio.
     * Usiamo POST (o PUT) perché è un'azione che modifica lo stato.
     */
    @PostMapping("/{id}/archive")
    public ResponseEntity<Map<String, String>> archiveMessage(@PathVariable Long id) {
        try {
            serv.archiveMessage(id);
            return ResponseEntity.ok(Map.of("message", "Messaggio archiviato"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * Endpoint per spostare un messaggio nel cestino (soft delete).
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> deleteMessage(@PathVariable Long id) {
        try {
            serv.deleteMessage(id);
            return ResponseEntity.ok(Map.of("message", "Messaggio spostato nel cestino"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
    /**
     * Endpoint per ripristinare un messaggio dall'archivio.
     */
    @PostMapping("/{id}/unarchive")
    public ResponseEntity<Map<String, String>> unarchiveMessage(@PathVariable Long id) {
        try {
            serv.unarchiveMessage(id);
            return ResponseEntity.ok(Map.of("message", "Messaggio ripristinato nella posta in arrivo"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * Endpoint per ripristinare un messaggio dal cestino.
     */
    @PostMapping("/{id}/restore")
    public ResponseEntity<Map<String, String>> restoreMessage(@PathVariable Long id) {
        try {
            serv.restoreFromTrash(id);
            return ResponseEntity.ok(Map.of("message", "Messaggio ripristinato nella posta in arrivo"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
}