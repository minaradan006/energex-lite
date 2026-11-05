package documents;

import java.util.List;

public class DamPricePrediction extends BaseDocument {
    private String currencyUnit;
    private String priceMeasureUnit;
    private List<DamPredictionInterval> points;

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

    public List<DamPredictionInterval> getPoints() {
        return points;
    }

    public void setPoints(List<DamPredictionInterval> points) {
        this.points = points;
    }

    public static class DamPredictionInterval extends Interval {
        private double predictedPriceEurMwh;
        private double spikeProbability;

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
}
