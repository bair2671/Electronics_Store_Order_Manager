package app.server.repository;

import app.server.model.Entity;

public interface Repository<E extends Entity> {
    E findOne(int id);

    Iterable<E> findAll();

    E save(E entity);

    void delete(int id);

    void update(E entity);

}
