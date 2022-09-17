package com.luizcasagrande.storeapi.product;

import com.luizcasagrande.storeapi.framework.CrudService;
import com.luizcasagrande.storeapi.framework.ReadRestController;
import com.luizcasagrande.storeapi.product.dto.ProductRequest;
import com.luizcasagrande.storeapi.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController extends ReadRestController<Product, ProductResponse> {

    private final ProductService productService;

    @Override
    protected CrudService<Product> getService() {
        return productService;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @ResponseStatus(CREATED)
    @PostMapping
    public ProductResponse save(@Valid @RequestBody ProductRequest request) {
        Product product = modelMapper.map(request, Product.class);
        product = productService.save(product);
        return toResponse(product);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @ResponseStatus(OK)
    @PutMapping("{id}")
    public ProductResponse update(@PathVariable("id") UUID id, @Valid @RequestBody ProductRequest request) {
        Product product = productService.findById(id);
        modelMapper.map(request, product);
        product = productService.save(product);
        return toResponse(product);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") UUID id) {
        productService.delete(id);
    }

    @GetMapping("category")
    public Set<String> findAllCategory() {
        return productService.findAllCategory();
    }

    @GetMapping("category/{category}")
    public Page<ProductResponse> findByCategory(@PathVariable("category") String category, Pageable pageable) {
        return productService.findByCategory(category, pageable)
                .map(this::toResponse);
    }
}
