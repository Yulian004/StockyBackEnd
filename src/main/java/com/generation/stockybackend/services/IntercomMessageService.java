package com.generation.stockybackend.services;

import com.generation.stockybackend.exceptions.UserNotFound;
import com.generation.stockybackend.model.dtos.intercom.IntercomMessageInputDto;
import com.generation.stockybackend.model.dtos.intercom.IntercomMessageOutputDto;
import com.generation.stockybackend.model.entities.IntercomMessage;
import com.generation.stockybackend.model.entities.auth.User;
import com.generation.stockybackend.model.repositories.IntercomMessageRepository;
import com.generation.stockybackend.model.repositories.auth.UserRepository;
import jakarta.persistence.EntityNotFoundException; // Assicurati di importare questo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors; // Assicurati di importare questo

@Service
public class IntercomMessageService {
    @Autowired
    IntercomMessageRepository repo;
    @Autowired
    UserRepository uRepo;

    public void convertToMessageAndSave(IntercomMessageInputDto dto)
    {
        IntercomMessage m = new IntercomMessage();
        // Usiamo findByEmail che ritorna Optional, è più sicuro
        User mittente = uRepo.findByEmail(dto.getEmailSender())
                .orElseThrow(() -> new UserNotFound("Mittente con email "+dto.getEmailSender()+ " non è stato trovato"));

        User destinatario = uRepo.findByEmail(dto.getEmailReceiver())
                .orElseThrow(() -> new UserNotFound("Destinatario con email "+dto.getEmailReceiver()+ " non è stato trovato"));

        m.setDestinatario(destinatario);
        m.setMittente(mittente);
        m.setTitolo(dto.getTitle());
        m.setContenuto(dto.getContent());
        m.setTime(LocalDateTime.now());
        repo.save(m);
    }

    /**
     * MODIFICATO: Legge la posta in arrivo (non archiviati, non eliminati)
     */
    public List<IntercomMessageOutputDto> ReadMyMessages(String email)
    {
        // Non usiamo più l'entity User, interroghiamo direttamente il repo
        return repo.findByDestinatarioEmailAndArchiviatoIsFalseAndEliminatoIsFalseOrderByTimeDesc(email)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * MODIFICATO: Legge i messaggi inviati
     */
    public List<IntercomMessageOutputDto> ReadSentMessages(String email)
    {
        // Non usiamo più l'entity User
        return repo.findByMittenteEmailOrderByTimeDesc(email)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // --- METODI NUOVI ---

    /**
     * Legge i messaggi archiviati
     */
    public List<IntercomMessageOutputDto> ReadArchivedMessages(String email)
    {
        return repo.findByDestinatarioEmailAndArchiviatoIsTrueAndEliminatoIsFalseOrderByTimeDesc(email)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Legge i messaggi nel cestino
     */
    public List<IntercomMessageOutputDto> ReadDeletedMessages(String email)
    {
        return repo.findByDestinatarioEmailAndEliminatoIsTrueOrderByTimeDesc(email)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Archivia un messaggio (setta flag archiviato = true)
     */
    public void archiveMessage(Long messageId) {
        IntercomMessage m = repo.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Messaggio non trovato con ID: " + messageId));

        m.setArchiviato(true);
        repo.save(m);
    }

    /**
     * Sposta un messaggio nel cestino (setta flag eliminato = true)
     */
    public void deleteMessage(Long messageId) {
        IntercomMessage m = repo.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Messaggio non trovato con ID: " + messageId));

        m.setEliminato(true);
        repo.save(m);
    }
    /**
     * Ripristina un messaggio dall'archivio (setta flag archiviato = false)
     */
    public void unarchiveMessage(Long messageId) {
        IntercomMessage m = repo.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Messaggio non trovato con ID: " + messageId));

        // Riporta allo stato di "non archiviato"
        m.setArchiviato(false);
        repo.save(m);
    }

    /**
     * Ripristina un messaggio dal cestino (setta flag eliminato = false)
     */
    public void restoreFromTrash(Long messageId) {
        IntercomMessage m = repo.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Messaggio non trovato con ID: " + messageId));

        // Riporta allo stato di "non eliminato"
        m.setEliminato(false);
        repo.save(m);
    }

    // ----------------------

    /**
     * MODIFICATO: Converte DTO includendo il nuovo flag
     */
    private IntercomMessageOutputDto convertToDto(IntercomMessage m)
    {
        IntercomMessageOutputDto dto = new IntercomMessageOutputDto();
        dto.setId(m.getId());
        dto.setEmailSender(m.getMittente().getEmail());
        dto.setEmailReceiver(m.getDestinatario().getEmail());
        dto.setArchiviato(m.isArchiviato());
        dto.setRead(m.isRead());
        dto.setContent(m.getContenuto());
        dto.setTimestamp(m.getTime());
        dto.setTitle(m.getTitolo());
        dto.setEliminato(m.isEliminato()); // <-- AGGIUNTO
        return dto;
    }

}