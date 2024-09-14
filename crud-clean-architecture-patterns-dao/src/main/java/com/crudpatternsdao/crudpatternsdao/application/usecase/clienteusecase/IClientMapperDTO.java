package com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase;

import com.crudpatternsdao.crudpatternsdao.domain.entities.Client;

public interface IClientMapperDTO {
    Client toClient(ClientRequestDTO clientRequestDTO);
    ClientRequestDTO toClientRequestDto(Client client);
    ClientResponseDTO toClientResponseDto(Client client);
}