package com.hellomeritz.member.global.encryption;

import com.hellomeritz.member.global.encryption.dto.EncryptionRequest;
import com.hellomeritz.member.global.encryption.dto.SaltRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class PasswordEncoder {

    @Value("$encryption.password-algorithm")
    private String ENCRYPTION_ALGORITHM;

    @Value("$encryption.salt-algorithm")
    private String SALT_ALGORITHM;
    private final int ITERATION_COUNT = 85319;
    private final int KEY_LENGTH = 128;

    public String encrypt(EncryptionRequest request) {
        try {
            KeySpec spec
                    = new PBEKeySpec(
                    request.getPasswordToCharArray(),
                    getSalt(request.toSaltRequest()),
                    ITERATION_COUNT,
                    KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM);

            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException |
                 InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getSalt(SaltRequest request)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest digest = MessageDigest.getInstance(SALT_ALGORITHM);
        byte[] keyBytes = request.combineSaltResources().getBytes("UTF-8");

        return digest.digest(keyBytes);
    }
}
