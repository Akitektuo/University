package ro.ubb.truckers.domain.validator;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
