package app.server.model.validator;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}

