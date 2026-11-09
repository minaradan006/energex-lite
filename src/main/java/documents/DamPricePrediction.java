package documents;

import java.util.List;

public class DamPricePrediction extends BaseDocument {
    private String currencyUnit;
    private String priceMeasureUnit;
    private List<DamPredictionInterval> points;

    @Override
    public boolean isValid() {
        for (DamPredictionInterval point : points) {
            if (!point.isValid()) {
                setValidationError(point.getValidationError());
                return false;
            }
        }

        return true;
    }

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
        private Double predictedPriceEurMwh;
        private Double spikeProbability;

        @Override
        public boolean isValid() {
            if (predictedPriceEurMwh == null) {
                setValidationError("PredictedPriceEurMwh is null; interval skipped.");
                return false;
            }
            if (predictedPriceEurMwh < -500 || predictedPriceEurMwh > 4000) {
                setValidationError(String.format("PredictedPriceEurMwh %s is outside the market range [-500, 4000] EUR/MWh; interval skipped.", predictedPriceEurMwh));
                return false;
            }

            if (spikeProbability == null) {
                setValidationError("SpikeProbability is null; interval skipped.");
                return false;
            }
            if (spikeProbability < 0.0 || spikeProbability > 1.0) {
                setValidationError(String.format("SpikeProbability %s is outside the valid range [0.0, 1.0]; interval skipped.", spikeProbability));
                return false;
            }

            return true;
        }

        public Double getPredictedPriceEurMwh() {
            return predictedPriceEurMwh;
        }

        public void setPredictedPriceEurMwh(Double predictedPriceEurMwh) {
            this.predictedPriceEurMwh = predictedPriceEurMwh;
        }

        public Double getSpikeProbability() {
            return spikeProbability;
        }

        public void setSpikeProbability(Double spikeProbability) {
            this.spikeProbability = spikeProbability;
        }
    }
}
