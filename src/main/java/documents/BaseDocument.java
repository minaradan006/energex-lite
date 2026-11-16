package documents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "id",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DamPrice.class, name = "DAM_PRICE"),
        @JsonSubTypes.Type(value = DamPricePrediction.class, name = "DAM_PRICE_PREDICTION"),
        @JsonSubTypes.Type(value = GridLoad.class, name = "GRID_LOAD"),
        @JsonSubTypes.Type(value = GridLoadPrediction.class, name = "GRID_LOAD_PREDICTION"),
        @JsonSubTypes.Type(value = ImbalancePrice.class, name = "IMBALANCE_PRICE"),
        @JsonSubTypes.Type(value = ImbalanceVolume.class, name = "ImbalanceVolume")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "id", "date", "resolution", "biddingZoneDomain", "periodEnd", "data" })
/**
 * Represents the abstract base document for all market data types,
 * defining common attributes like bidding zone, period, resolution, and validation state.
 */
public abstract class BaseDocument {
    private String biddingZoneDomain;

    @JsonProperty("date")
    private String periodStart;

    private String periodEnd;
    private String resolution;

    @JsonIgnore
    private String validationError;

    /**
     * Validates the document structure and its points.
     * Can be overridden by subclasses to implement specific validation logic safely.
     */
    @JsonIgnore
    public boolean isValid() {
        return true;
    }

    /**
     * Gets the validation error message if the document failed validation.
     */
    public final String getValidationError() {
        return validationError;
    }

    /**
     * Sets the validation error message for this document.
     */
    protected final void setValidationError(final String validationError) {
        this.validationError = validationError;
    }

    /**
     * Gets the bidding zone domain.
     */
    public final String getBiddingZoneDomain() {
        return biddingZoneDomain;
    }

    /**
     * Sets the bidding zone domain.
     */
    public final void setBiddingZoneDomain(final String biddingZoneDomain) {
        this.biddingZoneDomain = biddingZoneDomain;
    }

    /**
     * Gets the period start date.
     */
    public final String getPeriodStart() {
        return periodStart;
    }

    /**
     * Sets the period start date.
     */
    public final void setPeriodStart(final String periodStart) {
        this.periodStart = periodStart;
    }

    /**
     * Gets the period end date/time.
     */
    public final String getPeriodEnd() {
        return periodEnd;
    }

    /**
     * Sets the period end date/time.
     */
    public final void setPeriodEnd(final String periodEnd) {
        this.periodEnd = periodEnd;
    }

    /**
     * Gets the resolution of the document data.
     */
    public final String getResolution() {
        return resolution;
    }

    /**
     * Sets the resolution of the document data.
     */
    public final void setResolution(final String resolution) {
        this.resolution = resolution;
    }

    /**
     * Gets the string identifier representing the document type.
     */
    @JsonProperty("id")
    public final String getId() {
        if (this instanceof DamPrice) {
            return "DAM_PRICE";
        }
        if (this instanceof DamPricePrediction) {
            return "DAM_PRICE_PREDICTION";
        }
        if (this instanceof GridLoad) {
            return "GRID_LOAD";
        }
        if (this instanceof GridLoadPrediction) {
            return "GRID_LOAD_PREDICTION";
        }
        if (this instanceof ImbalancePrice) {
            return "IMBALANCE_PRICE";
        }
        if (this instanceof ImbalanceVolume) {
            return "IMBALANCE_VOLUME";
        }
        return null;
    }

    /**
     * Gets the list of interval points contained in the document.
     */
    @JsonProperty("data")
    public abstract List<? extends Interval> getPoints();
}
