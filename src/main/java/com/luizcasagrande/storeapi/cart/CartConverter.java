package com.luizcasagrande.storeapi.cart;

import com.luizcasagrande.storeapi.cart.dto.CartRequest;
import com.luizcasagrande.storeapi.product.Product;
import com.luizcasagrande.storeapi.product.ProductService;
import com.luizcasagrande.storeapi.user.UserService;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Component
public record CartConverter(ProductService productService,
                            UserService userService) implements Converter<CartRequest, Cart> {

    @Override
    public Cart convert(MappingContext<CartRequest, Cart> context) {
        Cart cart = Objects.requireNonNullElse(context.getDestination(), new Cart());
        CartRequest request = context.getSource();

        cart.setDate(now());
        cart.setPaymentMethod(request.getPaymentMethod());
        cart.setUser(userService.findLoggedIn());
        cart.getProducts().addAll(convertProducts(cart, request));

        return cart;
    }

    private List<CartProduct> convertProducts(Cart cart, CartRequest request) {
        Map<UUID, BigDecimal> quantityPerProduct = request.getQuantityPerProduct();
        Set<Product> products = productService.findByIdIn(quantityPerProduct.keySet());

        return products.stream()
                .map(p -> new CartProduct(quantityPerProduct.get(p.getId()), p, cart))
                .toList();
    }
}
