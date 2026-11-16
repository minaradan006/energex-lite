package documents;

import java.util.List;

/**
 * Represents Day-Ahead Market (DAM) price documents containing currency,
 * measure units, and a list of price intervals.
 */
public class DamPrice extends BaseDocument {
    private String currencyUnit;
    private String priceMeasureUnit;
    private List<DamPriceInterval> points;

    /**
     * Validates the DAM price document and all its associated interval points.
     */
    @Override
    public final boolean isValid() {
        if (points == null) {
            setValidationError("Points list is null.");
            return false;
        }

        for (DamPriceInterval point : points) {
            if (!point.isValid()) {
                setValidationError(point.getValidationError());
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the currency unit for the prices.
     */
    public final String getCurrencyUnit() {
        return currencyUnit;
    }

    /**
     * Sets the currency unit for the prices.
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
     * Gets the list of DAM price intervals.
     */
    public final List<DamPriceInterval> getPoints() {
        return points;
    }

    /**
     * Sets the list of DAM price intervals.
     */
    public final void setPoints(final List<DamPriceInterval> points) {
        this.points = points;
    }

    /**
     * Represents an individual interval point within a DAM price document.
     */
    public static final class DamPriceInterval extends Interval {
        private static final double MIN_PRICE = -500.0;
        private static final double MAX_PRICE = 4000.0;
        private Double priceAmount;

        /**
         * Validates the price amount interval against allowed market ranges.
         */
        @Override
        public boolean isValid() {
            if (priceAmount == null) {
                setValidationError("PriceAmount is null; interval skipped.");
                return false;
            }

            if (priceAmount < MIN_PRICE || priceAmount > MAX_PRICE) {
                setValidationError(String.format(
                        "PriceAmount %s is outside the market range [-500, 4000] EUR/MWh; "
                                + "interval skipped.", priceAmount));
                return false;
            }

            return true;
        }

        /**
         * Gets the price amount for this interval.
         */
        public Double getPriceAmount() {
            return priceAmount;
        }

        /**
         * Sets the price amount for this interval.
         */
        public void setPriceAmount(final Double priceAmount) {
            this.priceAmount = priceAmount;
        }
    }
}
