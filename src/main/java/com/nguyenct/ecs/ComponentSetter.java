package com.nguyenct.ecs;

public interface ComponentSetter<T> {
    void listener(ComponentListener<T> listener);
    void removeListener(ComponentListener<T> listener);
    void set(int entity, T component);
    default void setObject(int entity, Object component) {
        set(entity, (T) component);
    }
    T remove(int entity);
}
