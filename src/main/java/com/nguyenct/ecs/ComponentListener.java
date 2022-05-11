package com.nguyenct.ecs;

public interface ComponentListener<T>
{
    void whenAdd(int entity, T component);
    void whenRemove(int entity, T component);
    void whenUpdate(int entity, T old, T component);
}
