package com.generation.stockybackend.services;

import com.generation.stockybackend.exceptions.UserNotFound;
import com.generation.stockybackend.model.dtos.intercom.IntercomMessageInputDto;
import com.generation.stockybackend.model.dtos.intercom.IntercomMessageOutputDto;
import com.generation.stockybackend.model.entities.IntercomMessage;
import com.generation.stockybackend.model.entities.auth.User;
import com.generation.stockybackend.model.repositories.IntercomMessageRepository;
import com.generation.stockybackend.model.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IntercomMessageService {
    @Autowired
    IntercomMessageRepository repo;
    @Autowired
    UserRepository uRepo;

    public void convertToMessageAndSave(IntercomMessageInputDto dto)
    {
        IntercomMessage m = new IntercomMessage();
        User destinatario = uRepo.findByEmail2(dto.getEmailReceiver());
        User mittente = uRepo.findByEmail2(dto.getEmailSender());

        if(Objects.isNull(mittente)){
            throw new UserNotFound("Mittente con email "+dto.getEmailSender()+ " non è stato trovato");
        }
        if(Objects.isNull(destinatario)){
            throw new UserNotFound("Destinatario con email "+dto.getEmailReceiver()+ " non è stato trovato");
        }

        m.setDestinatario(destinatario);
        m.setMittente(mittente);
        m.setTitolo(dto.getTitle());
        m.setContenuto(dto.getContent());
        m.setTime(LocalDateTime.now());
        repo.save(m);

    }

    public List<IntercomMessageOutputDto> ReadMyMessages(String email)
    {
        User u = uRepo.findByEmail2(email);
        return u.getMessaggiRicevuti().stream().map(m->convertToDto(m)).toList();
    }
    public List<IntercomMessageOutputDto> ReadSentMessages(String email)
    {
        User u = uRepo.findByEmail2(email);
        return u.getMessaggiInviati().stream().map(m->convertToDto(m)).toList();
    }

    private IntercomMessageOutputDto convertToDto(IntercomMessage m)
    {
        IntercomMessageOutputDto dto = new IntercomMessageOutputDto();
        dto.setEmailSender(m.getMittente().getEmail());
        dto.setEmailReceiver(m.getDestinatario().getEmail());
        dto.setArchiviato(m.isArchiviato());
        dto.setRead(m.isRead());
        dto.setContent(m.getContenuto());
        dto.setTimestamp(m.getTime());
        dto.setTitle(m.getTitolo());
        return dto;
    }
}
