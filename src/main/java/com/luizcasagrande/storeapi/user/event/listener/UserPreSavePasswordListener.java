package com.luizcasagrande.storeapi.user.event.listener;

import com.luizcasagrande.storeapi.email.EmailService;
import com.luizcasagrande.storeapi.user.User;
import com.luizcasagrande.storeapi.user.event.UserPreSaveEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

import static com.luizcasagrande.storeapi.util.PasswordUtils.randomPassword;
import static com.luizcasagrande.storeapi.util.StoreApiConstants.EMAIL_LOG;
import static com.luizcasagrande.storeapi.util.StoreApiConstants.EMAIL_SUBJECT;
import static com.luizcasagrande.storeapi.util.StoreApiConstants.EMAIL_TEXT;
import static com.luizcasagrande.storeapi.util.StoreApiConstants.PASSWORD_SIZE;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.SPACE;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserPreSavePasswordListener implements ApplicationListener<UserPreSaveEvent> {

    private final EmailService emailService;
    private final Supplier<String> randomPassword = () -> randomPassword(PASSWORD_SIZE);

    @Override
    public void onApplicationEvent(UserPreSaveEvent event) {
        User user = event.getUser();
        user.setPassword(randomPassword.get());
        sendPasswordByEmail(user);
    }

    private void sendPasswordByEmail(User user) {
        String firstName = user.getName().split(SPACE)[0];
        String emailText = format(EMAIL_TEXT, firstName, user.getPassword());

        emailService.send(user.getEmail(), EMAIL_SUBJECT, emailText);
        log.info(format(EMAIL_LOG, user.getEmail()));
    }
}
