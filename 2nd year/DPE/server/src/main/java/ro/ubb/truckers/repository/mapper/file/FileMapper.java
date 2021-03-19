package ro.ubb.truckers.repository.mapper.file;

/**
 * A file mapper helps with interpreting the read data from the files and with formatting the entity for writing data
 * into files.
 *
 * @param <T> representing the type of entity that is mapped.
 */
public interface FileMapper<T> {

    /**
     * Maps the given entity to a {@code String[]}.
     *
     * @param entity to map to a {@code String[]}.
     * @return the entity as a {@code String[]}.
     */
    String[] mapEntityToStringArray(T entity);

    /**
     * Maps the given {@code String[]} to the entity {@code T} of the mapper.
     *
     * @param listOfProperties representing each property as {@code String} in a similar order as the constructor.
     * @return a mapped entity of type {@code T}.
     */
    T mapStringArrayToEntity(String[] listOfProperties);
}
