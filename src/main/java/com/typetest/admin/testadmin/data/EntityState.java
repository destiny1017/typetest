package com.typetest.admin.testadmin.data;

import lombok.*;

@Getter
@Setter
public class EntityState {
    protected Long id;
    protected Integer deleted = 0;
    protected Integer updated = 0;

    public EntityState() {
    }

    public EntityState(Long id, Integer deleted, Integer updated) {
        this.id = id;
        this.deleted = deleted;
        this.updated = updated;
    }
}
