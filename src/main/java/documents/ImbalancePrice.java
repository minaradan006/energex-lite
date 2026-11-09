package documents;

import java.util.List;

public class ImbalancePrice extends BaseDocument {
    private String currencyUnit;
    private List<ImbalancePriceInterval> points;

    @Override
    public boolean isValid() {
        for (ImbalancePriceInterval point : points) {
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

    public List<ImbalancePriceInterval> getPoints() {
        return points;
    }

    public void setPoints(List<ImbalancePriceInterval> points) {
        this.points = points;
    }

    public static class ImbalancePriceInterval extends Interval {
        private Double positiveImbalancePrice;
        private Double negativeImbalancePrice;

        @Override
        public boolean isValid() {
            if (positiveImbalancePrice == null) {
                setValidationError("PositiveImbalancePrice is null; interval skipped.");
                return false;
            }

            if (Math.abs(positiveImbalancePrice) > 15000) {
                setValidationError(String.format("PositiveImbalancePrice %s exceeds the maximum allowed value 15000 EUR/MWh; interval skipped.", positiveImbalancePrice));
                return false;
            }

            if (negativeImbalancePrice == null) {
                setValidationError("NegativeImbalancePrice is null; interval skipped.");
                return false;
            }

            if (Math.abs(negativeImbalancePrice) > 15000) {
                setValidationError(String.format("NegativeImbalancePrice %s exceeds the maximum allowed value 15000 EUR/MWh; interval skipped.", negativeImbalancePrice));
                return false;
            }

            return true;
        }

        public Double getPositiveImbalancePrice() {
            return positiveImbalancePrice;
        }

        public void setPositiveImbalancePrice(Double positiveImbalancePrice) {
            this.positiveImbalancePrice = positiveImbalancePrice;
        }

        public Double getNegativeImbalancePrice() {
            return negativeImbalancePrice;
        }

        public void setNegativeImbalancePrice(Double negativeImbalancePrice) {
            this.negativeImbalancePrice = negativeImbalancePrice;
        }
    }
}
