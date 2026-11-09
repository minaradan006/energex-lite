package documents;

import java.util.List;

public class DamPrice extends BaseDocument {
    private String currencyUnit;
    private String priceMeasureUnit;
    private List<DamPriceInterval> points;

    @Override
    public boolean isValid() {
        for (DamPriceInterval point : points) {
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

    public List<DamPriceInterval> getPoints() {
        return points;
    }

    public void setPoints(List<DamPriceInterval> points) {
        this.points = points;
    }

    public static class DamPriceInterval extends Interval {
        private Double priceAmount;

        @Override
        public boolean isValid() {
            if (priceAmount == null) {
                setValidationError("PriceAmount is null; interval skipped.");
                return false;
            }

            if (priceAmount < -500 || priceAmount > 4000) {
                setValidationError(String.format("PriceAmount %s is outside the market range [-500, 4000] EUR/MWh; interval skipped.", priceAmount));
                return false;
            }

            return true;
        }

        public Double getPriceAmount() {
            return priceAmount;
        }

        public void setPriceAmount(Double priceAmount) {
            this.priceAmount = priceAmount;
        }
    }
}
