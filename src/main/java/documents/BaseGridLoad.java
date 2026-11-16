package documents;

import java.util.List;

/**
 * Represents base grid load documents containing quantity measure units
 * and a list of grid load interval points.
 */
public class BaseGridLoad extends BaseDocument {
    private String quantityMeasureUnit;
    private List<GridLoadInterval> points;

    /**
     * Validates the grid load document and all its associated interval points.
     */
    @Override
    public final boolean isValid() {
        if (points == null) {
            setValidationError("Points list is null.");
            return false;
        }

        for (GridLoadInterval point : points) {
            if (!point.isValid()) {
                setValidationError(point.getValidationError());
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the quantity measure unit.
     */
    public final String getQuantityMeasureUnit() {
        return quantityMeasureUnit;
    }

    /**
     * Sets the quantity measure unit.
     */
    public final void setQuantityMeasureUnit(final String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }

    /**
     * Gets the list of grid load intervals.
     */
    public final List<GridLoadInterval> getPoints() {
        return points;
    }

    /**
     * Sets the list of grid load intervals.
     */
    public final void setPoints(final List<GridLoadInterval> points) {
        this.points = points;
    }

    /**
     * Represents an individual interval point within a grid load document.
     */
    public static final class GridLoadInterval extends Interval {
        private static final double MIN_QUANTITY = 0.0;
        private Double quantity;
        private Double reservesRequiredMW;

        /**
         * Validates the quantity and required reserves against allowed operational limits.
         */
        @Override
        public boolean isValid() {
            if (quantity == null) {
                setValidationError("Quantity is null; interval skipped.");
                return false;
            }

            if (quantity < MIN_QUANTITY) {
                setValidationError(String.format(
                        "Quantity %s is below the minimum allowed value 0; interval skipped.",
                        quantity));
                return false;
            }

            if (reservesRequiredMW == null) {
                setValidationError("ReservesRequiredMW is null; interval skipped.");
                return false;
            }

            if (reservesRequiredMW <= 0) {
                setValidationError(String.format(
                        "ReservesRequiredMW %s is not positive; interval skipped.",
                        reservesRequiredMW));
                return false;
            }

            return true;
        }

        /**
         * Gets the quantity value.
         */
        public Double getQuantity() {
            return quantity;
        }

        /**
         * Sets the quantity value.
         */
        public void setQuantity(final Double quantity) {
            this.quantity = quantity;
        }

        /**
         * Gets the required reserves in MW.
         */
        @com.fasterxml.jackson.annotation.JsonIgnore
        public Double getReservesRequiredMW() {
            return reservesRequiredMW;
        }

        /**
         * Sets the required reserves in MW.
         */
        public void setReservesRequiredMW(final Double reservesRequiredMW) {
            this.reservesRequiredMW = reservesRequiredMW;
        }
    }
}
