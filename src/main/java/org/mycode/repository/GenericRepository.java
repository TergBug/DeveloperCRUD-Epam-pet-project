package org.mycode.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    void create(T model) throws RuntimeException;
    T getById(ID readID) throws RuntimeException;
    void update(T updatedModel) throws RuntimeException;
    void delete(ID deletedEntry) throws RuntimeException;
    List<T> getAll() throws RuntimeException;
}