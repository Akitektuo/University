package ro.ubb.truckers.repository.mapper.sql;

import ro.ubb.truckers.domain.entity.BaseEntity;

import java.sql.ResultSet;

/**
 * A sql mapper helps with interpreting the entities from SQL queries, and creating queries based on those entities.
 *
 * @param <ID> representing the ID of the entity mapped.
 * @param <T> representing the entity that is mapped.
 */
public interface SqlMapper<ID, T extends BaseEntity<ID>> {

    /**
     * Maps the {@code ResultSet} to an entity.
     *
     * @param resultSet containing the attributes of the entity.
     * @return a mapped entity of type {@code T}
     */
    T mapResultSetToEntity(ResultSet resultSet);

    /**
     * Maps the id of the entity to a string.
     *
     * @param id to be mapped.
     * @return a {@code String} with the resulting mapped id.
     */
    String mapIdToString(ID id);

    /**
     * Maps the attributes of the entity to a string.
     *
     * @return a {@code String} with the mapped attributes.
     */
    String mapAttributesToString();

    /**
     * Maps the attributes and the values of the entity to a string.
     *
     * @param entity whose values will be mapped.
     * @return a {@code String} with the mapped attributes and values.
     */
    String mapAttributesAndValuesToString(T entity);

    /**
     * Maps the entity to an update string.
     *
     * @param entity that will be mapped into an update string.
     * @return a {@code String} with the update string.
     */
    String mapEntityToUpdateString(T entity);
}
