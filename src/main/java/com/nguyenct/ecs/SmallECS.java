package com.nguyenct.ecs;

import java.util.*;

public class SmallECS implements MapperSetterECS {

    private int objectCounter = 0;
    private Map<Class, SmallComponentMapperSetter> componentMap = new HashMap<>();
    private Map<Class, Class> childMapper = new HashMap<>();

    protected void addChildComponent(Class child, Class parent) {
        childMapper.put(child, parent);
    }

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
        if (childMapper.containsKey(componentClass)) {
            componentClass = childMapper.get(componentClass);
        }
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

    private List<GameSystem> systems;

    @Override
    public GameSystem getSystem(String systemName) {
        for (GameSystem system : systems) {
            if (system.getName().equals(systemName)) {
                return system;
            }
        }
        return null;
    }

    @Override
    public List<GameSystem> getSystems() {
        return systems;
    }

    public void setSystems(List<GameSystem> systems) {
        this.systems = systems;
    }

    protected boolean isRunning = false;
    private int lastFrame = 0;

    @Override
    public void update(int frame) {
        if (!isRunning || frame == lastFrame) {
            return;
        }
        for (GameSystem system : getSystems()) {
            system.update(frame);
        }
        lastFrame = frame;
    }
}
