package documents;

import java.util.List;

public class BaseGridLoad extends BaseDocument {
    private String currencyUnit;
    private String priceMeasureUnit;
    private List<GridLoadInterval> points;

    @Override
    public boolean isValid() {
        for (GridLoadInterval point : points) {
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

    public List<GridLoadInterval> getPoints() {
        return points;
    }

    public void setPoints(List<GridLoadInterval> points) {
        this.points = points;
    }

    public static class GridLoadInterval extends Interval{
        Double quantity;
        Double reservesRequiredMW;

        @Override
        public boolean isValid() {
            if (quantity == null) {
                setValidationError("Quantity is null; interval skipped.");
                return false;
            }

            if (quantity < 0) {
                setValidationError(String.format("Quantity %s is below the minimum allowed value 0; interval skipped.", quantity));
                return false;
            }

            if (reservesRequiredMW == null) {
                setValidationError("ReservesRequiredMW is null; interval skipped.");
                return false;
            }

            if (reservesRequiredMW <= 0) {
                setValidationError(String.format("ReservesRequiredMW %s is not positive; interval skipped.", reservesRequiredMW));
                return false;
            }

            return true;
        }

        public Double getQuantity() {
            return quantity;
        }

        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }

        public Double getReservesRequiredMW() {
            return reservesRequiredMW;
        }

        public void setReservesRequiredMW(Double reservesRequiredMW) {
            this.reservesRequiredMW = reservesRequiredMW;
        }
    }
}
