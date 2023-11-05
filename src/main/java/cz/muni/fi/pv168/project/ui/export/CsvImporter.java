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
import java.util.List;

public class CsvImporter implements BatchImporter {

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

            return new Batch(rides);
        } catch (IOException e) {
            throw new DataManipulationException("Unable to read file", e);
        }
    }

    private Ride parseRide(String line) {
        var ride = line.split(SEPARATOR);

        return new Ride(
                Float.parseFloat(ride[1]),
                LocalDateTime.parse(ride[0]),
                Float.parseFloat(ride[3]),
                Currency.valueOf(ride[4]),
                new Category(ride[5]),
                Integer.parseInt(ride[2])
        );
    }

}
