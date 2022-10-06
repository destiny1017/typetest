package com.typetest.exception;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class EntityValidationContext<T> {

    public T execute(ErrorStrategy<T> strategy, Optional entity) {
        if(entity.isPresent()) {
            return strategy.call();
        } else {
            throw new EntityNotFoundException("엔티티 정보를 찾을 수 없습니다.");
        }
    }
}
