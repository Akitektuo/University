package ro.ubb.truckers.repository;

import ro.ubb.truckers.domain.entity.BaseEntity;
import ro.ubb.truckers.domain.validator.ValidatorException;

import java.util.Optional;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 *
 * @author radu.
 *
 */
public interface Repository<ID, T extends BaseEntity<ID>> {

    /**
     * Find the entity with the given {@code id}.
     * 
     * @param id
     *            must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    Optional<T> findOne(ID id);

    /**
     *
     * @return all entities.
     */
    Iterable<T> findAll();

    /**
     * Saves the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    Optional<T> save(T entity) throws ValidatorException;

    /**
     * Removes the entity with the given id.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    Optional<T> delete(ID id);

    /**
     * Updates the given entity.
     * 
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was not updated otherwise returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    Optional<T> update(T entity) throws ValidatorException;
}
