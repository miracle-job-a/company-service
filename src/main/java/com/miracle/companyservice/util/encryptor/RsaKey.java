package com.miracle.companyservice.util.encryptor;

import org.springframework.beans.factory.annotation.Value;

import java.security.PrivateKey;
import java.security.PublicKey;

public class RsaKey {

    @Value("${rsa.public-key}")
    private static String pubKey;

    @Value("${rsa.private-key}")
    private static String priKey;

    public static PublicKey getPublicKey() {
        try {
            return PasswordEncryptor.stringToPublicKey(pubKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey getPrivateKey() {
        try {
            return PasswordEncryptor.stringToPrivateKey(priKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
