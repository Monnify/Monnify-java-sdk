package com.monnify.utils;

import java.util.Objects;
import java.util.regex.Pattern;

public final class StringUtils {
    private static Pattern whitespacePattern = Pattern.compile("\\s");

    public static boolean containsWhitespace(String str) {
        Objects.requireNonNull(str);
        return whitespacePattern.matcher(str).find();
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}

