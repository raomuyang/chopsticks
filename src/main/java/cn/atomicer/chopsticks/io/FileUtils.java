package cn.atomicer.chopsticks.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Rao-Mengnan
 * on 2018/2/27.
 */
public class FileUtils {

    public static <T> boolean writeObject(T object, String filePath) throws IOException {
        File cache = new File(filePath);
        if (cache.exists() && !cache.delete()) {
            throw new IOException("local file delete failed: " + filePath);
        }

        ObjectOutputStream objectOutputStream = null;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(cache);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            return true;
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readLocalObject(String localPath) throws IOException {
        File file = new File(localPath);
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    public static boolean isSymlink(File file) throws IOException {
        if (file == null) {
            throw new IOException("File object is null");
        }
        File canonicalFile;
        if (file.getParent() == null) {
            canonicalFile = file;
        } else {
            File canonDir = file.getParentFile().getCanonicalFile();
            canonicalFile = new File(canonDir, file.getName());
        }
        return !canonicalFile.getCanonicalFile().equals(canonicalFile.getAbsoluteFile());
    }

    /**
     * write byte array into file (overwrite)
     * @param path target path
     * @param name target file name
     * @param bytes src bytes
     * @return successful
     */
    public static boolean writeIntoFile(String path, String name, byte[] bytes) throws IOException {
        FileOutputStream outputStream = null;
        try {
            File filePath = new File(path);
            if (!filePath.exists() && !filePath.mkdirs())
                throw new IOException(String.format("make directory %s failed", path));

            File file = new File(filePath, name);
            outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
        } finally {
            if (outputStream != null)
                outputStream.close();
        }

        return true;
    }

    public static void rename(String srcFilePath, String targetPath) throws IOException {
        Path source = Paths.get(srcFilePath);
        Path target = Paths.get(targetPath);
        Files.move(source, target);
    }

}
