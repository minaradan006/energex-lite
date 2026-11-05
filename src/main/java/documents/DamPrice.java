package documents;

import java.util.List;

public class DamPriceDocument extends BaseDocument {
    private String currencyUnit;
    private String priceMeasureUnit;
    private List<DamPriceInterval> points;

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

class DamPriceInterval extends Interval {
    private Double priceAmount;

    public Double getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(Double priceAmount) {
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