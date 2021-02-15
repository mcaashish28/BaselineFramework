package pt.dbservices.utilities.fileReaders;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JData {
    public static < T > T getFileProperties(String fileName) throws Exception {
        return openFile("./data/" + fileName);
    }

    public static < T > T openFile(String path) throws Exception {
        File file = getFile(path);
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file.getCanonicalPath()));
            JsonElement json = new Gson().fromJson(reader, JsonElement.class);
            if (json instanceof JsonArray) {
                return (T) json.getAsJsonArray();
            }
            return (T) json;
        } catch (Exception e) {
            throw new Error("File: " + file.getCanonicalPath() + " - " + e.toString());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static File getFile(String path) {
        String parentDirectory = System.getProperty("parentDir");
        if (parentDirectory != null) {
            try {
                return new File(new File(parentDirectory).getCanonicalPath(), path);
            } catch (IOException e) {
                return new File(parentDirectory, path);
            }
        } else {
            return new File(path);
        }
    }

    public static File getDirectory(String directoryName,boolean deleteFiles) {
        File driversDir = null;
        try {
            driversDir = new File(System.getProperty("user.dir"), directoryName);
            if (driversDir.exists()) {
                for (File file: driversDir.listFiles()) {
                    if (!file.isDirectory() && deleteFiles)
                        file.delete();
                }
                System.out.println("Files inside the Directory"+ directoryName + " deleted ");
            } else {
                driversDir.mkdir();
                System.out.println("Directory by name "+ directoryName + " is created ");
            }
        } catch (Exception e) {
            throw new Error("Failed to create Directory " + directoryName + " @ " + System.getProperty("user.dir"));
        } finally {
            return driversDir;
        }
    }
}
