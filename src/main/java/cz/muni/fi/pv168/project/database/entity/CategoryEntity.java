package cz.muni.fi.pv168.project.database.entity;

import java.util.Objects;

public record CategoryEntity(Integer id, String name) {
    public CategoryEntity(Integer id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
    }
}
