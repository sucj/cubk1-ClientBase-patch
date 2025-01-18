package org.union4dev.base.value.impl;

import org.union4dev.base.value.AbstractValue;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class BooleanValue extends AbstractValue<Boolean> {

    public BooleanValue(String name, boolean enabled) {
        super(name);
        this.setValue(enabled);
    }

    @Override
    public void toJson(JsonObject jsonObject) {
        jsonObject.addProperty(getName(),getValue());
    }

    @Override
    public void fromJson(JsonElement jsonElement) {
        boolean value = jsonElement.getAsBoolean();
        this.setValue(value);
    }

}
