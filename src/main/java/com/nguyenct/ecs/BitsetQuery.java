package com.nguyenct.ecs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BitsetQuery implements Query {

    public static final int WORD_SIZE = 32;
    public static final int WORD_LENGTH = 5;
    private int[] words;

    public BitsetQuery() {
        words = new int[2];
    }

    @Override
    public void set(int entity) {
        ensureCapacity(entity);
        int wordIndex = entity >> 5;
        words[wordIndex] |= 1 << entity;
    }

    @Override
    public void remove(int entity) {
        if (entity < words.length* WORD_SIZE) {
            int wordIndex = entity >> WORD_LENGTH;
            words[wordIndex] &= ~(1 << entity);
        }
    }

    @Override
    public List<Integer> getAll() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            int word = words[i];
            int posIndex = i << WORD_LENGTH;
            if (word != 0) {
                for (int j = 0; j < WORD_SIZE; j++) {
                    if ((word & 1<<j) != 0) {
                        result.add(posIndex | j);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Query and(Query query) {
        if (!(query instanceof BitsetQuery)) {
            throw new UnsupportedOperationException("Query must be in SmallQuery");
        }
        BitsetQuery bitsetQuery = (BitsetQuery) query;
        BitsetQuery newQuery = this.cloneToNewQuery();
        for (int i = 0; i < newQuery.words.length; i++) {
            int smallQueryData = bitsetQuery.words.length > i ? bitsetQuery.words[i] : 0;
            newQuery.words[i] &= smallQueryData;
        }
        return newQuery;
    }

    @Override
    public Query or(Query query) {
        if (!(query instanceof BitsetQuery)) {
            throw new UnsupportedOperationException("Query must be in SmallQuery");
        }
        BitsetQuery bitsetQuery = (BitsetQuery) query;
        BitsetQuery newQuery = this.cloneToNewQuery();
        for (int i = 0; i < newQuery.words.length; i++) {
            int smallQueryData = bitsetQuery.words.length > i ? bitsetQuery.words[i] : 0;
            newQuery.words[i] |= smallQueryData;
        }
        return newQuery;
    }

    @Override
    public Query not(Query query) {
        throw new UnsupportedOperationException();
    }

    private void ensureCapacity(int size){
        if (words.length > size>>WORD_LENGTH) {
            return;
        }
        int newSize = (size >> WORD_LENGTH) + 2;
        words = Arrays.copyOf(words, newSize);
    }

    public int size() {
        return this.words.length << WORD_LENGTH;
    }

    BitsetQuery cloneToNewQuery() {
        BitsetQuery query = new BitsetQuery();
        query.words = Arrays.copyOf(this.words, this.words.length);
        return query;
    }
}
