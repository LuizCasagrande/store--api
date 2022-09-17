package com.luizcasagrande.storeapi.util;

import static com.luizcasagrande.storeapi.util.StoreApiConstants.CHARS;
import static org.apache.commons.lang3.RandomStringUtils.random;

public class PasswordUtils {

    public static String randomPassword(int count) {
        return random(count, CHARS);
    }
}
