package repository;

import java.util.List;
import java.util.ArrayList;
import documents.BaseDocument;
import documents.Interval;

public class MarketDataRepository {
    private final List<BaseDocument> storedDocuments = new ArrayList<>();

    public void insert(BaseDocument document) {
        if (document != null) {
            storedDocuments.add(document);
        }
    }

    public List<BaseDocument> getStoredDocuments() {
        return storedDocuments;
    }

    public BaseDocument findByCriteria(String dataType, String periodStart) {
        for (BaseDocument doc : storedDocuments) {
            String normalizedType = dataType.replace("_", "").toLowerCase();
            String docClassName = doc.getClass().getSimpleName().toLowerCase();

            if (docClassName.contains(normalizedType)) {
                if (periodStart == null || periodStart.equals(doc.getPeriodStart())) {
                    return doc;
                }
            }
        }
        return null;
    }

    public void patchPoint(String dataType, Interval newInterval) {
        BaseDocument existingDoc = findByCriteria(dataType, null);

        if (existingDoc == null) {
            return;
        }

        List<? extends Interval> points = existingDoc.getPoints();
        if (points != null) {
            for (int i = 0; i < points.size(); i++) {
                Interval existingInterval = points.get(i);

                if (existingInterval.getIntervalIndex() == newInterval.getIntervalIndex()) {
                    updateIntervalList(points, i, newInterval);
                    return;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Interval> void updateIntervalList(List<T> points, int index, Interval newInterval) {
        points.set(index, (T) newInterval);
    }
}