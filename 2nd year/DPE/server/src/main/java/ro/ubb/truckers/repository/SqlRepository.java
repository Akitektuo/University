package ro.ubb.truckers.repository;

import org.postgresql.jdbc.PgConnection;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.BaseEntity;
import ro.ubb.truckers.domain.validator.Validator;
import ro.ubb.truckers.domain.validator.ValidatorException;
import ro.ubb.truckers.repository.mapper.sql.SqlMapper;
import ro.ubb.truckers.util.ServiceProvider;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static ro.ubb.truckers.util.Constants.EXECUTE_QUERY_ERROR;

/**
 * The SQL repository keeps the data stored in a database.
 *
 * @param <ID> represents the type of id of the entity.
 * @param <T>  represents the type of the entity itself.
 */
public class SqlRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    private final PgConnection connection = ServiceProvider.inject(PgConnection.class);

    private final String tableName;
    private final Validator<T> validator;
    private final SqlMapper<ID, T> mapper;

    protected SqlRepository(String tableName, Validator<T> validator, SqlMapper<ID, T> mapper) {
        this.tableName = tableName;
        this.validator = validator;
        this.mapper = mapper;
    }

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }

        T result = null;
        var query = String.format("SELECT * FROM %s WHERE %s;", tableName, mapper.mapIdToString(id));

        try (var selectStatement = connection.createStatement();
             var resultSet = selectStatement.executeQuery(query)) {

            if (resultSet.next()) {
                result = mapper.mapResultSetToEntity(resultSet);
            }
        } catch (SQLException exception) {
            throw new TruckersException(EXECUTE_QUERY_ERROR);
        }

        return Optional.ofNullable(result);
    }

    /**
     * @return all entities.
     */
    @Override
    public Iterable<T> findAll() {
        List<T> entities = new LinkedList<>();

        var query = String.format("SELECT * FROM %s", tableName);

        try (var selectStatement = connection.createStatement();
             var resultSet = selectStatement.executeQuery(query)) {
            while (resultSet.next()) {
                entities.add(mapper.mapResultSetToEntity(resultSet));
            }
        } catch (SQLException exception) {
            throw new TruckersException(EXECUTE_QUERY_ERROR);
        }

        return new HashSet<>(entities);
    }

    /**
     * Saves the given entity.
     *
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException       if the entity is not valid.
     */
    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        if (entity == null)
            throw new IllegalArgumentException("Entity must not be null.");

        validator.validate(entity);

        var result = findOne(entity.getId());

        if (result.isPresent()) {
            return result;
        }

        var query = String.format("INSERT INTO %s %s VALUES %s",
                tableName, mapper.mapAttributesToString(), mapper.mapAttributesAndValuesToString(entity));

        try (var insertStatement = connection.createStatement()) {
            insertStatement.executeUpdate(query);
        } catch (SQLException exception) {
            throw new TruckersException("Could not insert entity.");
        }

        return result;
    }

    /**
     * Removes the entity with the given id.
     *
     * @param id must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }

        var result = findOne(id);
        var query = String.format("DELETE FROM %s WHERE %s;", tableName, mapper.mapIdToString(id));

        try (var selectStatement = connection.createStatement()) {
            selectStatement.executeUpdate(query);
        } catch (SQLException exception) {
            throw new TruckersException("Could not delete entity.");
        }

        return result;
    }

    /**
     * Updates the given entity.
     *
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was not updated otherwise returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException       if the entity is not valid.
     */
    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }

        validator.validate(entity);

        var result = findOne(entity.getId());
        if (result.isEmpty()) {
            return Optional.empty();
        }

        var query = String.format("UPDATE %s SET %s WHERE %s;",
                tableName, mapper.mapEntityToUpdateString(entity), mapper.mapIdToString(entity.getId()));

        try (Statement selectStatement = connection.createStatement()) {
            selectStatement.executeUpdate(query);
        } catch (SQLException exception) {
            throw new TruckersException("Could not update entity.");
        }

        return Optional.of(entity);
    }
}
