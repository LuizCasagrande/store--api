package com.luizcasagrande.storeapi.cart;

import com.luizcasagrande.storeapi.cart.dto.CartRequest;
import com.luizcasagrande.storeapi.cart.dto.CartResponse;
import com.luizcasagrande.storeapi.framework.CrudService;
import com.luizcasagrande.storeapi.framework.ReadRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController extends ReadRestController<Cart, CartResponse> {

    private final CartService cartService;

    @Override
    protected CrudService<Cart> getService() {
        return cartService;
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public CartResponse save(@Valid @RequestBody CartRequest request) {
        Cart cart = modelMapper.map(request, Cart.class);
        cart = cartService.save(cart);
        return toResponse(cart);
    }
}
