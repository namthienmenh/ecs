package com.nguyenct.ecs;

import java.util.ArrayList;
import java.util.List;

public interface ComponentMapper<T> {
    T get(int entity);
    default List<T> get(List<Integer> entities) {
        List<T> result = new ArrayList<>();
        for (Integer entity : entities) {
            result.add(get(entity));
        }
        return result;
    }
    boolean has(int entity);
    Query query();
    default List<Integer> getAll(){
        return query().getAll();
    }
    default List<T> getAllComponents(){
        return get(getAll());
    }
    default Query and(ComponentMapper other) {
        return query().and(other);
    }
    default Query or(ComponentMapper other) {
        return query().or(other);
    }
    default Query not(ComponentMapper other) {
        return query().not(other);
    }

}
