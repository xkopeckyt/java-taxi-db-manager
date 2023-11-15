package cz.muni.fi.pv168.project.ui.resources;

import javax.swing.*;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Icons {
    public static final Icon SHOW_ICON = createIcon("show.png");
    public static final Icon NEW_ICON = createIcon("new_ride.png");
    public static final Icon NEW_TEMPLATE_ICON = createIcon("new_ride_template.png");
    public static final Icon EDIT_ICON = createIcon("edit.png");
    public static final Icon DELETE_ICON = createIcon("delete.png");
    public static final Icon IMPORT_ICON = createIcon("import.png");
    public static final Icon EXPORT_ICON = createIcon("export.png");
    public static final Icon LICENSE_ICON = createIcon("license.png");
    public static final Icon SELECT_ICON = createIcon("radiobutton_list.png");

    private Icons() {
        throw new AssertionError("This class is not instantiable");
    }

    public static <E extends Enum<E>> Map<E, Icon> createEnumIcons(Class<E> clazz, int width) {
        return Stream.of(clazz.getEnumConstants())
                .collect(Collectors.toUnmodifiableMap(
                        Function.identity(),
                        e -> createIcon(clazz.getSimpleName() + "." + e.name() + "-" + width + ".png")));
    }

    private static ImageIcon createIcon(String name) {
        URL url = Icons.class.getResource(name);
        if (url == null) {
            throw new IllegalArgumentException("Icon resource not found on classpath: " + name);
        }
        return new ImageIcon(url);
    }
}