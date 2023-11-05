package cz.muni.fi.pv168.project.ui.export.batch;

import cz.muni.fi.pv168.project.model.Ride;

import java.util.Collection;

public record Batch(Collection<Ride> rides) {
}
