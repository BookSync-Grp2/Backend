package com.booksync.backend.service;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    public static String generateSecretKey(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static void main(String[] args) {
        String secretKey = generateSecretKey(32);
        System.out.println("Generated secret key: " + secretKey);
    }
}