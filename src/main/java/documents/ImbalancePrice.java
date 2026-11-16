package documents;

import java.util.List;

/**
 * Represents Imbalance Price documents containing currency units
 * and a list of imbalance price interval points.
 */
public class ImbalancePrice extends BaseDocument {
    private String currencyUnit;
    private List<ImbalancePriceInterval> points;

    /**
     * Validates the imbalance price document and all its associated interval points.
     */
    @Override
    public final boolean isValid() {
        if (points == null) {
            setValidationError("Points list is null.");
            return false;
        }

        for (ImbalancePriceInterval point : points) {
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
     * Gets the list of imbalance price intervals.
     */
    public final List<ImbalancePriceInterval> getPoints() {
        return points;
    }

    /**
     * Sets the list of imbalance price intervals.
     */
    public final void setPoints(final List<ImbalancePriceInterval> points) {
        this.points = points;
    }

    /**
     * Represents an individual interval point within an imbalance price document.
     */
    public static final class ImbalancePriceInterval extends Interval {
        private static final double MAX_ABS_PRICE = 15000.0;
        private Double positiveImbalancePrice;
        private Double negativeImbalancePrice;

        /**
         * Validates the positive and negative imbalance prices against absolute limits.
         */
        @Override
        public boolean isValid() {
            if (positiveImbalancePrice == null) {
                setValidationError("PositiveImbalancePrice is null; interval skipped.");
                return false;
            }

            if (Math.abs(positiveImbalancePrice) > MAX_ABS_PRICE) {
                setValidationError(String.format(
                        "PositiveImbalancePrice %s exceeds the maximum allowed value 15000 "
                                + "EUR/MWh; interval skipped.", positiveImbalancePrice));
                return false;
            }

            if (negativeImbalancePrice == null) {
                setValidationError("NegativeImbalancePrice is null; interval skipped.");
                return false;
            }

            if (Math.abs(negativeImbalancePrice) > MAX_ABS_PRICE) {
                setValidationError(String.format(
                        "NegativeImbalancePrice %s exceeds the maximum allowed value 15000 "
                                + "EUR/MWh; interval skipped.", negativeImbalancePrice));
                return false;
            }

            return true;
        }

        /**
         * Gets the positive imbalance price.
         */
        public Double getPositiveImbalancePrice() {
            return positiveImbalancePrice;
        }

        /**
         * Sets the positive imbalance price.
         */
        public void setPositiveImbalancePrice(final Double positiveImbalancePrice) {
            this.positiveImbalancePrice = positiveImbalancePrice;
        }

        /**
         * Gets the negative imbalance price.
         */
        public Double getNegativeImbalancePrice() {
            return negativeImbalancePrice;
        }

        /**
         * Sets the negative imbalance price.
         */
        public void setNegativeImbalancePrice(final Double negativeImbalancePrice) {
            this.negativeImbalancePrice = negativeImbalancePrice;
        }
    }
}
