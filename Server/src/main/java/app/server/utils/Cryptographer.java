package app.server.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Cryptographer {
    private Cipher cipher;
    private SecretKey key;

    public Cryptographer() {
        try {
            String deSede = "DESede";
            byte[] bytes = "ThisIsMyEncryptionKey123".getBytes(StandardCharsets.UTF_8);
            KeySpec keySpec = new DESedeKeySpec(bytes);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(deSede);
            cipher = Cipher.getInstance(deSede);
            key = secretKeyFactory.generateSecret(keySpec);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String encrypt(String string) {
        String encrypted = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = cipher.doFinal(bytes);
            encrypted = new String(Base64.getEncoder().encode(encryptedBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }


    public String decrypt(String string) {
        String decrypted = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedBytes = Base64.getDecoder().decode(string.getBytes());
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            decrypted = new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypted;
    }
}
