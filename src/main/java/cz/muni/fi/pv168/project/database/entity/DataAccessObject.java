package cz.muni.fi.pv168.project.database.entity;

import java.util.Collection;
import java.util.Optional;

public interface DataAccessObject<E> {

    E create(E entity);

    Collection<E> findAll();

    Optional<E> findById(Integer id);

    Optional<E> findByGuid(String guid);

    E update(E entity);

    void deleteByGuid(String guid);

    void deleteAll();

    boolean existsByGuid(String guid);
}
