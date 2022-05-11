package com.nguyenct.ecs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface ECS {
    int createEntity(Object... components);
    void addComponents(int entity, Object... components);
    List<Object> getComponents(int entity);
    List<Object> removeEntity(int entity);
    void removeComponent(int entity, Class... componentClass);
    void setComponent(int entity, Object... components);

    //get all entity of component
    Collection<? extends ComponentMapper> getAllComponentMappers();
    <T> ComponentMapper<T> mapperOf(Class<T> componentClass);
}
