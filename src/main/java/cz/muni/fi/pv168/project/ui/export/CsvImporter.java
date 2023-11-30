package cz.muni.fi.pv168.project.ui.export;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.export.batch.Batch;
import cz.muni.fi.pv168.project.ui.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.ui.export.format.Format;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvImporter implements BatchImporter {
    private static final List<Category> categories = new ArrayList<>();
    private static final String SEPARATOR = ",";
    private static final Format FORMAT = new Format("CSV", List.of("csv"));

    @Override
    public Format getFormat() {
        return FORMAT;
    }

    @Override
    public Batch importBatch(String filePath) {
        try(var reader = Files.newBufferedReader(Path.of(filePath))) {
            var rides = reader.lines()
                    .map(this::parseRide)
                    .toList();
            Batch batch = new Batch(rides, List.copyOf(categories));
            categories.clear();
            return batch;
        } catch (IOException e) {
            throw new DataManipulationException("Unable to read file", e);
        }
    }

    private Category getCategoryByName(String name) {
        for (var category : categories) {
            if (category.getName().equals(name))
                return category;
        }
        return null;
    }

    private Ride parseRide(String line) {
        var ride = line.split(SEPARATOR);
        Category category = getCategoryByName(ride[6]);
        if (category == null) {
            category = new Category(ride[6], ride[7]);
            categories.add(category);
        }

       /* ride.getGuid(),
        ride.getDateTime().toString(),
        Float.toString(ride.getDistance()),
        Integer.toString(ride.getPassengersCount()),
        Float.toString(ride.getPrice()),
        ride.getOriginalCurrency().toString(),
        ride.getCategory().toString(),
        ride.getCategory().getGuid()*/

        return new Ride(
                Float.parseFloat(ride[2]),
                LocalDateTime.parse(ride[1]),
                Float.parseFloat(ride[4]),
                Currency.valueOf(ride[5]),
                category,
                Integer.parseInt(ride[3]),
                ride[6]
        );
    }

}
