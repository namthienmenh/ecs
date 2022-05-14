package com.nguyenct.ecs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BitsetQuery implements Query {

    public static final int WORD_SIZE = 64;
    public static final int WORD_LENGTH = 6;
    private long[] words;

    public BitsetQuery() {
        words = new long[1];
    }

    @Override
    public void set(int entity) {
        ensureCapacity(entity);
        int wordIndex = entity >> WORD_LENGTH;
        words[wordIndex] |= 1L << entity;
    }

    @Override
    public void remove(int entity) {
        if (entity < (words.length << WORD_LENGTH)) {
            int wordIndex = entity >> WORD_LENGTH;
            words[wordIndex] &= ~(1L << entity);
        }
    }

    @Override
    public List<Integer> getAll() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            long word = words[i];
            int posIndex = i << WORD_LENGTH;
            if (word != 0) {
                for (int j = 0; j < WORD_SIZE; j += 8) {
                    if ((word & 255L << j) != 0) {
                        for (int k = 0; k < 8; k++) {
                            if ((word & 1L << (j | k)) != 0) {
                                result.add(posIndex | j | k);
                            }
                        }
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
            long smallQueryData = bitsetQuery.words.length > i ? bitsetQuery.words[i] : 0;
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
            long smallQueryData = bitsetQuery.words.length > i ? bitsetQuery.words[i] : 0;
            newQuery.words[i] |= smallQueryData;
        }
        return newQuery;
    }

    @Override
    public Query not(Query query) {
        if (!(query instanceof BitsetQuery)) {
            throw new UnsupportedOperationException("Query must be in SmallQuery");
        }
        BitsetQuery bitsetQuery = (BitsetQuery) query;
        BitsetQuery newQuery = this.cloneToNewQuery();
        for (int i = 0; i < newQuery.words.length; i++) {
            long smallQueryData = bitsetQuery.words.length > i ? bitsetQuery.words[i] : 0;
            newQuery.words[i] &= ~smallQueryData;
        }
        return newQuery;
    }

    private void ensureCapacity(int size) {
        if (words.length > size >> WORD_LENGTH) {
            return;
        }
        int newSize = (size >> WORD_LENGTH) + 1;
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
