package com.crudpatternsdao.crudpatternsdao.infrastructure.mapper;

import com.crudpatternsdao.crudpatternsdao.domain.entities.Client;
import com.crudpatternsdao.crudpatternsdao.infrastructure.Model.ClientModel;

public class ClientMapperModel implements IClientMapperModel {

    
    @Override
    public ClientModel toClientModel(Client client) {
        ClientModel clientModel = new ClientModel();
        if (client.getCodeClient() != null) {
            clientModel.setCodeClient(client.getCodeClient());
        }
    
        clientModel.setName(client.getName());
        clientModel.setCpf(client.getCpf());
        clientModel.setAge(client.getAge());
    
        return clientModel;
    }
    

    @Override
    public Client toClient(ClientModel clientModel) {

        Client client = new Client();

        client.setCodeClient(clientModel.getCodeClient());
        client.setName(clientModel.getName());
        client.setCpf(clientModel.getCpf());
        client.setAge(clientModel.getAge());
        return client;
    }

}
