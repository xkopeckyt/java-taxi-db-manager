package cz.muni.fi.pv168.project.storage.sql.dao;

import java.util.Collection;
import java.util.Optional;

public interface DataAccessObject<E> {

    E create(E entity);

    Collection<E> findAll();

    Optional<E> findById(long id);

    Optional<E> findByGuid(String guid);

    E update(E entity);

    void deleteByGuid(String guid);

    void deleteAll();

    boolean existsByGuid(String guid);
}
