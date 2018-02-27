package cn.atomicer.chopsticks.io;


import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by rao-mengnan on 2017/6/20.
 * DES加密工具
 */
public class EncryptionUtil {

    /**
     * @param message 要加密的字符串
     * @param key     秘钥
     * @return 加密字符串
     */
    public static String desEncrypt(String message, String key) throws BadPaddingException, IllegalBlockSizeException {
        byte[] result;
        try {
            result = desEncrypt(message.getBytes("UTF-8"), key);
            return BufferUtils.buffer2HexStr(result);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用DES加密
     *
     * @param message 需要加密数据的byte数组
     * @param key     秘钥
     * @return 加密后的byte数组
     */

    public static byte[] desEncrypt(byte[] message, String key) throws BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(message);
    }

    /**
     * @param key       秘钥
     * @param hexString 16进制字符串
     * @return 返回解密后的字符串
     */
    public static String desDecrypt(String hexString, String key) throws BadPaddingException, IllegalBlockSizeException {
        byte[] bytes = BufferUtils.hexStrToByteArray(hexString);
        return new String(desDecrypt(bytes, key));
    }

    /**
     * 使用DES解密
     *
     * @param key     秘钥
     * @param message 需要解密数据的byte数组
     * @return 解密后的byte数组
     */
    public static byte[] desDecrypt(byte[] message, String key) throws BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = initCipher(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(message);
    }

    /**
     * @param MODE 加密/解密模式
     * @param key  秘钥
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
