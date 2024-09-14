package com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.crudpatternsdao.crudpatternsdao.domain.entities.Client;
import com.crudpatternsdao.crudpatternsdao.infrastructure.Model.ClientModel;
import com.crudpatternsdao.crudpatternsdao.infrastructure.dao.IClientDAO;
import com.crudpatternsdao.crudpatternsdao.infrastructure.mapper.IClientMapperModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientUseCaseImpl implements IClientUseCase {

    private final IClientDAO iclientDAO;

    private final IClientMapperModel iClientMapperModel;

    
    public ClientUseCaseImpl(IClientDAO iclientDAO, IClientMapperModel iClientMapperModel) {
        this.iclientDAO = iclientDAO;
        this.iClientMapperModel = iClientMapperModel;
    }

    @Override
    public ClientModel create(Client client) {

        ClientModel clientModel = this.iClientMapperModel.toClientModel(client);

        return this.iclientDAO.create(clientModel);
    }

    @Override
    public ClientModel findById(Long id) {
        ClientModel clientModel = this.iclientDAO.findById(id);
        if (clientModel == null) {
            throw new RuntimeException("Cliente não encontrado");
        }
        return clientModel;
    }
    
    // @Override
    // public List<ClientModel> findAll(int page, int size) {
    //     List<ClientModel> clientModelList = this.iclientDAO.findAll(page, size);
    //     if (clientModelList == null || clientModelList.isEmpty()) {
    //         throw new RuntimeException("Não existem clientes");
    //     }
    //     return clientModelList;
    // }

    // @Override
    // public long countTotal() {
    //     return this.iclientDAO.countTotal(); 
    // }

    @Override
    @Cacheable(value = "clients", key = "#page + '-' + #size")
    public Map<String, Object> findAll(int page, int size) {
    List<ClientModel> clientModelList = this.iclientDAO.findAll(page, size);
    if (clientModelList == null || clientModelList.isEmpty()) {
              throw new RuntimeException("Não existem clientes");
     }
    long totalClients = this.iclientDAO.countTotal();

    Map<String, Object> result = new HashMap<>();
    result.put("clients", clientModelList);
    result.put("total", totalClients);

    return result;
    }

    @Override
    public ClientModel update(Client client,Long id) {

        ClientModel clientModel = this.iClientMapperModel.toClientModel(client);
        ClientModel clientModelUpdate=this.iclientDAO.update(clientModel,id);
        return clientModelUpdate;
    }

    @Override
    public boolean delete(Long id) {
        ClientModel clientModel = this.iclientDAO.findById(id);
        if (clientModel == null) {
            throw new RuntimeException("Cliente não encontrado");
        }
        this.iclientDAO.delete(id);
        return true; 
    }
}
