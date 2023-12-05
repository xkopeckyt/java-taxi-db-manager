package cz.muni.fi.pv168.project.business.service.export.batch;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Ride;

import java.util.Collection;

public record Batch(Collection<Ride> rides, Collection<Category> categories) {
}
