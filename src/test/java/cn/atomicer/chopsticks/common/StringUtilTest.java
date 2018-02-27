package cn.atomicer.chopsticks.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by Rao-Mengnan
 * on 2017/8/29.
 */
public class StringUtilTest {
    @Test
    public void testFullWidth() {
        String str = "问号？ 句号。 逗号， 括号（） 感叹号！ 冒号： 全角空格　";

        Assert.assertEquals(true, StringUtils.existingFullWidthCharacters(str));

        Matcher matcher = StringUtils.getFullWidthCharacterMatcher(str);
        List<String> characters = new ArrayList<>();
        while (matcher.find()) {
            characters.add(matcher.group(1));
        }

        Assert.assertEquals("？", characters.remove(0));
        Assert.assertEquals("。", characters.remove(0));
        Assert.assertEquals("，", characters.remove(0));
        Assert.assertEquals("（", characters.remove(0));
        Assert.assertEquals("）", characters.remove(0));
        Assert.assertEquals("！", characters.remove(0));
        Assert.assertEquals("：", characters.remove(0));
        Assert.assertEquals("　", characters.remove(0));
    }

    @Test
    public void testReplaceFullWidthCharacters() {
        String str_1 = "问号？ 句号。 逗号， 括号（） 感叹号！ 冒号： 全角空格　";
        String str_2 = "问号 句号 逗号 括号 感叹号 冒号 全角空格";
        Assert.assertEquals(str_2, StringUtils.replaceAllFullWidthCharacters(str_1, ""));
    }
}
