package documents;

import java.util.List;

public class DamPriceDocument extends BaseDocument {
    private String currencyUnit;
    private String priceMeasureUnit;
    private List<Interval> points;

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
}

class Interval {
    private int IntervalIndex;
    private double priceAmount;

    public int getIntervalIndex() {
        return IntervalIndex;
    }

    public void setIntervalIndex(int intervalIndex) {
        IntervalIndex = intervalIndex;
    }

    public double getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(double priceAmount) {
        if (priceAmount == null) {
            System.out.println("PriceAmount is null; interval skipped.");
            return;
        }

        if (priceAmount < -500 || priceAmount > 4000) {
            System.out.println("PriceAmount " + priceAmount + " is outside the market range [-500, 4000] EUR/MWh; interval skipped.");
            return;
        }

        this.priceAmount = priceAmount;
    }
}