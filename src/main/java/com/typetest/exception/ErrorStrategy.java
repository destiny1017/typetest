package com.typetest.exception;

public interface ErrorStrategy<T> {
    T call();
}
