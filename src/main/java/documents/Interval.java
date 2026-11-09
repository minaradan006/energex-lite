package documents;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Interval {
    private Integer intervalIndex;

    @JsonIgnore
    private String validationError;

    public boolean isValid() {
        return true;
    }

    public String getValidationError() {
        return validationError;
    }

    protected void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    public Integer getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(Integer intervalIndex) {
        this.intervalIndex = intervalIndex;
    }
}
