package com.nguyenct.ecs;

import java.util.List;

public interface Query {
    void set(int entity);
    void remove(int entity);
    List<Integer> getAll();
    Query and(Query query);
    Query or(Query query);
    Query not(Query query);
}
