package com.hello.global.encryption;

import com.hello.global.encryption.dto.EncryptionResponse;
import com.hello.global.encryption.dto.SaltRequest;
import com.hello.global.encryption.dto.EncryptionRequest;
import com.hello.global.encryption.dto.PasswordMatchRequest;
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

    private String ENCRYPTION_ALGORITHM ="PBKDF2WithHmacSHA1";

    private String SALT_ALGORITHM = "SHA-512";
    private final int ITERATION_COUNT = 85319;
    private final int KEY_LENGTH = 128;

    public EncryptionResponse encrypt(EncryptionRequest request) {
        try {
            byte[] salt = getSalt(request.toSaltRequest());
            String password = encryptedPassword(request.getPasswordToCharArray(), salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);

            return EncryptionResponse.to(password, encodedSalt);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getSalt(SaltRequest request)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest digest = MessageDigest.getInstance(SALT_ALGORITHM);
        byte[] keyBytes = request.combineSaltResources().getBytes("UTF-8");

        return digest.digest(keyBytes);
    }

    public boolean matchPassword(PasswordMatchRequest request) {
        byte[] salt = changeSaltByEncoded(request.encodedSalt());
        String encryptedInputPassword = encryptedPassword(request.inputPassword(), salt);

        return encryptedInputPassword.equals(request.savedChatRoomPassword());
    }

    private byte[] changeSaltByEncoded(String encodedSalt) {
        return Base64.getDecoder().decode(encodedSalt);
    }

    private String encryptedPassword(char[] password, byte[] salt) {
        try {
            KeySpec spec
                    = new PBEKeySpec(
                    password,
                    salt,
                    ITERATION_COUNT,
                    KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM);

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException |
                 InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
