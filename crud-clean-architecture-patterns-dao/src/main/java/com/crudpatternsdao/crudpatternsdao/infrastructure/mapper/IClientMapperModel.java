package com.crudpatternsdao.crudpatternsdao.infrastructure.mapper;

import com.crudpatternsdao.crudpatternsdao.domain.entities.Client;
import com.crudpatternsdao.crudpatternsdao.infrastructure.Model.ClientModel;

public interface IClientMapperModel {
    Client toClient(ClientModel clientModel);
    ClientModel toClientModel(Client client);

}
