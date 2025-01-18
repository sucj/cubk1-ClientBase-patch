package org.union4dev.base.config.impl;

import org.union4dev.base.Access;
import org.union4dev.base.config.Config;
import org.union4dev.base.module.handlers.ModuleHandle;
import org.union4dev.base.util.JsonUtil;
import org.union4dev.base.value.AbstractValue;
import com.google.gson.JsonObject;

public class ValueConfig extends Config {
    public ValueConfig() {
        super("values.json");
    }

    @Override
    public void readConfig() {
        if (file.exists()) {
            JsonObject object = (JsonObject) JsonUtil.toJson(file);
            if (object != null && object.isJsonObject()) {
                object.entrySet().forEach(entry -> {
                    Class<?> m = Access.getInstance().getModuleManager().getModuleClass(entry.getKey());
                    JsonObject moduleObject = entry.getValue().getAsJsonObject();
                    if (m != null) {
                        if (moduleObject.has("KeyBind"))
                        Access.getInstance().getModuleManager().setKey(m, moduleObject.get("KeyBind").getAsInt());

                        if (moduleObject.has("State"))
                        Access.getInstance().getModuleManager().setEnable(m, moduleObject.get("State").getAsBoolean());

                        if (moduleObject.has("Hidden"))
                        Access.getInstance().getModuleManager().setVisible(m, moduleObject.get("Hidden").getAsBoolean());

                        if (moduleObject.has("Values")) {
                            try {
                                JsonObject valueObject = moduleObject.get("Values").getAsJsonObject();
                                valueObject.entrySet().forEach(elementEntry -> {
                                    AbstractValue<?> value = Access.getInstance().getModuleManager().getValue(m, elementEntry.getKey());
                                    if (value != null) {
                                        value.fromJson(elementEntry.getValue());
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        } else {
            writeConfig();
        }
    }

    @Override
    public void writeConfig() {
        JsonObject object = new JsonObject();

        Access.getInstance().getModuleManager().getModules().forEach(m -> {
            JsonObject moduleObject = new JsonObject();
            moduleObject.addProperty("KeyBind", Access.getInstance().getModuleManager().getKey(m));
            moduleObject.addProperty("State", Access.getInstance().getModuleManager().isEnabled(m));
            moduleObject.addProperty("Hidden", Access.getInstance().getModuleManager().isVisible(m));
            if (Access.getInstance().getModuleManager().hasValue(m)) {
                JsonObject valueObject = new JsonObject();
                Access.getInstance().getModuleManager().getValues(m).forEach(value -> value.toJson(valueObject));
                moduleObject.add("Values",valueObject);
            }
            object.add(Access.getInstance().getModuleManager().format(m), moduleObject);
        });

        JsonUtil.toFile(file,object);
    }
}
