package org.union4dev.base.module.handlers;

import org.lwjgl.input.Keyboard;
import org.union4dev.base.Access;
import org.union4dev.base.annotations.system.Disable;
import org.union4dev.base.annotations.system.Enable;
import org.union4dev.base.events.EventManager;
import org.union4dev.base.module.Category;
import org.union4dev.base.value.AbstractValue;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public final class ModuleHandle {

    private final String name;

    private final Category category;

    private final Object object;

    private ArrayList<AbstractValue<?>> values;
    private final HashMap<String, AbstractValue<?>> valueHashMap = new HashMap<>();

    private boolean state;

    private boolean visible;

    private String suffix;

    private int key;

    public ModuleHandle(String name, Category category, Object object) {
        this.state = false;
        this.name = name;
        this.category = category;
        this.object = object;
        this.values = new ArrayList<>();
        this.visible = true;
        this.suffix = "";
        this.key = Keyboard.KEY_NONE;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setValues(ArrayList<AbstractValue<?>> values) {
        this.values = values;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public ArrayList<AbstractValue<?>> getValues() {
        Arrays.stream(this.getClass().getDeclaredFields()).forEach(field -> {
            try {
                if (!field.isAccessible()) field.setAccessible(true);

                Object obj = field.get(this);
                if (obj instanceof AbstractValue) {
                    AbstractValue<?> value = (AbstractValue<?>) obj;
                    values.add(value);
                    valueHashMap.put(value.getName().toLowerCase(), value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return values;
    }

    public AbstractValue<?> getValue(String name) {
        return valueHashMap.getOrDefault(name.toLowerCase(), null);
    }

    public void setEnable(boolean state) {
        if (state == this.state) return;
        this.state = state;
        if (state) {
            EventManager.register(object);
            invokeMethodsAnnotationPresent(Enable.class);
        } else {
            EventManager.unregister(object);
            invokeMethodsAnnotationPresent(Disable.class);
        }
    }

    private void invokeMethodsAnnotationPresent(Class<? extends Annotation> anno) {
        for (Method method : this.object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(anno)) {
                try {
                    Access.getInstance().getInvoke().invokeMethod(object,method);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return state;
    }
}
