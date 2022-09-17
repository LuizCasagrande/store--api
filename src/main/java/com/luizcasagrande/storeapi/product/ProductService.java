package com.luizcasagrande.storeapi.product;

import com.luizcasagrande.storeapi.framework.CrudServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService extends CrudServiceImpl<Product> {

    private final ProductRepository productRepository;

    @Override
    protected JpaRepository<Product, UUID> getRepository() {
        return productRepository;
    }

    public Set<String> findAllCategory() {
        return productRepository.findAllCategory();
    }

    public Page<Product> findByCategory(String category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }

    public Set<Product> findByIdIn(Set<UUID> id) {
        return productRepository.findByIdIn(id);
    }
}
