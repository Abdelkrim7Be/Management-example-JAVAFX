package com.abdelkrim.management_template.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    void create(T entity) throws SQLException;

    List<T> findAll() throws SQLException;

    T findById(int id) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(int id) throws SQLException;
}
