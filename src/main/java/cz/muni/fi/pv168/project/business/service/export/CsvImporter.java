package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Currency;
import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;


import java.io.IOException;
import java.math.BigDecimal;
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
        Category category = getCategoryByName(ride[7]);
        if (category == null) {
            category = new Category(ride[7], ride[6]);
            categories.add(category);
        }

        return new Ride(
                BigDecimal.valueOf(Double.parseDouble(ride[2])),
                LocalDateTime.parse(ride[1]),
                BigDecimal.valueOf(Double.parseDouble(ride[4])),
                Currency.valueOf(ride[5]),
                category,
                Integer.parseInt(ride[3]),
                ride[0]
        );
    }

}
