package com.nguyenct.ecs;

import java.util.List;

public interface Query {
    List<Integer> getAll();
    ComponentMapper and(ComponentMapper mapper);
    ComponentMapper or(ComponentMapper mapper);
    ComponentMapper xor(ComponentMapper mapper);
}
