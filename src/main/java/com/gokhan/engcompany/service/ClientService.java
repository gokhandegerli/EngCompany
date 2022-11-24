package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.ClientDto;
import com.gokhan.engcompany.entity.Client;
import com.gokhan.engcompany.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;
    @Autowired
    PersonService personService;

    public ClientDto getClientDto(Client client) {
        return toDto(client);
    }

    private ClientDto toDto(Client client) {

        ClientDto dto = new ClientDto();
        dto.clientId = client.getClientId();
        dto.personDto = personService.getPersonDto(client.getPerson());
        return dto;
    }
}
