package cn.atomicer.chopsticks.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The tools of directory, include get the struct of directory
 * and delete directory recursion
 *
 * @author Rao-Mengnan
 *         on 2018/1/24.
 */
public class DirectoryUtils {

    private boolean excludeFile;
    private boolean excludeFolder;

    /**
     * Get the struct of directory, exclude folders
     *
     * @param path root path
     * @return node list
     */
    public static List<SubFile> directoryStruct(String path) {
        return create().filterFolder().getSubFiles(path);
    }

    /**
     * Delete directory recursion, if the target is the file will be deleted as well
     *
     * @param path target path
     * @throws IOException directory delete failed, may be the target path can't write
     */
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

    /**
     * Result exclude files
     *
     * @return self
     */
    public DirectoryUtils filterFile() {
        excludeFile = true;
        return this;
    }

    /**
     * Result exclude folders
     *
     * @return self
     */
    public DirectoryUtils filterFolder() {
        excludeFolder = true;
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

    /**
     * Get all sub-files, default include files and folders, you can ignore files or ignore folders
     * by filter:
     *
     * @param path target path
     * @return list of {@link SubFile} which include all the files/folders of result
     * @see DirectoryUtils#filterFile()
     * @see DirectoryUtils#filterFolder()
     */
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
                if (!excludeFolder) nodes.add(subNode);
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

    /**
     * Attributes of sub file
     */
    public static class SubFile {
        /**
         * The file path relative to the root directory:
         * {@code root/parent/name}  equals  {@code path}
         */
        String parent = "";
        /**
         * file name
         */
        String name;

        /**
         * is file: true, otherwise: false
         */
        boolean fileType;
        /**
         * absolute path of this file
         */
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
         * @return {@link SubFile#parent}
         */
        @Override
        public String toString() {
            return parent + name;
        }
    }

}
