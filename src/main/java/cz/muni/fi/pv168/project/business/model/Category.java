package cz.muni.fi.pv168.project.business.model;

import org.tinylog.Logger;

import java.util.Objects;

public class Category extends Entity{
    private static final UuidGuidProvider guidProvider = new UuidGuidProvider();
    private String name;

    public Category(String name) {
        super(guidProvider.newGuid());
        setName(name);
    }

    public Category(String name, String guid) {
        super(guid);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public static Category createDefaultCategory() {
        return new Category("default");
    }

    @Override
    public String toString() {
        return getName();
    }
}
