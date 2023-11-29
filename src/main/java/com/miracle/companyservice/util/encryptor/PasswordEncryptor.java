package com.miracle.companyservice.util.encryptor;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;

public class PasswordEncryptor {

    public static String SHA3Algorithm(String input) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512(); // SHA-3-512 사용
        byte[] hashedBytes = digestSHA3.digest(input.getBytes(StandardCharsets.UTF_8)); // 입력문자열을 UTF-8 인코딩으로 바이트 배열로 변환
        return Hex.toHexString(hashedBytes); // 바이트 배열을 16진수 문자열로 반환
    }
}
