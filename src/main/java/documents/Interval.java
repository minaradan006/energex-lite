package documents;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a base interval containing timestamp string bounds, index, and validation state.
 * Subclasses extend this class to implement specific validation rules for different market
 * data types.
 */
public class Interval {
    private String interval;
    private Integer intervalIndex;

    @JsonIgnore
    private String validationError;

    /**
     * Validates the interval data point.
     * Can be overridden by subclasses to implement type-specific validation rules safely.
     */
    @JsonIgnore
    public boolean isValid() {
        return true;
    }

    /**
     * Gets the validation error message if the interval failed validation.
     */
    public final String getValidationError() {
        return validationError;
    }

    /**
     * Sets the validation error message for this interval.
     */
    protected final void setValidationError(final String validationError) {
        this.validationError = validationError;
    }

    /**
     * Gets the 1-based interval index.
     */
    public final Integer getIntervalIndex() {
        return intervalIndex;
    }

    /**
     * Sets the 1-based interval index.
     */
    public final void setIntervalIndex(final Integer intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    /**
     * Gets the string representation of the interval.
     */
    public final String getInterval() {
        return interval;
    }

    /**
     * Sets the string representation of the interval.
     */
    public final void setInterval(final String interval) {
        this.interval = interval;
    }
}
