package com.my.timekeeping;

import com.my.timekeeping.exceptions.EncryptException;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Class utility that encodes password and check identical passwords
 */
public class PasswordUtil {
    // The higher the number of iterations the more
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final Logger logger = LogManager.getLogger(PasswordUtil.class);
    private static final int ITERATIONS = 20 * 1000;
    private static final int SALT_LEN = 32;
    private static final int DESIRED_KEY_LEN = 256;

    private PasswordUtil() {
    }

    /**
     * Computes a salted PBKDF2 hash of given plaintext password
     * suitable for storing in a database.
     * Empty passwords are not supported.
     */
    public static String getSaltedHash(String password) throws NoSuchAlgorithmException, EncryptException {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(SALT_LEN);
        // store the salt with the password
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    /**
     * Checks whether given plaintext password corresponds
     * to a stored salted hash of the password.
     */
    public static boolean check(String password, String stored) throws EncryptException {
        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            logger.error("The stored password have the form 'salt$hash'");
            throw new IllegalStateException(
                    "The stored password have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    private static String hash(String password, byte[] salt) throws EncryptException {
        if (password == null || password.length() == 0) {
            logger.error("Entry password are empty or not supported: {}", password);
            throw new EncryptException("Empty passwords are not supported.");
        }
        SecretKeyFactory f;
        SecretKey key;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            key = f.generateSecret(new PBEKeySpec(
                    password.toCharArray(), salt, ITERATIONS, DESIRED_KEY_LEN)
            );
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("cannot hash password {}", password);
            throw new EncryptException("cannot check password");
        }
        return Base64.encodeBase64String(key.getEncoded());
    }
}
