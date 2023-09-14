package ru.karelin.project.validators;

public interface Validator<T> {
    public void validate(T item);
}
