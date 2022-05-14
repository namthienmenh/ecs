package com.nguyenct.ecs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ECSTest {

    @Test
    void testComponentMapper() throws Exception
    {
        SmallComponentMapperSetter<Component1> mapper = new SmallComponentMapperSetter<>();
        Component1[] sample = new Component1[500];
        for (int i = 0; i < sample.length; i++) {
            if (i==31) {
                System.out.println("he he");
            }
            Component1 component = new Component1(i, "#"+i);
            mapper.set(i, component);
            sample[i] = component;
            compareSampleAndEntity(sample, mapper);
        }

        for (int i = 0; i < 500; i++) {
            assertEquals(true, mapper.has(i));
            mapper.remove(i);
            sample[i] = null;
            compareSampleAndEntity(sample, mapper);
        }
    }

    @Test
    void testComponentMapperRandom() throws Exception {
        SmallComponentMapperSetter<Component1> mapper = new SmallComponentMapperSetter<>();
        Component1[] sample = new Component1[500];
        Random random = new Random();
        for (int i = 1; i < sample.length; i++) {
            int pos = random.nextInt(sample.length);
            Component1 component = new Component1(pos, "#"+i);
            sample[pos] = component;
            mapper.set(pos, component);
            compareSampleAndEntity(sample, mapper);
        }

        for (int i = 1; i < sample.length; i++) {
            int pos = random.nextInt(sample.length);
            sample[pos] = null;
            mapper.remove(pos);
            compareSampleAndEntity(sample, mapper);
        }
    }

    @Test
    void testECS2Components() throws Exception {
        ECS ecs = new SmallECS();
        int length = 500;
        Component1[] sample1 = new Component1[length];
        Component2[] sample2 = new Component2[length];
        Random random = new Random();
        ComponentMapper<Component1> mapper1 = ecs.mapperOf(Component1.class);
        ComponentMapper<Component2> mapper2 = ecs.mapperOf(Component2.class);
        int max = 0;
        for (int i = 1; i < length*10; i++) {
            int pos = random.nextInt(length);
            Component1 component1 = new Component1(pos, "#"+i);
            sample1[pos] = component1;
            Component2 component2 = new Component2(pos, "#"+i, "-"+i);
            sample2[pos] = component2;
            ecs.addComponents(pos, component1, component2);
            compareSampleAndEntity(sample1, mapper1);
            compareSampleAndEntity(sample2, mapper2);
            max = Math.max(max, pos);
        }

        for (int i = 1; i < length*10; i++) {
            int pos = random.nextInt(length);
            sample1[pos] = null;
            ecs.removeComponent(pos, Component1.class);
            compareSampleAndEntity(sample1, mapper1);
            compareSampleAndEntity(sample2, mapper2);
        }
        int entityId = ecs.createEntity();
        assert entityId > max;
        assert entityId == max+1;
    }

    private <T> void compareSampleAndEntity(T[] sample, ComponentMapper<T> mapper) {
        List<Integer> all = new ArrayList<>();
        for (int i = 0; i < sample.length; i++) {
            T s = sample[i];
            assertEquals(s, mapper.get(i));
            if (s!=null){
                all.add(i);
            }
        }
        List<Integer> entities = mapper.getAll();
        if (entities.size() != all.size()) {
            entities = mapper.getAll();
        }
        assertEquals(entities.size(), all.size());
        for (int i = 0; i < entities.size(); i++) {
            assertEquals(all.get(i), entities.get(i));
        }
    }

}
