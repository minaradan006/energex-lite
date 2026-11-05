package documents;

import java.util.List;

public class ImbalanceVolume extends BaseDocument {
    private String quantityMeasureUnit;
    private List<ImbalanceVolumeInterval> points;

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
}

class ImbalanceVolumeInterval extends Interval {
    private Double positiveImbalanceVol;
    private Double negativeImbalanceVol;

    public Double getPositiveImbalanceVol() {
        return positiveImbalanceVol;
    }

    public void setPositiveImbalanceVol(Double positiveImbalanceVol) {
        if (positiveImbalanceVol == null) {
            System.out.println("PositiveImbalanceVol is null; interval skipped.");
            return;
        }

        if (positiveImbalanceVol < 0) {
            System.out.println("PositiveImbalanceVol " + positiveImbalanceVol + " is below the minimum allowed value 0; interval skipped.");
            return;
        }

        this.positiveImbalanceVol = positiveImbalanceVol;
    }

    public Double getNegativeImbalanceVol() {
        return negativeImbalanceVol;
    }

    public void setNegativeImbalanceVol(Double negativeImbalanceVol) {
        if (negativeImbalanceVol == null) {
            System.out.println("NegativeImbalanceVol is null; interval skipped.");
            return;
        }

        if (negativeImbalanceVol < 0) {
            System.out.println("NegativeImbalanceVol " + negativeImbalanceVol + " is below the minimum allowed value 0; interval skipped.");
        }

        this.negativeImbalanceVol = negativeImbalanceVol;
    }
}
