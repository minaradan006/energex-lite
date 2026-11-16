package documents;

import java.util.List;

/**
 * Represents Day-Ahead Market Price Prediction documents containing currency,
 * price measure units, and a list of prediction intervals.
 */
public class DamPricePrediction extends BaseDocument {
    private String currencyUnit;
    private String priceMeasureUnit;
    private List<DamPredictionInterval> points;

    /**
     * Validates the DAM price prediction document and all its associated interval points.
     */
    @Override
    public final boolean isValid() {
        if (points == null) {
            setValidationError("Points list is null.");
            return false;
        }

        for (DamPredictionInterval point : points) {
            if (!point.isValid()) {
                setValidationError(point.getValidationError());
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the currency unit.
     */
    public final String getCurrencyUnit() {
        return currencyUnit;
    }

    /**
     * Sets the currency unit.
     */
    public final void setCurrencyUnit(final String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    /**
     * Gets the price measure unit.
     */
    public final String getPriceMeasureUnit() {
        return priceMeasureUnit;
    }

    /**
     * Sets the price measure unit.
     */
    public final void setPriceMeasureUnit(final String priceMeasureUnit) {
        this.priceMeasureUnit = priceMeasureUnit;
    }

    /**
     * Gets the list of DAM prediction intervals.
     */
    public final List<DamPredictionInterval> getPoints() {
        return points;
    }

    /**
     * Sets the list of DAM prediction intervals.
     */
    public final void setPoints(final List<DamPredictionInterval> points) {
        this.points = points;
    }

    /**
     * Represents an individual prediction interval point within a DAM price prediction document.
     */
    public static final class DamPredictionInterval extends Interval {
        private static final double MIN_PRICE = -500.0;
        private static final double MAX_PRICE = 4000.0;
        private static final double MIN_PROBABILITY = 0.0;
        private static final double MAX_PROBABILITY = 1.0;

        private Double predictedPriceEurMwh;
        private Double spikeProbability;

        /**
         * Validates the predicted price and spike probability against allowed market ranges.
         */
        @Override
        public boolean isValid() {
            if (predictedPriceEurMwh == null) {
                setValidationError("PredictedPriceEurMwh is null; interval skipped.");
                return false;
            }
            if (predictedPriceEurMwh < MIN_PRICE || predictedPriceEurMwh > MAX_PRICE) {
                setValidationError(String.format(
                        "PredictedPriceEurMwh %s is outside the market range [-500, 4000] "
                                + "EUR/MWh; interval skipped.", predictedPriceEurMwh));
                return false;
            }

            if (spikeProbability == null) {
                setValidationError("SpikeProbability is null; interval skipped.");
                return false;
            }
            if (spikeProbability < MIN_PROBABILITY || spikeProbability > MAX_PROBABILITY) {
                setValidationError(String.format(
                        "SpikeProbability %s is outside the valid range [0.0, 1.0]; "
                                + "interval skipped.", spikeProbability));
                return false;
            }

            return true;
        }

        /**
         * Gets the predicted price in EUR/MWh.
         */
        public Double getPredictedPriceEurMwh() {
            return predictedPriceEurMwh;
        }

        /**
         * Sets the predicted price in EUR/MWh.
         */
        public void setPredictedPriceEurMwh(final Double predictedPriceEurMwh) {
            this.predictedPriceEurMwh = predictedPriceEurMwh;
        }

        /**
         * Gets the spike probability.
         */
        public Double getSpikeProbability() {
            return spikeProbability;
        }

        /**
         * Sets the spike probability.
         */
        public void setSpikeProbability(final Double spikeProbability) {
            this.spikeProbability = spikeProbability;
        }
    }
}
