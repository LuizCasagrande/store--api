package com.luizcasagrande.storeapi.user;

import com.luizcasagrande.storeapi.user.dto.UserAddressRequest;
import com.luizcasagrande.storeapi.user.dto.UserRequest;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNullElse;

@Component
public record UserConverter() implements Converter<UserRequest, User> {

    @Override
    public User convert(MappingContext<UserRequest, User> context) {
        User user = requireNonNullElse(context.getDestination(), new User());
        UserRequest userRequest = context.getSource();

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setAddress(convertAddress(user, userRequest));

        return user;
    }

    private UserAddress convertAddress(User user, UserRequest userRequest) {
        UserAddress address = requireNonNullElse(user.getAddress(), new UserAddress());
        UserAddressRequest addressRequest = userRequest.getAddress();

        address.setUser(user);
        address.setCity(addressRequest.getCity());
        address.setStreet(addressRequest.getStreet());
        address.setNumber(addressRequest.getNumber());
        address.setZipcode(addressRequest.getZipcode());

        return address;
    }
}
