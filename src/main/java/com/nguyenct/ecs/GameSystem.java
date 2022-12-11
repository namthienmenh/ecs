package com.nguyenct.ecs;

public interface GameSystem {

    default String getName() {
        return this.getClass().getSimpleName();
    }
    void update(int frame);
}
