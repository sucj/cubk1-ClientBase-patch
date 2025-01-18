package org.union4dev.base.config;

import org.union4dev.base.Access;
import org.union4dev.base.config.impl.*;
import org.union4dev.base.config.Config;
import org.apache.logging.log4j.LogManager;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ConfigManager {

    public static final File configDir = new File(Minecraft.getMinecraft().mcDataDir, Access.CLIENT_NAME);
    public final ValueConfig valueConfig = new ValueConfig();

    public ConfigManager() {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
    }

    public File getConfigDir() {
        return configDir;
    }

    public void loadConfigs(Config... configs) {
        Arrays.asList(configs).forEach(this::loadConfig);
    }

    public void saveConfigs(Config... configs) {
        Arrays.asList(configs).forEach(this::saveConfig);
    }

    public void loadConfig(Config config) {
        try {
            config.readConfig();
        } catch (Exception e) {
            LogManager.getLogger().info("Failed to load {}", config.getName());
            e.printStackTrace();
        }
    }

    public void saveConfig(Config config) {
        try {
            config.writeConfig();
        } catch (Exception e) {
            LogManager.getLogger().info("Failed to save {}", config.getName());
            e.printStackTrace();
        }
    }
}