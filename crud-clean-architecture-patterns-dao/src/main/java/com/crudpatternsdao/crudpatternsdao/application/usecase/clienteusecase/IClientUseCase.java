package com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase;

import java.util.List;
import java.util.Map;

import com.crudpatternsdao.crudpatternsdao.domain.entities.Client;
import com.crudpatternsdao.crudpatternsdao.infrastructure.Model.ClientModel;

public interface IClientUseCase {
    ClientModel create(Client client);
    ClientModel findById(Long id);
    Map<String, Object> findAll(int page, int size); 
    ClientModel update(Client client, Long id);
    boolean delete(Long id);
}
