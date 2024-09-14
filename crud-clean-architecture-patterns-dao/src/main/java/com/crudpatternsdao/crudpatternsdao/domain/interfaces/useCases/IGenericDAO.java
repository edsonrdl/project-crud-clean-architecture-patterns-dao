package com.crudpatternsdao.crudpatternsdao.domain.interfaces.useCases;

import java.util.List;

public interface IGenericDAO<T, ID> {
    T create(T model);
    T findById(ID id);
    List<T> findAll(int page, int size);
    T  update(T entity,ID id);
    boolean delete(ID id);
}
