package repository;

import documents.BaseDocument;
import documents.BaseGridLoad;
import java.util.List;

/**
 * The GridLoadValidator class enforces business rules and delta validation limits
 * for grid load quantities between adjacent intervals.
 */
public final class GridLoadValidator {
    private static final double MAX_QUANTITY_CHANGE = 10000.0;

    public GridLoadValidator() {
    }

    /**
     * Validates that the quantity change between the new point and the preceding interval
     * does not exceed the allowed threshold, and upserts the point if valid.
     */
    public String validateAndUpsertGridLoad(final MarketDataStore store,
                                            final String dataType,
                                            final String date,
                                            final BaseGridLoad.GridLoadInterval newPoint) {
        if ("GRID_LOAD".equalsIgnoreCase(dataType)) {
            BaseDocument existingDoc = store.findByCriteria(dataType, date);
            if (existingDoc != null && existingDoc.getPoints() != null) {
                @SuppressWarnings("unchecked")
                List<BaseGridLoad.GridLoadInterval> points =
                        (List<BaseGridLoad.GridLoadInterval>) existingDoc.getPoints();

                BaseGridLoad.GridLoadInterval precedingPoint = null;
                for (BaseGridLoad.GridLoadInterval p : points) {
                    if (p.getIntervalIndex() < newPoint.getIntervalIndex()) {
                        if (precedingPoint == null
                                || p.getIntervalIndex() > precedingPoint.getIntervalIndex()) {
                            precedingPoint = p;
                        }
                    }
                }

                if (precedingPoint != null
                        && precedingPoint.getQuantity() != null
                        && newPoint.getQuantity() != null) {
                    double diff = Math.abs(newPoint.getQuantity() - precedingPoint.getQuantity());
                    if (diff > MAX_QUANTITY_CHANGE) {
                        return "Quantity change exceeds 10000 MW compared with the "
                                + "previous interval; interval skipped.";
                    }
                }
            }
        }

        store.upsertPoint(dataType, date, newPoint);
        return null;
    }
}
