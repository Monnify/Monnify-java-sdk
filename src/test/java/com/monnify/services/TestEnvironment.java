package com.monnify.services;

import io.github.cdimascio.dotenv.Dotenv;

public class TestEnvironment {
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String get(String key) {
        String value = dotenv.get(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Environment variable '" + key + "' is not defined"
            );
        }

        return value;
    }

}
