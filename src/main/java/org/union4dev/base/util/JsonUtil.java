package org.union4dev.base.util;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class JsonUtil {
    public static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();
    public static JsonObject toJson(String obj) {
        return new JsonParser().parse(obj).getAsJsonObject();
    }

    public static String toString(JsonObject jsonObject) {
        return new GsonBuilder().create().toJson(jsonObject);
    }

    public static JsonElement toJson(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            JsonElement object = JsonParser.parseReader(fileReader);
            //JsonObject object = new JsonParser().parse(fileReader).getAsJsonObject();
            fileReader.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void toFile(File file, JsonElement jsonObject) {
        try {
            if (!file.getParentFile().exists()) file.mkdirs();
            if (!file.exists()) file.createNewFile();
            final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            printWriter.println(PRETTY_GSON.toJson(jsonObject));
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
