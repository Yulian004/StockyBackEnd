package com.generation.stockybackend.model.repositories;

import com.generation.stockybackend.model.entities.IntercomMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntercomMessageRepository extends JpaRepository<IntercomMessage, Long>
{
// --- METODI AGGIUNTI ---

    /**
     * Ritorna la "Posta in arrivo" (non archiviati, non eliminati)
     */
    List<IntercomMessage> findByDestinatarioEmailAndArchiviatoIsFalseAndEliminatoIsFalseOrderByTimeDesc(String email);

    /**
     * Ritorna i messaggi inviati (per ora, tutti quelli inviati)
     */
    List<IntercomMessage> findByMittenteEmailOrderByTimeDesc(String email);

    /**
     * Ritorna gli archiviati (archiviati, ma non eliminati)
     */
    List<IntercomMessage> findByDestinatarioEmailAndArchiviatoIsTrueAndEliminatoIsFalseOrderByTimeDesc(String email);

    /**
     * Ritorna il cestino (solo eliminati)
     */
    List<IntercomMessage> findByDestinatarioEmailAndEliminatoIsTrueOrderByTimeDesc(String email);
}
