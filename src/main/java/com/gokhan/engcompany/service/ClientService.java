package com.gokhan.engcompany.service;

import com.gokhan.engcompany.dto.ClientDto;
import com.gokhan.engcompany.entity.Client;
import com.gokhan.engcompany.repository.ClientRepository;
import com.gokhan.engcompany.request.ClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;
    @Autowired
    PersonService personService;

    public ClientDto getClientDto(int clientId) {
        Client client = getClientEntity(clientId);
        if (client == null) {
            return new ClientDto("This Client is not exist");
        } else {
            return client.toDto();
        }
    }

    public Client getClientEntity(int clientId) {
        return repository.findById(clientId).get();
    }


    public ClientDto createClient(ClientRequest clientRequest) {
        return createAndSetClient(clientRequest);
    }

    private ClientDto createAndSetClient(ClientRequest clientRequest) {

        Client client = new Client();
        client.setPerson(personService.insert(clientRequest.personRequest));
        return repository.save(client).toDto();
    }

    public String deleteClient(int clientId) {
        if (getClientEntity(clientId) != null) {
            repository.deleteById(clientId);
            return "Kişi silindi";
        } else {
            return "Kişi silinemedi, çünkü yok";
        }
    }

    public List<ClientDto> getAllClients() {
        return repository.findAll().stream().map(Client::toDto).toList();
    }
}
