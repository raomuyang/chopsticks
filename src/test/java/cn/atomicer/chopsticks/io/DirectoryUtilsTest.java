package cn.atomicer.chopsticks.io;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/2/28.
 */
public class DirectoryUtilsTest {

    private static final String RESOURCE_PATH = DirectoryUtilsTest.class.getResource("/").getPath();
    @Test
    public void directoryStruct() {

        List<String> list1 = new ArrayList<>();
        for(DirectoryUtils.SubFile sub: DirectoryUtils.directoryStruct(RESOURCE_PATH)) {
            assertTrue(sub.getPath().contains(sub.name));
            list1.add(sub.getPath());
        }
        List<String> list2 = getSubFiles(RESOURCE_PATH);
        Assert.assertEquals(list1, list2);
    }

    @Test
    public void deleteDirectory() throws Exception {
        File dir = new File(RESOURCE_PATH + "/test-directory/sub1/sub2/");
        dir.mkdirs();
        assertTrue(dir.exists());

        File newFile = new File(dir, "test.file");
        FileUtils.copy(new File(RESOURCE_PATH + "/test.file"), newFile);
        assertTrue(newFile.exists());

        DirectoryUtils.deleteDirectory(dir.getPath());
        assertFalse(dir.exists());
        assertFalse(newFile.exists());
    }

    private List<String> getSubFiles(String path) {
        List<String> list = new ArrayList<>();
        File file = new File(path);
        if (file.isFile()) {
            list.add(file.getAbsolutePath());
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File sub: files) {
                    list.addAll(getSubFiles(sub.getAbsolutePath()));
                }
            }
        }
        return list;
    }

}