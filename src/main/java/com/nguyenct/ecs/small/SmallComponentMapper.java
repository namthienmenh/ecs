package com.nguyenct.ecs.small;

import com.nguyenct.ecs.ComponentMapper;
import com.nguyenct.ecs.Query;

import java.util.Arrays;

public class SmallComponentMapper<T> implements ComponentMapper<T> {
    private SmallQuery query = new SmallQuery();
    private T[] componentStorage = (T[]) new Object[64];
    @Override
    public void set(int entityId, T component) {
        ensureCapacity(entityId);
        componentStorage[entityId] = component;
        query.set(entityId);
    }

    @Override
    public T remove(int entityId) {
        if (entityId > componentStorage.length) {
            return null;
        }
        T old = componentStorage[entityId];
        componentStorage[entityId] = null;
        query.remove(entityId);
        return old;
    }

    @Override
    public T get(int entityId) {
        if (entityId > componentStorage.length) {
            return null;
        }
        return componentStorage[entityId];
    }

    @Override
    public boolean has(int entityId) {
        if (entityId > componentStorage.length) {
            return false;
        }
        return false;
    }

    @Override
    public Query query() {
        return query;
    }

    private void ensureCapacity(int size){
        if (componentStorage.length > size) {
            return;
        }
        componentStorage = Arrays.copyOf(componentStorage, componentStorage.length+32);
    }
}
