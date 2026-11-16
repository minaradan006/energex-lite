package repository;

import documents.BaseDocument;
import documents.DamPrice;
import documents.DamPricePrediction;
import documents.GridLoad;
import documents.GridLoadPrediction;
import documents.ImbalancePrice;
import documents.ImbalanceVolume;
import documents.Interval;
import java.util.ArrayList;
import java.util.List;

/**
 * The MarketDataStore class manages in-memory storage, retrieval,
 * and mutations for various market data documents and their intervals.
 */
public final class MarketDataStore {
    private final List<BaseDocument> storedDocuments = new ArrayList<>();

    /**
     * Constructs a new MarketDataStore instance.
     */
    public MarketDataStore() {
    }

    /**
     * Inserts a document into storage if it is not null.
     */
    public void insert(final BaseDocument document) {
        if (document != null) {
            storedDocuments.add(document);
        }
    }

    /**
     * Gets the list of all stored documents.
     */
    public List<BaseDocument> getStoredDocuments() {
        return storedDocuments;
    }

    /**
     * Finds a stored document matching the specified data type and timestamp criteria.
     */
    public BaseDocument findByCriteria(final String dataType, final String targetTimestamp) {
        if (dataType == null) {
            return null;
        }
        String normalizedType = dataType.replace("_", "").toLowerCase();

        String targetDate = (targetTimestamp != null && targetTimestamp.contains("T"))
                ? targetTimestamp.split("T")[0]
                : targetTimestamp;

        for (BaseDocument doc : storedDocuments) {
            String docClassName = doc.getClass().getSimpleName().toLowerCase();

            if (docClassName.equals(normalizedType)) {
                if (targetDate == null || targetDate.equals(doc.getPeriodStart())) {
                    return doc;
                }
            }
        }
        return null;
    }

    /**
     * Removes an interval point from a document matching the criteria.
     */
    public void removePoint(final String dataType, final String date, final int intervalIndex) {
        BaseDocument doc = findByCriteria(dataType, date);
        if (doc != null && doc.getPoints() != null) {
            doc.getPoints().removeIf(p -> ((Interval) p).getIntervalIndex() == intervalIndex);
        }
    }

    /**
     * Inserts or updates an interval point in the matching document,
     * creating an empty document if one does not already exist.
     */
    public void upsertPoint(final String dataType,
                            final String date,
                            final Interval newInterval) {
        BaseDocument existingDoc = findByCriteria(dataType, date);

        if (existingDoc == null) {
            existingDoc = createEmptyDocument(dataType, date);
            if (existingDoc == null) {
                return;
            }
            storedDocuments.add(existingDoc);
        }

        addOrUpdateInterval(existingDoc.getPoints(), newInterval);
    }

    @SuppressWarnings("unchecked")
    private <T extends Interval> void addOrUpdateInterval(final List<T> points,
                                                          final Interval newInterval) {
        if (points == null) {
            return;
        }
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).getIntervalIndex() == newInterval.getIntervalIndex()) {
                points.set(i, (T) newInterval);
                return;
            }
        }
        points.add((T) newInterval);
        points.sort((a, b) -> Integer.compare(a.getIntervalIndex(), b.getIntervalIndex()));
    }

    /**
     * Sets matched points to the appropriate strongly-typed list on a target document.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void setPointsToDocument(final BaseDocument doc,
                                    final String dataType,
                                    final List<Interval> matchedPoints) {
        if (dataType == null) {
            return;
        }
        switch (dataType.toUpperCase()) {
            case "DAM_PRICE" -> ((DamPrice) doc).setPoints((List) matchedPoints);
            case "DAM_PRICE_PREDICTION" ->
                    ((DamPricePrediction) doc).setPoints((List) matchedPoints);
            case "GRID_LOAD" -> ((GridLoad) doc).setPoints((List) matchedPoints);
            case "GRID_LOAD_PREDICTION" ->
                    ((GridLoadPrediction) doc).setPoints((List) matchedPoints);
            case "IMBALANCE_PRICE" -> ((ImbalancePrice) doc).setPoints((List) matchedPoints);
            case "IMBALANCE_VOLUME" -> ((ImbalanceVolume) doc).setPoints((List) matchedPoints);
            default -> {
            }
        }
    }

    /**
     * Creates an empty document instance for the specified data type and date.
     */
    public BaseDocument createEmptyDocument(final String dataType, final String date) {
        BaseDocument doc = switch (dataType.toUpperCase()) {
            case "DAM_PRICE" -> {
                DamPrice d = new DamPrice();
                d.setPoints(new ArrayList<>());
                yield d;
            }
            case "DAM_PRICE_PREDICTION" -> {
                DamPricePrediction d = new DamPricePrediction();
                d.setPoints(new ArrayList<>());
                yield d;
            }
            case "GRID_LOAD" -> {
                GridLoad d = new GridLoad();
                d.setPoints(new ArrayList<>());
                yield d;
            }
            case "GRID_LOAD_PREDICTION" -> {
                GridLoadPrediction d = new GridLoadPrediction();
                d.setPoints(new ArrayList<>());
                yield d;
            }
            case "IMBALANCE_PRICE" -> {
                ImbalancePrice d = new ImbalancePrice();
                d.setPoints(new ArrayList<>());
                yield d;
            }
            case "IMBALANCE_VOLUME" -> {
                ImbalanceVolume d = new ImbalanceVolume();
                d.setPoints(new ArrayList<>());
                yield d;
            }
            default -> null;
        };

        if (doc != null) {
            doc.setPeriodStart(date);
        }
        return doc;
    }
}
