package com.luizcasagrande.storeapi.user;

import com.luizcasagrande.storeapi.framework.CrudServiceImpl;
import com.luizcasagrande.storeapi.user.event.UserPreSaveEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Predicate;

import static com.luizcasagrande.storeapi.user.UserType.CUSTOMER;
import static com.luizcasagrande.storeapi.util.StoreApiConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService extends CrudServiceImpl<User> implements UserDetailsService {

    public static final Predicate<User> isCustomer = user -> CUSTOMER.equals(user.getType());
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected JpaRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    protected void preSave(User entity) {
        eventPublisher.publishEvent(new UserPreSaveEvent(this, entity));
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    }

    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    public User findLoggedIn() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return loadUserByUsername(email);
    }
}
