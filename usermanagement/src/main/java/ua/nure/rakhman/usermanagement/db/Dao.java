package ua.nure.rakhman.usermanagement.db;

import java.util.Collection;

import ua.nure.rakhman.usermanagement.db.ConnectionFactory;
import ua.nure.rakhman.usermanagement.db.DatabaseException;

public interface Dao <T> {
    T create(T entity) throws DatabaseException;

    void update(T entity) throws DatabaseException;

    void delete(T entity) throws DatabaseException;

    T find(Long id) throws DatabaseException;

    Collection<T> findAll() throws DatabaseException;


    void setConnectionFactory(ConnectionFactory connectionFactory);
}