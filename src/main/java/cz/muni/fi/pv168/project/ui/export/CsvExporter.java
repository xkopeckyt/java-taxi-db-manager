package cz.muni.fi.pv168.project.ui.export;

import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.export.batch.Batch;
import cz.muni.fi.pv168.project.ui.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.ui.export.format.Format;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvExporter implements BatchExporter {

    private static final String SEPARATOR = ",";
    private static final Format FORMAT = new Format("CSV", List.of("csv"));

    @Override
    public Format getFormat() {
        return FORMAT;
    }

    @Override
    public void exportBatch(Batch batch, String filePath) {

        try (var writer = Files.newBufferedWriter(Path.of(filePath), StandardCharsets.UTF_8)) {
            for (var ride : batch.rides()) {
                String line = createCsvLine(ride);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {
            throw new DataManipulationException("Unable to write to file", exception);
        }
    }

    private String createCsvLine(Ride ride) {
        return serializeRide(ride);
    }

    private String serializeRide(Ride ride) {
        return String.join(SEPARATOR,
                ride.getDateTime().toString(),
                Float.toString(ride.getDistance()),
                Integer.toString(ride.getPassengersCount()),
                Float.toString(ride.getPrice()),
                ride.getOriginalCurrency().toString(),
                ride.getCategory().toString());
    }
}
