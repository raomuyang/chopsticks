package cn.atomicer.chopsticks.io;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Encryption tools, support the DES algorithm at present
 *
 * @author rao-mengnan
 *         on 2017/6/20.
 */
public class EncryptionUtil {

    /**
     * Encrypt string (DES)
     *
     * @param message target string
     * @param key     secret key of encryption/decryption, the key size must be greater than 8
     * @return encrypted string
     * @throws BadPaddingException       illegal message content
     * @throws IllegalBlockSizeException illegal message content
     * @throws RuntimeException          unsupported encoding exception
     */
    public static String desEncrypt(String message, String key) throws BadPaddingException, IllegalBlockSizeException {
        byte[] result;
        try {
            result = desEncrypt(message.getBytes("UTF-8"), key);
            return BinaryUtils.buffer2HexStr(result);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encrypt bytes (DES)
     *
     * @param message target byte array
     * @param key     secret key of encryption/decryption, the key size must be greater than 8
     * @return encrypted byte array
     * @throws BadPaddingException       illegal message content
     * @throws IllegalBlockSizeException illegal message content
     */

    public static byte[] desEncrypt(byte[] message, String key) throws BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(message);
    }

    /**
     * Decrypt hex string (DES)
     *
     * @param key       secret key of encryption/decryption, the key size must be greater than 8
     * @param hexString target string, must be a hexadecimal string
     * @return decrypted string
     * @throws BadPaddingException       illegal message content
     * @throws IllegalBlockSizeException illegal message content
     */
    public static String desDecrypt(String hexString, String key) throws BadPaddingException, IllegalBlockSizeException {
        byte[] bytes = BinaryUtils.hexStrToByteArray(hexString);
        return new String(desDecrypt(bytes, key));
    }

    /**
     * Decrypt byte array (DES)
     *
     * @param key     secret key of encryption/decryption, the key size must be greater than 8
     * @param message target byte array
     * @return decrypted byte array
     * @throws BadPaddingException       illegal message content
     * @throws IllegalBlockSizeException illegal message content
     */
    public static byte[] desDecrypt(byte[] message, String key) throws BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = initCipher(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(message);
    }

    /**
     * @param MODE 加密/解密模式
     *             encryption/decryption mode
     * @param key  秘钥
     *             secret key of encryption/decryption, the key size must be greater than 8
     * @return {@link Cipher} instance
     */
    private static Cipher initCipher(int MODE, String key) {
        try {
            SecureRandom secureRandom = new SecureRandom();
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(MODE, secretKey, secureRandom);

            return cipher;
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
