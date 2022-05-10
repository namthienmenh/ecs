package com.nguyenct.ecs.small;

import com.nguyenct.ecs.Query;

import java.util.Arrays;
import java.util.List;

public class SmallQuery implements Query {

    private byte[] queryStorage = new byte[8];

    @Override
    public void set(int entity) {

    }

    @Override
    public void remove(int entity) {

    }

    @Override
    public List<Integer> getAll() {
        return null;
    }

    @Override
    public Query and(Query query) {
        if (!(query instanceof SmallQuery)) {
            throw new UnsupportedOperationException("Query must be in SmallQuery");
        }
        SmallQuery smallQuery = (SmallQuery) query;
        SmallQuery newQuery = this.cloneToNewQuery();
        for (int i = 0; i < newQuery.queryStorage.length; i++) {
            int smallQueryData = smallQuery.queryStorage.length > i ? smallQuery.queryStorage[i] : 0;
            newQuery.queryStorage[i] &= smallQueryData;
        }
        return newQuery;
    }

    @Override
    public Query or(Query query) {
        if (!(query instanceof SmallQuery)) {
            throw new UnsupportedOperationException("Query must be in SmallQuery");
        }
        SmallQuery smallQuery = (SmallQuery) query;
        SmallQuery newQuery = this.cloneToNewQuery();
        for (int i = 0; i < newQuery.queryStorage.length; i++) {
            int smallQueryData = smallQuery.queryStorage.length > i ? smallQuery.queryStorage[i] : 0;
            newQuery.queryStorage[i] |= smallQueryData;
        }
        return newQuery;
    }

    @Override
    public Query not(Query query) {
        throw new UnsupportedOperationException();
    }

    private void ensureCapacity(int size){
        if (queryStorage.length > size) {
            return;
        }
        queryStorage = Arrays.copyOf(queryStorage, queryStorage.length+4);
    }

    SmallQuery cloneToNewQuery() {
        SmallQuery query = new SmallQuery();
        query.queryStorage = Arrays.copyOf(this.queryStorage, this.queryStorage.length);
        return query;
    }
}
