package com.luizcasagrande.storeapi.framework;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CrudService<T> {

    Page<T> findAll(Pageable pageable);

    T findById(UUID id);

    T save(T entity);

    void delete(UUID id);
}
