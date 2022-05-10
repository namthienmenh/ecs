package com.nguyenct.ecs;

import java.util.List;

public interface ComponentMapper<T> {
    void set(int entityId);
    T remove(int entityId);
    T get(int entityId);
    boolean has(int entityId);
    Query query();
}
