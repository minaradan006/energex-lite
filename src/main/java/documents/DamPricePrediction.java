package documents;

import java.util.List;

public class DamPricePrediction extends {
    private String currencyUnit;
    private String priceMeasureUnit;

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getPriceMeasureUnit() {
        return priceMeasureUnit;
    }

    public void setPriceMeasureUnit(String priceMeasureUnit) {
        this.priceMeasureUnit = priceMeasureUnit;
    }
}

class Interval {
    private int intervalIndex;
    private double predictedPriceEurMwh;
    private double spikeProbability;

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public double getPredictedPriceEurMwh() {
        return predictedPriceEurMwh;
    }

    public void setPredictedPriceEurMwh(double predictedPriceEurMwh) {
        this.predictedPriceEurMwh = predictedPriceEurMwh;
    }

    public double getSpikeProbability() {
        return spikeProbability;
    }

    public void setSpikeProbability(double spikeProbability) {
        this.spikeProbability = spikeProbability;
    }
}