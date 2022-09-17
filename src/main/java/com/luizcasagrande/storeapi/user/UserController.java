package com.luizcasagrande.storeapi.user;

import com.luizcasagrande.storeapi.user.dto.UserRequest;
import com.luizcasagrande.storeapi.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @ResponseStatus(CREATED)
    @PostMapping("new")
    public UserResponse save(@Valid @RequestBody UserRequest request) {
        User user = modelMapper.map(request, User.class);
        user = userService.save(user);
        return toResponse(user);
    }

    @GetMapping("logged-in")
    public UserResponse findLoggedIn() {
        User user = userService.findLoggedIn();
        return toResponse(user);
    }

    private UserResponse toResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
