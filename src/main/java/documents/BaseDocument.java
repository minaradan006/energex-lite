package documents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
        @JsonSubTypes.Type(value = ImbalanceVolume.class, name = "IMBALANCE_VOLUME")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDocument {
    private String biddingZoneDomain;
    private String periodStart;
    private String periodEnd;
    private String resolution;

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

    public String getBiddingZoneDomain() {
        return biddingZoneDomain;
    }

    public void setBiddingZoneDomain(String biddingZoneDomain) {
        this.biddingZoneDomain = biddingZoneDomain;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }
}