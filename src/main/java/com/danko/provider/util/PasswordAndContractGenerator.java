package com.danko.provider.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class PasswordAndContractGenerator {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final int CATEGORIES_COUNT = 3;
    private boolean useLower;
    private boolean useUpper;
    private boolean useDigits;

    private PasswordAndContractGenerator() {
        throw new UnsupportedOperationException("Empty constructor is not supported.");
    }

    private PasswordAndContractGenerator(Builder builder) {
        this.useLower = builder.useLower;
        this.useUpper = builder.useUpper;
        this.useDigits = builder.useDigits;
    }

    public static class Builder {
        private boolean useLower;
        private boolean useUpper;
        private boolean useDigits;

        public Builder() {
            this.useLower = false;
            this.useUpper = false;
            this.useDigits = false;
        }

        public Builder useLower(boolean useLower) {
            this.useLower = useLower;
            return this;
        }

        public Builder useUpper(boolean useUpper) {
            this.useUpper = useUpper;
            return this;
        }

        public Builder useDigits(boolean useDigits) {
            this.useDigits = useDigits;
            return this;
        }

        public PasswordAndContractGenerator build() {
            return new PasswordAndContractGenerator(this);
        }
    }

    public String generate(int length) {

        if (length <= 0) {
            return "";
        }

        StringBuilder password = new StringBuilder(length);
        Random random = new Random(System.nanoTime());

        List<String> charCategories = new ArrayList<>(CATEGORIES_COUNT);
        if (useLower) {
            charCategories.add(LOWER);
        }
        if (useUpper) {
            charCategories.add(UPPER);
        }
        if (useDigits) {
            charCategories.add(DIGITS);
        }

        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }
        return password.toString();
    }
}