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
        assertEquals(entities.size(), all.size());
        for (int i = 0; i < entities.size(); i++) {
            assertEquals(all.get(i), entities.get(i));

        }

    }
}