package com.luizcasagrande.storeapi.framework;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.UUID;

@Component
public abstract class ReadRestController<T, X> {

    private final Type responseType;

    @Autowired
    protected ModelMapper modelMapper;

    protected ReadRestController() {
        this.responseType = ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
    }

    protected abstract CrudService<T> getService();

    @GetMapping
    public Page<X> findAll(Pageable pageable) {
        return getService().findAll(pageable)
                .map(this::toResponse);
    }

    @GetMapping("{id}")
    public X findById(@PathVariable("id") UUID id) {
        T entity = getService().findById(id);
        return toResponse(entity);
    }

    protected X toResponse(T entity) {
        return modelMapper.map(entity, responseType);
    }
}
