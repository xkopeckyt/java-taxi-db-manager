package cz.muni.fi.pv168.project.ui.filters.Matchers;

public class EntityMatchers {
    private EntityMatchers() {
    }

    public static <T> EntityMatcher<T> all() {
        return new EntityMatcher<>() {
            @Override
            public boolean evaluate(T entity) {
                return true;
            }
        };
    }
}
