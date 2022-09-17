package com.luizcasagrande.storeapi.framework;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.NoResultException;
import java.lang.reflect.ParameterizedType;
import java.util.UUID;

import static com.luizcasagrande.storeapi.util.StoreApiConstants.NO_RESULT_MSG;
import static java.lang.String.format;

@SuppressWarnings("unchecked")
public abstract class CrudServiceImpl<T> implements CrudService<T> {

    private final Class<T> persistentClass;

    protected CrudServiceImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected abstract JpaRepository<T, UUID> getRepository();

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public T findById(UUID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> getNoResultException(id));
    }

    @Override
    public T save(T entity) {
        preSave(entity);
        return getRepository().save(entity);
    }

    protected void preSave(T entity) {
    }

    @Override
    public void delete(UUID id) {
        if (!getRepository().existsById(id)) {
            throw getNoResultException(id);
        }
        getRepository().deleteById(id);
    }

    protected NoResultException getNoResultException(UUID id) {
        String message = format(NO_RESULT_MSG, persistentClass.getSimpleName(), id);
        return new NoResultException(message);
    }
}
