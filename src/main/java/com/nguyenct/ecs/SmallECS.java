package com.nguyenct.ecs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SmallECS implements MapperSetterECS {

    private int objectCounter = 0;
    private Map<Class, SmallComponentMapperSetter> componentMap = new HashMap<>();

    @Override
    public Collection<? extends ComponentMapper> getAllComponentMappers() {
        return componentMap.values();
    }

    @Override
    public <T> ComponentMapper<T> mapperOf(Class<T> componentClass) {
        componentMap.putIfAbsent(componentClass, new SmallComponentMapperSetter());
        return componentMap.get(componentClass);
    }

    @Override
    public Collection<? extends ComponentSetter> getAllComponentSetter() {
        return componentMap.values();
    }

    @Override
    public <T> ComponentSetter<T> setterOf(Class<T> componentClass) {
        componentMap.putIfAbsent(componentClass, new SmallComponentMapperSetter());
        return componentMap.get(componentClass);
    }


    @Override
    public int createEntity() {
        return objectCounter++;
    }

    @Override
    public void ensureEntityCounter(int entity) {
        objectCounter = Math.max(objectCounter, entity +1);
    }
}
