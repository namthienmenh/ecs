package com.nguyenct.ecs;

import java.util.List;

public interface ComponentMapper<T> {
    T get(int entity);
    boolean has(int entity);
    Query query();
    default List<Integer> getAll(){
        return query().getAll();
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
