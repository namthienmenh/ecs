package com.nguyenct.ecs;

import java.util.List;

public interface Query {
    void set(int entity);
    void remove(int entity);
    List<Integer> getAll();
    Query and(Query query);
    Query or(Query query);
    Query not(Query query);

    default Query and(ComponentMapper other){
        return and(other.query());
    }
    default Query or(ComponentMapper other){
        return or(other.query());
    }
    default Query not(ComponentMapper other){
        return not(other.query());
    }
}
