package repository;

import documents.BaseDocument;
import documents.Interval;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The MarketDataQueryService class provides advanced search, range filtering,
 * and latest-start timestamp resolution for stored market data documents.
 */
public final class MarketDataQueryService {

    /**
     * Constructs a new MarketDataQueryService instance.
     */
    public MarketDataQueryService() {
    }

    /**
     * Finds the latest available interval start timestamp matching the given criteria and timestamp limit.
     */
    public String findLatestStart(final List<BaseDocument> storedDocuments,
                                  final String dataType,
                                  final String timestamp) {
        if (dataType == null || timestamp == null) {
            return null;
        }
        String normalizedType = dataType.replace("_", "").toLowerCase();

        String latestIntervalStart = null;
        String latestIntervalFullStart = null;

        for (BaseDocument doc : storedDocuments) {
            String docClassName = doc.getClass().getSimpleName().toLowerCase();
            if (docClassName.equals(normalizedType) && doc.getPoints() != null) {
                String docDate = doc.getPeriodStart();
                for (Object pObj : doc.getPoints()) {
                    Interval interval = (Interval) pObj;
                    String intervalStr = interval.getInterval();
                    if (intervalStr != null && intervalStr.contains("-")) {
                        String[] parts = intervalStr.split("-");
                        String intStartTime = parts[0].trim();
                        String intEndTime = parts[1].trim();

                        String endDate = intEndTime.compareTo(intStartTime) <= 0
                                ? LocalDate.parse(docDate).plusDays(1).toString()
                                : docDate;
                        String intervalEndFull = endDate + "T" + intEndTime;

                        boolean isAvailable = intervalEndFull.compareTo(timestamp) <= 0;

                        if (isAvailable) {
                            String intervalStartFull = docDate + "T" + intStartTime;
                            if (latestIntervalFullStart == null
                                    || intervalStartFull.compareTo(latestIntervalFullStart) > 0) {
                                latestIntervalFullStart = intervalStartFull;
                                latestIntervalStart = intervalStartFull;
                            }
                        }
                    }
                }
            }
        }
        return latestIntervalStart;
    }

    /**
     * Retrieves documents matching a date/time range query criteria.
     */
    public List<BaseDocument> findByCriteriaRange(final MarketDataStore store,
                                                  final String dataType,
                                                  final String from,
                                                  final String to,
                                                  final String timestamp) {
        if (dataType == null || from == null || to == null) {
            return null;
        }
        String normalizedType = dataType.replace("_", "").toLowerCase();

        Map<String, List<Interval>> pointsByDate = new LinkedHashMap<>();

        for (BaseDocument doc : store.getStoredDocuments()) {
            String docClassName = doc.getClass().getSimpleName().toLowerCase();

            if (docClassName.equals(normalizedType) && doc.getPoints() != null) {
                String docDate = doc.getPeriodStart();

                for (Object pObj : doc.getPoints()) {
                    Interval interval = (Interval) pObj;
                    if (isIntervalInDateTimeRange(docDate, interval.getInterval(),
                            from, to, timestamp)) {
                        pointsByDate.computeIfAbsent(docDate, k -> new ArrayList<>())
                                .add(interval);
                    }
                }
            }
        }

        if (pointsByDate.isEmpty()) {
            return null;
        }

        List<BaseDocument> resultDocs = new ArrayList<>();
        List<String> sortedDates = new ArrayList<>(pointsByDate.keySet());
        sortedDates.sort(String::compareTo);

        for (String docDate : sortedDates) {
            List<Interval> matchedPoints = pointsByDate.get(docDate);
            matchedPoints.sort((a, b) -> Integer.compare(
                    a.getIntervalIndex(), b.getIntervalIndex()));

            BaseDocument filteredDoc = store.createEmptyDocument(dataType, docDate);
            if (filteredDoc != null) {
                store.setPointsToDocument(filteredDoc, dataType, matchedPoints);
                resultDocs.add(filteredDoc);
            }
        }

        return resultDocs.isEmpty() ? null : resultDocs;
    }

    private boolean isIntervalInDateTimeRange(final String docDate,
                                              final String intervalStr,
                                              final String rangeStart,
                                              final String rangeEnd,
                                              final String timestamp) {
        if (intervalStr == null || !intervalStr.contains("-")) {
            return false;
        }
        try {
            String[] parts = intervalStr.split("-");
            String intStartTime = parts[0].trim();
            String intEndTime = parts[1].trim();

            String intervalStartFull = docDate + "T" + intStartTime;

            String endDate = intEndTime.compareTo(intStartTime) <= 0
                    ? LocalDate.parse(docDate).plusDays(1).toString()
                    : docDate;
            String intervalEndFull = endDate + "T" + intEndTime;

            boolean inRange = intervalStartFull.compareTo(rangeEnd) < 0
                    && intervalEndFull.compareTo(rangeStart) > 0;
            boolean isAvailable = timestamp == null
                    || intervalEndFull.compareTo(timestamp) <= 0;

            return inRange && isAvailable;
        } catch (Exception e) {
            return false;
        }
    }
}
