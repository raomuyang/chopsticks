package cn.atomicer.chopsticks.io;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/2/28.
 */
public class BinaryUtilsTest {
    private static File FILE = new File("test=buf-unit-test.test");
    private static final String MESSAGE = "BufferUtilsTest";

    @BeforeClass
    public static void beforeClass() {
        try (FileWriter writer = new FileWriter(FILE)) {
            writer.write(MESSAGE);
            writer.flush();
        } catch (IOException e) {
            fail(e.toString());
        }
    }

    @AfterClass
    public static void afterClass() {
        FILE.delete();
    }

    @Test
    public void getMD5Digits() throws Exception {
        byte[] digest1 = BinaryUtils.getFileMD5Digest(FILE);
        byte[] digest2 = BinaryUtils.getStringMD5Digests(MESSAGE);
        assertEquals(BinaryUtils.buffer2HexStr(digest1), BinaryUtils.buffer2HexStr(digest2));
    }

    @Test
    public void getBytes() throws Exception {
        byte[] buf1 = BinaryUtils.getBytes(FILE);
        byte[] buf2 = MESSAGE.getBytes();
        String str1 = new String(buf1);
        String str2 = new String(buf2);
        assertEquals(str1, str2);
    }

    @Test
    public void testBufConvert() throws Exception {
        byte[] digest = BinaryUtils.getFileMD5Digest(FILE);
        assertEquals(new String(digest),
                new String(
                        BinaryUtils.hexStrToByteArray(
                                BinaryUtils.buffer2HexStr(digest))));
    }

}