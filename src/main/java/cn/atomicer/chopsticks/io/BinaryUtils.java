package cn.atomicer.chopsticks.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The tools of binary process, such as md5, hex, byte array...
 *
 * @author Rao-Mengnan
 *         on 2018/2/27.
 */
public class BinaryUtils {

    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * JDK does not provide the release of MappedByteBuffer, MappedByteBuffer was recovered in full GC, through the
     * manual release to let it recover, so this method provides a MappedByteBuffer release method
     *
     * @param buffer       not released MappedByteBuffer
     * @param channelClass the class of channel
     * @throws IOException unmap failed
     */
    @SuppressWarnings("unchecked")
    public static void unMapBuffer(MappedByteBuffer buffer, Class channelClass) throws IOException {
        if (buffer == null) {
            return;
        }

        Throwable throwable = null;
        try {
            Method unmap = channelClass.getDeclaredMethod("unmap", MappedByteBuffer.class);
            unmap.setAccessible(true);
            unmap.invoke(channelClass, buffer);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throwable = e;
        }

        if (throwable != null) {
            throw new IOException("MappedByte buffer unmap error", throwable);
        }
    }

    /**
     * Read files through FileChannel and calculate the file md5, support for large files
     *
     * @param file target file
     * @return md5 digest
     * @throws IOException file read error
     */
    public static byte[] getFileMD5Digest(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel channel = inputStream.getChannel();
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            long position = 0;
            long remaining = file.length();
            MappedByteBuffer byteBuffer;
            while (remaining > 0) {
                long size = Integer.MAX_VALUE / 2;
                if (size > remaining) {
                    size = remaining;
                }
                byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, position, size);
                messagedigest.update(byteBuffer);
                position += size;
                remaining -= size;
                unMapBuffer(byteBuffer, channel.getClass());
            }
            return messagedigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            channel.close();
            inputStream.close();
        }
    }

    /**
     * Get the md5 digest of string
     *
     * @param str target str
     * @return byte array of md5 digest
     */
    public static byte[] getStringMD5Digests(String str) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(str.getBytes());
            return messagedigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Byte array to hex string
     *
     * @param bytes byte array
     * @return hex string (low case)
     */
    public static String buffer2HexStr(byte[] bytes) {
        char[] strMd5 = new char[bytes.length * 2];
        int k = 0;
        for (byte b : bytes) {
            //Integer中也有转换16进制的方法
            //(b & 0xff >> 4) == (b & (0xff >> 4)) == (b & 0xf)
            //4bit表示一个16进制数
            strMd5[k++] = HEX[(b >> 4) & 0xf];// b >> 4 :高四位的为16进制的第一个数
            strMd5[k++] = HEX[b & 0xf]; //低四位的为16进制第二个数字
        }
        return new String(strMd5);
    }

    /**
     * Direct access to the file byte array (not recommended for large files)
     *
     * @param file target file
     * @return byte array of file
     * @throws IOException file read exception
     */
    public static byte[] getBytes(File file) throws IOException {
        if (file == null || !file.exists())
            return null;
        byte[] buffer;
        try (FileInputStream is = new FileInputStream(file); ByteArrayOutputStream bos = new ByteArrayOutputStream(1024)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) != -1)
                bos.write(buf, 0, len);
            buffer = bos.toByteArray();
        }
        return buffer;
    }

    /**
     * Hex string to byte array
     *
     * @param hexStr hex string: [0-9]|[a-f]|[A-F]
     * @return byte array of hex string
     */
    public static byte[] hexStrToByteArray(String hexStr) {
        if (hexStr.length() % 2 != 0) {
            throw new IllegalArgumentException("Input hex string length must be multiple of 2");
        }
        byte[] bytes = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length(); i += 2) {
            int h = hexValueMapper(hexStr.charAt(i));
            int l = hexValueMapper(hexStr.charAt(i + 1));

            byte origin = new Integer((h << 4) + l).byteValue();
            bytes[i / 2] = origin;
        }
        return bytes;
    }

    private static int hexValueMapper(char ch) {
        if (ch >= '0' && ch <= '9') {
            return Integer.parseInt(ch + "");
        }

        switch (ch) {
            case 'A':
            case 'a':
                return 10;
            case 'B':
            case 'b':
                return 11;
            case 'C':
            case 'c':
                return 12;
            case 'D':
            case 'd':
                return 13;
            case 'E':
            case 'e':
                return 14;
            case 'F':
            case 'f':
                return 15;
            default:
                throw new IllegalArgumentException("Input must be hex string");

        }
    }

}
