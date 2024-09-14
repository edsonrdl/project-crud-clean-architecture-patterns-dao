package com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase;

import org.springframework.stereotype.Component;

import com.crudpatternsdao.crudpatternsdao.domain.entities.Client;

@Component
public class ClientMapperDTO implements IClientMapperDTO {

    @Override
    public Client toClient(ClientRequestDTO clientRequestDTO) {
        Client client = new Client();
        client.setName(clientRequestDTO.getName());
        client.setCpf(clientRequestDTO.getCpf());
        client.setAge(clientRequestDTO.getAge());
        return client;
    }

    @Override
    public ClientRequestDTO toClientRequestDto(Client client) {
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
        clientRequestDTO.setName(client.getName());
        clientRequestDTO.setCpf(client.getCpf());
        clientRequestDTO.setAge(client.getAge());
        return clientRequestDTO;
    }

    @Override
    public ClientResponseDTO toClientResponseDto(Client client) {
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
        clientResponseDTO.setCodeClient(client.getCodeClient());
        clientResponseDTO.setName(client.getName());
        clientResponseDTO.setCpf(client.getCpf());
        clientResponseDTO.setAge(client.getAge());
        return clientResponseDTO;
    }

  
}