package cn.atomicer.chopsticks.io;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/2/28.
 */
public class FileUtilsTest {
    @Test
    public void testReadAndWriteObject() throws IOException {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();
        queue.add("aaa");
        queue.add("bbb");
        queue.add("ccc");

        File file = new File(FileUtils.class.getResource("/").getPath() + "local.obj");
        FileUtils.writeObject(queue, file.getPath());

        ConcurrentLinkedQueue<String> local = FileUtils.readLocalObject(file.getPath());
        Assert.assertNotNull(local);
        for (int i = 0; i < local.size(); i++) {
            assertEquals(queue.poll(), local.poll());
        }
        Assert.assertEquals(queue.poll(), local.poll());

        file.delete();
    }

    @Ignore
    @Test
    public void isLinkFile() throws IOException, InterruptedException {
        String root = FileUtilsTest.class.getResource("/").getPath();

        // 判断不存在的文件路径
        String invalid = root + File.separator + "this is invalid path";
        Assert.assertEquals(false, FileUtils.isSymlink(new File(invalid)));

        // 判断有效的文件路径
        String valid = root + File.separator + "test.fq";
        Assert.assertEquals(false, FileUtils.isSymlink(new File(valid)));

        // 判断链接文件
        try {
            String link = root + File.separator + "link";
            String createSymlink = String.format("ln -s %s %s", valid, link);
            Runtime.getRuntime().exec(createSymlink);
            Thread.sleep(10); // 调用系统进程是异步的

            File linkFile = new File(link);
            System.out.println("------ TEST Link file: " + linkFile);
            System.out.println(linkFile.exists());
            Assert.assertEquals(true, FileUtils.isSymlink(new File(link)));
            linkFile.delete();

            String tmpDir = root + "test-link-dir";
            Runtime.getRuntime().exec("mkdir -p " + tmpDir);
            Thread.sleep(10);
            String tmpDirLink = tmpDir + "-link";
            createSymlink = String.format("ln -s %s %s", tmpDir, tmpDirLink);
            Runtime.getRuntime().exec(createSymlink);
            Thread.sleep(10);

            File realDir = new File(tmpDir);
            File linkDir = new File(tmpDirLink);
            Assert.assertEquals(false, FileUtils.isSymlink(realDir));
            Assert.assertEquals(true, FileUtils.isSymlink(linkDir));
            Assert.assertEquals(realDir.getCanonicalPath(), linkDir.getCanonicalPath());
            DirectoryUtils.deleteDirectory(tmpDirLink);
            DirectoryUtils.deleteDirectory(tmpDir);

        } catch (Throwable e) {
            if (e.getClass().equals(AssertionError.class)) {
                throw e;
            }
            // windows 下无法使用runtime创建链接文件
            e.printStackTrace();
        }

    }

    @Test
    public void writeIntoFile() throws Exception {

    }

    @Test
    public void rename() throws Exception {

    }

}