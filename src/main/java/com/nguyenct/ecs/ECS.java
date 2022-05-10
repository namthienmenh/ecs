package com.nguyenct.ecs;

import java.util.List;

public interface ECS {
    int createEntity(Object... components);
    List<Object> removeEntity(int entityId);
    List<Object> getComponents(int entity);
    <T> boolean hasComponent(int entity, Class<T> clazz);

    void addComponent(int entity, Object... components);
    void removeComponent(int entity, Object... components);
    void setComponent(int entity, Object... components);

    //get all entity of component
    ComponentMapper mapperOf(Class<? extends Object> componentClass);
    default Query query(Class<? extends Object> componentClass) {
        return mapperOf(componentClass).query();
    }
}
