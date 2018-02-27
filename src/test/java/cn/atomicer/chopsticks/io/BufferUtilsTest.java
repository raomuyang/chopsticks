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
public class BufferUtilsTest {
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
        byte[] digest1 = BufferUtils.getFileMD5Digits(FILE);
        byte[] digest2 = BufferUtils.getStringMD5Digits(MESSAGE);
        assertEquals(BufferUtils.buffer2HexStr(digest1), BufferUtils.buffer2HexStr(digest2));
    }

    @Test
    public void getBytes() throws Exception {
        byte[] buf1 = BufferUtils.getBytes(FILE);
        byte[] buf2 = MESSAGE.getBytes();
        String str1 = new String(buf1);
        String str2 = new String(buf2);
        assertEquals(str1, str2);
    }

    @Test
    public void testBufConvert() throws Exception {
        byte[] digest = BufferUtils.getFileMD5Digits(FILE);
        assertEquals(new String(digest),
                new String(
                        BufferUtils.hexStrToByteArray(
                                BufferUtils.buffer2HexStr(digest))));
    }

}