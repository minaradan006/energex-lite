package documents;

import java.util.List;

public class BaseGridLoad extends BaseDocument {
    private String currencyUnit;
    private String priceMeasureUnit;
    private List<GridLoadInterval> points;

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

        public Double getQuantity() {
            return quantity;
        }

        public void setQuantity(Double quantity) {

            if (quantity == null) {
                System.out.println("Quantity is null; interval skipped.");
                return;
            }

            if (quantity < 0) {
                System.out.println("Quantity "  + quantity + " is below the minimum allowed value 0; interval skipped.");
                return;
            }

            this.quantity = quantity;
        }

        public Double getReservesRequiredMW() {
            return reservesRequiredMW;
        }

        public void setReservesRequiredMW(Double reservesRequiredMW) {
            if (reservesRequiredMW == null) {
                System.out.println("ReservesRequiredMW is null; interval skipped.");
                return;
            }

            if (reservesRequiredMW <= 0) {
                System.out.println("ReservesRequiredMW {value} is not positive; interval skipped.");
                return;
            }

            this.reservesRequiredMW = reservesRequiredMW;
        }
    }
}
