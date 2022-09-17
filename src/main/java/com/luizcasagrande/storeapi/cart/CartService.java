package com.luizcasagrande.storeapi.cart;

import com.luizcasagrande.storeapi.framework.CrudServiceImpl;
import com.luizcasagrande.storeapi.user.User;
import com.luizcasagrande.storeapi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static com.luizcasagrande.storeapi.user.UserService.isCustomer;

@Service
@RequiredArgsConstructor
public class CartService extends CrudServiceImpl<Cart> {

    private final CartRepository cartRepository;
    private final UserService userService;

    @Override
    protected JpaRepository<Cart, UUID> getRepository() {
        return cartRepository;
    }

    @Override
    protected void preSave(Cart entity) {
        BigDecimal total = entity.getProducts().stream()
                .map(p -> p.getProduct().getPrice().multiply(p.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        entity.setTotal(total);
    }

    @Override
    public Page<Cart> findAll(Pageable pageable) {
        User user = userService.findLoggedIn();

        if (isCustomer.test(user)) {
            return cartRepository.findByUserId(user.getId(), pageable);
        }
        return super.findAll(pageable);
    }

    @Override
    public Cart findById(UUID id) {
        User user = userService.findLoggedIn();

        if (isCustomer.test(user)) {
            return cartRepository.findByIdAndUserId(id, user.getId())
                    .orElseThrow(() -> getNoResultException(id));
        }
        return super.findById(id);
    }
}
