package ro.ubb.truckers.repository;

import ro.ubb.truckers.domain.entity.BaseEntity;
import ro.ubb.truckers.domain.validator.Validator;
import ro.ubb.truckers.domain.validator.ValidatorException;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

/**
 * The base for each type of repositories which contains the main implementation.
 *
 * @param <ID> represents the type of id of the entity.
 * @param <T> represents the type of the entity itself.
 */
public abstract class BaseRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    private final Validator<T> validator;

    protected BaseRepository(Validator<T> validator) {
        this.validator = validator;
    }

    /**
     * Returns all of the data from the repository.
     *
     * @return a {@code Map} consisting of all the data from the repository.
     */
    public abstract Map<ID, T> getData();

    /**
     * Sets the data in the repository to the given state.
     *
     * @param data represents the new state of data.
     */
    public abstract void setData(Map<ID, T> data);

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id
     *            must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }

        return Optional.ofNullable(getData().get(id));
    }

    /**
     *
     * @return all entities.
     */
    @Override
    public Iterable<T> findAll() {
        return new HashSet<>(getData().values());
    }

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
    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }
        validator.validate(entity);

        var data = getData();
        var result = Optional.ofNullable(data.putIfAbsent(entity.getId(), entity));
        setData(data);

        return result;
    }

    /**
     * Removes the entity with the given id.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }

        var data = getData();
        var result = Optional.ofNullable(data.remove(id));
        setData(data);

        return result;
    }

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
    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }
        validator.validate(entity);

        var data = getData();
        var result = Optional.ofNullable(data.computeIfPresent(entity.getId(), (k, v) -> entity));
        setData(data);

        return result;
    }
}
