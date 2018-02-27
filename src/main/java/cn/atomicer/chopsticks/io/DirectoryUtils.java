package cn.atomicer.chopsticks.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rao-Mengnan
 * on 2018/1/24.
 */
public class DirectoryUtils {

    private boolean excludeFile;
    private boolean excludeDir;

    /**
     *
     * @param path root path
     * @return node list, exclude directory
     */
    public static List<SubFile> directoryStruct(String path) {
        return create().filterDirectory().getSubFiles(path);
    }

    public static void deleteDirectory(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) return;
        if (file.isDirectory()) {
            cleanDir(file);
        }
        if (!file.delete()) throw new IOException(String.format("directory delete failed: %s", file.getPath()));
    }

    public static String getUserHome() {
        return System.getProperties().getProperty("user.home") + File.separator;
    }

    public static String getProjectHome() {
        return System.getProperty("user.dir") + File.separator;
    }

    public static DirectoryUtils create() {
        return new DirectoryUtils();
    }

    public DirectoryUtils filterFile() {
        excludeFile = true;
        return this;
    }

    public DirectoryUtils filterDirectory() {
        excludeDir = true;
        return this;
    }

    private static void cleanDir(File file) throws IOException {
        File[] files = file.listFiles();
        if (files == null || files.length == 0) return;
        for (File sub : files) {
            if (sub.isDirectory()) cleanDir(file);
            if (!sub.delete()) throw new IOException(String.format("delete failed: %s", sub.getPath()));
        }
    }

    public List<SubFile> getSubFiles(String path) {
        return getSubFiles(path, "");
    }

    private List<SubFile> getSubFiles(String path, String parent) {

        List<SubFile> nodes = new ArrayList<>();

        SubFile subNode = new SubFile(parent);
        File file = new File(path);
        subNode.setAttr(file.getName(), file.isFile(), file.getAbsolutePath());

        if (file.isFile()) {
            if (!excludeFile) nodes.add(subNode);
        } else {
            // exclude root path
            if (parent != null && !"".equals(parent)) {
                if (!excludeDir) nodes.add(subNode);
            }

            File[] subFiles = file.listFiles();

            if (subFiles != null) {
                for (File sub : subFiles) {
                    nodes.addAll(getSubFiles(sub.getPath(), subNode.toString()));
                }
            }
        }
        return nodes;
    }

    public static class SubFile {
        String parent = "";
        String name;

        boolean fileType;
        String path;

        SubFile(String parent) {
            if (parent != null)
                this.parent = parent;
            if (!"".equals(this.parent)) {
                this.parent += File.separator;
            }
        }

        void setAttr(String name, boolean isFile, String path) {
            this.name = name;
            this.fileType = isFile;
            this.path = path;
        }

        public boolean isFile() {
            return fileType;
        }

        public boolean isDirectory() {
            return !fileType;
        }

        public File getFile() {
            return new File(path);
        }

        public String getPath() {
            return path;
        }

        /**
         * @return 从根目录开始的相对路径
         */
        @Override
        public String toString() {
            return parent + name;
        }
    }

}
