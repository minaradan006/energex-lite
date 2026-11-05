package documents;

import java.util.List;

public class ImbalancePrice extends BaseDocument {
    private String currencyUnit;
    private List<ImbalancePriceInterval> points;

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
}

class ImbalancePriceInterval extends Interval {
    private Double positiveImbalancePrice;
    private Double negativeImbalancePrice;

    public Double getPositiveImbalancePrice() {
        return positiveImbalancePrice;
    }

    public void setPositiveImbalancePrice(Double positiveImbalancePrice) {
        if (positiveImbalancePrice == null) {
            System.out.println("PositiveImbalancePrice is null; interval skipped.");
            return;
        }

        if (positiveImbalancePrice > 15000 || positiveImbalancePrice < -15000) {
            System.out.println("PositiveImbalancePrice " + positiveImbalancePrice +" exceeds the maximum allowed value 15000 EUR/MWh; interval skipped.");
        }

        this.positiveImbalancePrice = positiveImbalancePrice;
    }

    public Double getNegativeImbalancePrice() {
        return negativeImbalancePrice;
    }

    public void setNegativeImbalancePrice(Double negativeImbalancePrice) {
        if (negativeImbalancePrice == null) {
            System.out.println("NegativeImbalancePrice is null; interval skipped.");
            return;
        }

        if (negativeImbalancePrice > 15000 || negativeImbalancePrice < -15000) {
            System.out.println("NegativeImbalancePrice " + negativeImbalancePrice + " exceeds the maximum allowed value 15000 EUR/MWh; interval skipped.");
        }

        this.negativeImbalancePrice = negativeImbalancePrice;
    }
}
