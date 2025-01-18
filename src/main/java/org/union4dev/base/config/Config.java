package org.union4dev.base.config;

import java.io.File;

public abstract class Config {
    protected final File file;

    public Config(String fileName) {
        this.file = new File(ConfigManager.configDir, fileName);
    }

    public abstract void readConfig();
    public abstract void writeConfig();

    public String getName() {
        return file.getName().replace(".json","");
    }
}
