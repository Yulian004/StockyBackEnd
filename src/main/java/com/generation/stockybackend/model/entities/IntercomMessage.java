package com.generation.stockybackend.model.entities;

import com.generation.stockybackend.model.entities.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class IntercomMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User mittente;
    @ManyToOne(fetch = FetchType.EAGER)
    private User destinatario;
    private LocalDateTime timestamp;
    private String titolo;
    private String contenuto;
    private boolean archiviato = false;
    private boolean read = false;


}
