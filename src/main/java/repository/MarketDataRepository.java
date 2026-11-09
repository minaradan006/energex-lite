package repository;

import java.util.List;
import java.util.ArrayList;
import documents.BaseDocument;

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

    public BaseDocument findByCriteria(String dataType, String targetDate) {
        for (BaseDocument doc : storedDocuments) {
        }
        return null;
    }
}
