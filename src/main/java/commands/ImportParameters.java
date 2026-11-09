package commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import documents.BaseDocument;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImportParameters {
    private List<BaseDocument> data;

    public List<BaseDocument> getData() {
        return data;
    }

    public void setData(List<BaseDocument> data) {
        this.data = data;
    }
}