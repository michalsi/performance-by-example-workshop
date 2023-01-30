package utils;

import java.util.Optional;

public abstract class SystemHelper {
    public static String getPropertyIfNotEmptyOrThrow(String key, String message) {
        return Optional.ofNullable(System.getProperty(key)).filter(it -> !it.isEmpty()).orElseThrow(() -> new IllegalStateException(message));
    }
}