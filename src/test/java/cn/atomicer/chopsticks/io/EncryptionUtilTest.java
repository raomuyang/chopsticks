package cn.atomicer.chopsticks.io;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/2/28.
 */
public class EncryptionUtilTest {
    private static final String KEY = "TEST_KEY";
    private static final String STRING = "xxxxxaaaaaaxxxxxvvvvvbbbbbbsssssdddd";

    @Test
    public void desEncrypt() throws Exception {
        byte[] result = EncryptionUtil.desEncrypt(STRING.getBytes(), KEY);
        Assert.assertEquals(new String(EncryptionUtil.desDecrypt(result, KEY)), STRING);

    }

    @Test
    public void desEncryptStr() throws Exception {
        String target = STRING + STRING;
        String result = EncryptionUtil.desEncrypt(target, KEY);
        String decrypt = EncryptionUtil.desDecrypt(result, KEY);
        Assert.assertEquals(target, decrypt);
    }

}