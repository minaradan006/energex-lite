package repository;

import documents.BaseDocument;
import documents.BaseGridLoad;
import documents.Interval;
import java.util.List;

/**
 * The MarketDataRepository class acts as a facade coordinating the underlying storage,
 * querying services, and grid load validation rules.
 */
public final class MarketDataRepository {
    private final MarketDataStore store = new MarketDataStore();
    private final MarketDataQueryService queryService = new MarketDataQueryService();
    private final GridLoadValidator gridLoadValidator = new GridLoadValidator();

    /**
     * Constructs a new MarketDataRepository instance.
     */
    public MarketDataRepository() {
    }

    /**
     * Inserts a document into the repository storage.
     */
    public void insert(final BaseDocument document) {
        store.insert(document);
    }

    /**
     * Retrieves all stored documents.
     */
    public List<BaseDocument> getStoredDocuments() {
        return store.getStoredDocuments();
    }

    /**
     * Finds a document matching the given data type and target timestamp/date.
     */
    public BaseDocument findByCriteria(final String dataType, final String targetTimestamp) {
        return store.findByCriteria(dataType, targetTimestamp);
    }

    /**
     * Removes an interval point from a specific document based on criteria.
     */
    public void removePoint(final String dataType,
                            final String date,
                            final int intervalIndex) {
        store.removePoint(dataType, date, intervalIndex);
    }

    /**
     * Finds the latest available interval start timestamp for a given criteria.
     */
    public String findLatestStart(final String dataType, final String timestamp) {
        return queryService.findLatestStart(store.getStoredDocuments(), dataType, timestamp);
    }

    /**
     * Retrieves documents matching a date/time range query criteria.
     */
    public List<BaseDocument> findByCriteriaRange(final String dataType,
                                                  final String from,
                                                  final String to,
                                                  final String timestamp) {
        return queryService.findByCriteriaRange(store, dataType, from, to, timestamp);
    }

    /**
     * Upserts an interval point into the appropriate document.
     */
    public void upsertPoint(final String dataType,
                            final String date,
                            final Interval newInterval) {
        store.upsertPoint(dataType, date, newInterval);
    }

    /**
     * Validates and upserts a grid load interval point with delta checks.
     */
    public String validateAndUpsertGridLoad(final String dataType,
                                            final String date,
                                            final BaseGridLoad.GridLoadInterval newPoint) {
        return gridLoadValidator.validateAndUpsertGridLoad(store, dataType, date, newPoint);
    }
}
