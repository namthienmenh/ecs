package com.nguyenct.ecs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface MapperSetterECS extends ECS {
    Collection<? extends ComponentSetter> getAllComponentSetter();
    <T> ComponentSetter<T> setterOf(Class<T> componentClass);

    int createEntity();
    void ensureEntityCounter(int entity);

    default int createEntity(Object... components) {
        int entity = createEntity();
        addComponents(entity, components);
        return entity;
    }

    default void addComponents(int entity, Object... components) {
        ensureEntityCounter(entity);
        for (Object component : components) {
            setterOf(component.getClass()).setObject(entity, component);
        }
    }

    default List<Object> getComponents(int entity) {
        Collection<? extends ComponentMapper> all = getAllComponentMappers();
        List<Object> result = new ArrayList<>();
        for (ComponentMapper mapper : all) {
            Object component = mapper.get(entity);
            if (component != null) {
                result.add(component);
            }
        }
        return result;
    }

    default List<Object> removeEntity(int entity) {
        Collection<? extends ComponentSetter> all = getAllComponentSetter();
        List<Object> result = new ArrayList<>();
        for (ComponentSetter setter : all) {
            Object component = setter.remove(entity);
            if (component != null) {
                result.add(component);
            }
        }
        return result;
    }

    default void removeComponent(int entity, Class... componentClass) {
        for (Class clazz : componentClass) {
            setterOf(clazz).remove(entity);
        }
    }

    default void setComponent(int entity, Object... components) {
        Collection<? extends ComponentSetter> all = getAllComponentSetter();
        for (ComponentSetter setter : all) {
            setter.remove(entity);
        }
        addComponents(entity, components);
    }
}
