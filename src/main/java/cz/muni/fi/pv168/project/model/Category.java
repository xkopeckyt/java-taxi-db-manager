package cz.muni.fi.pv168.project.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Category extends Entity{
    private static final UuidGuidProvider guidProvider = new UuidGuidProvider();
    private static final AtomicLong AL = new AtomicLong();
    private final long id;
    private String name;

    public Category(String name) {
        super(guidProvider.newGuid());
        setName(name);
        this.id = Category.AL.incrementAndGet();
    }

    public Category(String name, long id) {
        super(guidProvider.newGuid());
        setName(name);
        this.id = id;
        if (Category.AL.get() < id) {
            Category.AL.set(id);
        }
    }
    public Category(String name, String guid) {
        super(guid);
        setName(name);
        this.id = Category.AL.incrementAndGet();
    }

    public Category(String name, long id, String guid) {
        super(guid);
        setName(name);
        this.id = id;
        if (Category.AL.get() < id) {
            Category.AL.set(id);
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    @Override
    public String toString() {
        return getName();
    }
}
