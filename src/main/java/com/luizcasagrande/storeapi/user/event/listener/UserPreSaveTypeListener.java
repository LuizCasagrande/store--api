package com.luizcasagrande.storeapi.user.event.listener;

import com.luizcasagrande.storeapi.user.User;
import com.luizcasagrande.storeapi.user.event.UserPreSaveEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.luizcasagrande.storeapi.user.UserType.CUSTOMER;

@Component
public class UserPreSaveTypeListener implements ApplicationListener<UserPreSaveEvent> {

    @Override
    public void onApplicationEvent(UserPreSaveEvent event) {
        User user = event.getUser();
        user.setType(CUSTOMER);
    }
}
