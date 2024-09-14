package com.crudpatternsdao.crudpatternsdao.infrastructure.dao;

import java.util.List;

import com.crudpatternsdao.crudpatternsdao.domain.interfaces.useCases.IGenericDAO;
import com.crudpatternsdao.crudpatternsdao.infrastructure.Model.ClientModel;

public interface IClientDAO extends  IGenericDAO<ClientModel,Long>{

    ClientModel create(ClientModel clientModel) ;

    ClientModel findById(Long id);

    List<ClientModel> findAll(int page, int size);

    long countTotal();

    ClientModel update(ClientModel clientModel,Long id);

     boolean delete(Long id) ;

}
