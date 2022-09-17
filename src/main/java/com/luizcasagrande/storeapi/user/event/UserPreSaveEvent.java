package com.luizcasagrande.storeapi.user.event;

import com.luizcasagrande.storeapi.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class UserPreSaveEvent extends ApplicationEvent {

    @Getter
    private final User user;

    public UserPreSaveEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
