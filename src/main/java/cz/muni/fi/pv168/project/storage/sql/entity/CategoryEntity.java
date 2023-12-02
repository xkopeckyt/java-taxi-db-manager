package cz.muni.fi.pv168.project.storage.sql.entity;

import java.util.Objects;

public record CategoryEntity(Integer id, String name, String guid) {
    public CategoryEntity(Integer id, String name, String guid) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.guid = Objects.requireNonNull(guid, "guid must not be null");
    }
}
