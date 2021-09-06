package com.danko.provider.util;

import java.util.UUID;

public final class UniqueStringGenerator {
    private UniqueStringGenerator() {
    }

    public static String generationUniqueString() {
        return UUID.randomUUID().toString();
    }
}
