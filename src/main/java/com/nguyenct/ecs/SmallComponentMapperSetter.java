package com.nguyenct.ecs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmallComponentMapperSetter<T> implements ComponentMapper<T>, ComponentSetter<T> {
    private Query query;
    private T[] componentStorage;
    private List<ComponentListener> listeners;

    private static final int SIZE_LENGTH = 5;

    public SmallComponentMapperSetter() {
        this.query = new BitsetQuery();
        componentStorage = (T[]) new Object[2 << SIZE_LENGTH];
        listeners = new ArrayList<>();
    }

    @Override
    public void listener(ComponentListener<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ComponentListener<T> listener) {
        listeners.remove(listener);
    }

    @Override
    public void set(int entity, T component) {
        if (component == null) {
            return;
        }
        ensureCapacity(entity);
        T old = componentStorage[entity];
        componentStorage[entity] = component;
        for (ComponentListener listener : listeners) {
            if (old == null) {
                listener.whenAdd(entity, component);
            }else {
                listener.whenUpdate(entity, old, component);
            }
        }

        query.set(entity);
    }

    @Override
    public T remove(int entity) {
        if (entity >= componentStorage.length) {
            return null;
        }
        T old = componentStorage[entity];
        componentStorage[entity] = null;
        query.remove(entity);
        for (ComponentListener listener : listeners) {
            listener.whenRemove(entity, old);
        }
        return old;
    }

    @Override
    public T get(int entity) {
        if (entity >= componentStorage.length) {
            return null;
        }
        return componentStorage[entity];
    }

    @Override
    public boolean has(int entity) {
        if (entity >= componentStorage.length) {
            return false;
        }
        return componentStorage[entity] != null;
    }

    @Override
    public Query query() {
        return query;
    }

    private void ensureCapacity(int size){
        if (componentStorage.length > size) {
            return;
        }
        int newSize = ((size >> SIZE_LENGTH) + 2) << SIZE_LENGTH;
        componentStorage = Arrays.copyOf(componentStorage, newSize);
    }
}
