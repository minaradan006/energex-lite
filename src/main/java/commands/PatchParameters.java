package commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatchParameters {
    private String dataType;
    private List data;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}