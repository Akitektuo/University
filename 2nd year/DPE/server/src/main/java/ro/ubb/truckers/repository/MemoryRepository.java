package ro.ubb.truckers.repository;

import ro.ubb.truckers.domain.entity.BaseEntity;
import ro.ubb.truckers.domain.validator.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * The memory repository keeps the data stored in the memory and it is lost when stopping the code.
 *
 * @param <ID> represents the type of id of the entity.
 * @param <T> represents the type of the entity itself.
 */
public class MemoryRepository<ID, T extends BaseEntity<ID>> extends BaseRepository<ID, T> {
    private HashMap<ID, T> data = new HashMap<>();

    protected MemoryRepository(Validator<T> validator) {
        super(validator);
    }

    /**
     * Returns all of the data from the repository.
     *
     * @return a {@code Map} consisting of all the data from the repository.
     */
    @Override
    public Map<ID, T> getData() {
        return data;
    }

    /**
     * Sets the data in the repository to the given state.
     *
     * @param data represents the new state of data.
     */
    @Override
    public void setData(Map<ID, T> data) {
        this.data = (HashMap<ID, T>) data;
    }
}
