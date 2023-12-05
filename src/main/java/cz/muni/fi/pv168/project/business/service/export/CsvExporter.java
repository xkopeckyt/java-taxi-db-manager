package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;

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
                ride.getGuid(),
                ride.getRideDateTime().toString(),
                ride.getDistance().toString(),
                Integer.toString(ride.getPassengersCount()),
                ride.getPrice().toString(),
                ride.getOriginalCurrency().toString(),
                ride.getCategory().toString(),
                ride.getCategory().getGuid());
    }
}
