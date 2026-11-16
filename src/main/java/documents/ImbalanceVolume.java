package documents;

import java.util.List;

/**
 * Represents Imbalance Volume documents containing quantity measure units
 * and a list of volume intervals.
 */
public class ImbalanceVolume extends BaseDocument {
    private String quantityMeasureUnit;
    private List<ImbalanceVolumeInterval> points;

    /**
     * Validates the imbalance volume document and all its associated interval points.
     */
    @Override
    public final boolean isValid() {
        if (points == null) {
            setValidationError("Points list is null.");
            return false;
        }

        for (ImbalanceVolumeInterval point : points) {
            if (!point.isValid()) {
                setValidationError(point.getValidationError());
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the quantity measure unit for the volumes.
     */
    public final String getQuantityMeasureUnit() {
        return quantityMeasureUnit;
    }

    /**
     * Sets the quantity measure unit for the volumes.
     */
    public final void setQuantityMeasureUnit(final String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }

    /**
     * Gets the list of imbalance volume intervals.
     */
    public final List<ImbalanceVolumeInterval> getPoints() {
        return points;
    }

    /**
     * Sets the list of imbalance volume intervals.
     */
    public final void setPoints(final List<ImbalanceVolumeInterval> points) {
        this.points = points;
    }

    /**
     * Represents an individual interval point within an imbalance volume document.
     */
    public static final class ImbalanceVolumeInterval extends Interval {
        private static final double MIN_VOLUME = 0.0;
        private Double positiveImbalanceVol;
        private Double negativeImbalanceVol;

        /**
         * Validates the positive and negative imbalance volumes against allowed ranges.
         */
        @Override
        public boolean isValid() {
            if (positiveImbalanceVol == null) {
                setValidationError("PositiveImbalanceVol is null; interval skipped.");
                return false;
            }

            if (positiveImbalanceVol < MIN_VOLUME) {
                setValidationError(String.format(
                        "PositiveImbalanceVol %s is below the minimum allowed value 0; "
                                + "interval skipped.", positiveImbalanceVol));
                return false;
            }

            if (negativeImbalanceVol == null) {
                setValidationError("NegativeImbalanceVol is null; interval skipped.");
                return false;
            }

            if (negativeImbalanceVol < MIN_VOLUME) {
                setValidationError(String.format(
                        "NegativeImbalanceVol %s is below the minimum allowed value 0; "
                                + "interval skipped.", negativeImbalanceVol));
                return false;
            }

            return true;
        }

        /**
         * Gets the positive imbalance volume.
         */
        public Double getPositiveImbalanceVol() {
            return positiveImbalanceVol;
        }

        /**
         * Sets the positive imbalance volume.
         */
        public void setPositiveImbalanceVol(final Double positiveImbalanceVol) {
            this.positiveImbalanceVol = positiveImbalanceVol;
        }

        /**
         * Gets the negative imbalance volume.
         */
        public Double getNegativeImbalanceVol() {
            return negativeImbalanceVol;
        }

        /**
         * Sets the negative imbalance volume.
         */
        public void setNegativeImbalanceVol(final Double negativeImbalanceVol) {
            this.negativeImbalanceVol = negativeImbalanceVol;
        }
    }
}
