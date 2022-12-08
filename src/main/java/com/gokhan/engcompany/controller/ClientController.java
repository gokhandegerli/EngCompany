package com.gokhan.engcompany.controller;

import com.gokhan.engcompany.dto.ClientDto;
import com.gokhan.engcompany.dto.EmployeeDto;
import com.gokhan.engcompany.repository.ClientRepository;
import com.gokhan.engcompany.request.ClientRequest;
import com.gokhan.engcompany.request.EmployeeRequest;
import com.gokhan.engcompany.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    ClientService service;

    @PostMapping()
    public ClientDto createClient(@RequestBody ClientRequest clientRequest) {
        return service.createClient(clientRequest);
    }

    @PutMapping("{clientId}/update-client")
    public ClientDto updateClient(@RequestBody ClientRequest clientRequest,
                                  @PathVariable(value = "clientId") int clientId) {
        return service.updateClient(clientRequest, clientId);
    }

    @GetMapping ("{clientId}")
    public ClientDto getClient(@PathVariable (value="clientId") int clientId) {
        return service.getClientDto(clientId);
    }

    @DeleteMapping("{clientId}/delete-client")
    public String deleteClient(@PathVariable (value="clientId") int clientId) {
        return service.deleteClient(clientId);
    }

    @GetMapping()
    public List<ClientDto> getAllClients() {
        return service.getAllClients();
    }




}
