package documents;

import java.util.List;

public class ImbalanceVolume extends BaseDocument {
    private String quantityMeasureUnit;
    private List<ImbalanceVolumeInterval> points;

    @Override
    public boolean isValid() {
        for (ImbalanceVolumeInterval point : points) {
            if (!point.isValid()) {
                setValidationError(point.getValidationError());
                return false;
            }
        }

        return true;
    }

    public String getQuantityMeasureUnit() {
        return quantityMeasureUnit;
    }

    public void setQuantityMeasureUnit(String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }

    public List<ImbalanceVolumeInterval> getPoints() {
        return points;
    }

    public void setPoints(List<ImbalanceVolumeInterval> points) {
        this.points = points;
    }

    public static class ImbalanceVolumeInterval extends Interval {
        private Double positiveImbalanceVol;
        private Double negativeImbalanceVol;

        @Override
        public boolean isValid() {
            if (positiveImbalanceVol == null) {
                setValidationError("PositiveImbalanceVol is null; interval skipped.");
                return false;
            }

            if (positiveImbalanceVol < 0) {
                setValidationError(String.format("PositiveImbalanceVol %s is below the minimum allowed value 0; interval skipped.", positiveImbalanceVol));
                return false;
            }

            if (negativeImbalanceVol == null) {
                setValidationError("NegativeImbalanceVol is null; interval skipped.");
                return false;
            }

            if (negativeImbalanceVol < 0) {
                setValidationError(String.format("NegativeImbalanceVol %s is below the minimum allowed value 0; interval skipped.", negativeImbalanceVol));
                return false;
            }

            return true;
        }

        public Double getPositiveImbalanceVol() {
            return positiveImbalanceVol;
        }

        public void setPositiveImbalanceVol(Double positiveImbalanceVol) {
            this.positiveImbalanceVol = positiveImbalanceVol;
        }

        public Double getNegativeImbalanceVol() {
            return negativeImbalanceVol;
        }

        public void setNegativeImbalanceVol(Double negativeImbalanceVol) {
            this.negativeImbalanceVol = negativeImbalanceVol;
        }
    }
}
