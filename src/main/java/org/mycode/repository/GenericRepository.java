package org.mycode.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    void create(T model);

    T getById(ID readID);

    void update(T updatedModel);

    void delete(ID deletedEntryId);

    List<T> getAll();
}