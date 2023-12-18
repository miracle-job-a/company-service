package com.miracle.companyservice.util.encryptor;

import com.miracle.companyservice.exception.*;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@Component
public class Encryptors {

    @Value("${miracle.key}")
    public String encodedSecretKey;

    public String SHA3Algorithm(String input) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512(); // SHA-3-512 사용
        byte[] hashedBytes = digestSHA3.digest(input.getBytes(StandardCharsets.UTF_8)); // 입력문자열을 UTF-8 인코딩으로 바이트 배열로 변환
        return Hex.toHexString(hashedBytes); // 바이트 배열을 16진수 문자열로 반환
    }

    public String secretKeyGeneratorAndDecode() {
        try {
            SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey(); // SecretKey 생성
            return Base64.getEncoder().encodeToString(secretKey.getEncoded()); // String으로 인코딩하여 리턴
        } catch (NoSuchAlgorithmException e) {
            throw new GenerateSecretKeyException("비밀키 생성 실패");
        }
    }

    public SecretKey decodeSecretKey(String secretKeyGeneratorAndDecode) {
        byte[] decode = Base64.getDecoder().decode(secretKeyGeneratorAndDecode);
        SecretKeySpec secretKey = new SecretKeySpec(decode, 0, decode.length, "AES");
        return secretKey;
    }

    public SecretKey getSecretKey() {
        return decodeSecretKey(encodedSecretKey);
    }

    public String encryptAES(String originalData, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypt = cipher.doFinal(originalData.getBytes());
            return Base64.getEncoder().encodeToString(encrypt);
        } catch (InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                 BadPaddingException e) {
            throw new EncryptDataException("데이터 암호화 실패");
        }catch (IllegalArgumentException | IllegalStateException e) {
            throw new EncryptDataException("데이터 암호화 실패");
        }
    }

    public String decryptAES(String encryptedData, SecretKey secretKey) {
        try {
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted);
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException e) {
            throw new DecryptDataException("데이터 복호화 실패");
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new DecryptDataException("데이터 복호화 실패");
        }
    }
}
