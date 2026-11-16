package core;

import documents.BaseGridLoad;
import documents.DamPrice;
import documents.DamPricePrediction;
import documents.ImbalancePrice;
import documents.ImbalanceVolume;
import documents.Interval;
import fileio.MarketDataInput;

/**
 * The IntervalParser utility class handles parsing raw interval strings and market data inputs
 * into specific structured domain interval objects.
 */
public final class IntervalParser {
    private static final int MINUTES_PER_HOUR = 60;
    private static final int INTERVAL_DURATION_MINUTES = 15;
    private static final int HOURS_PER_DAY = 24;

    private IntervalParser() {
    }

    /**
     * Parses a raw market data input into a specific interval domain object based on the data type.
     */
    public static Interval parseIntervalToSpecificType(final String dataType,
                                                       final MarketDataInput pointInput) {
        if (dataType == null) {
            return null;
        }

        int index = extractIntervalIndex(pointInput.interval());

        String formattedInterval = pointInput.interval();
        if (formattedInterval != null
                && formattedInterval.contains("-")
                && !formattedInterval.contains(" - ")) {
            formattedInterval = formattedInterval.replace("-", " - ");
        }

        switch (dataType.toUpperCase()) {
            case "DAM_PRICE":
                DamPrice.DamPriceInterval damInterval = new DamPrice.DamPriceInterval();
                damInterval.setIntervalIndex(index);
                damInterval.setInterval(formattedInterval);
                if (pointInput.priceAmount() != null) {
                    damInterval.setPriceAmount(pointInput.priceAmount());
                }
                return damInterval;

            case "DAM_PRICE_PREDICTION":
                DamPricePrediction.DamPredictionInterval predInterval =
                        new DamPricePrediction.DamPredictionInterval();
                predInterval.setIntervalIndex(index);
                predInterval.setInterval(formattedInterval);
                if (pointInput.predictedPriceEurMwh() != null) {
                    predInterval.setPredictedPriceEurMwh(pointInput.predictedPriceEurMwh());
                }
                if (pointInput.spikeProbability() != null) {
                    predInterval.setSpikeProbability(pointInput.spikeProbability());
                }
                return predInterval;

            case "GRID_LOAD":
            case "GRID_LOAD_PREDICTION":
                BaseGridLoad.GridLoadInterval loadInterval = new BaseGridLoad.GridLoadInterval();
                loadInterval.setIntervalIndex(index);
                loadInterval.setInterval(formattedInterval);
                if (pointInput.quantity() != null) {
                    loadInterval.setQuantity(pointInput.quantity().doubleValue());
                }
                if (pointInput.reservesRequiredMW() != null) {
                    loadInterval.setReservesRequiredMW(pointInput.reservesRequiredMW());
                }
                return loadInterval;

            case "IMBALANCE_PRICE":
                ImbalancePrice.ImbalancePriceInterval imbPriceInterval =
                        new ImbalancePrice.ImbalancePriceInterval();
                imbPriceInterval.setIntervalIndex(index);
                imbPriceInterval.setInterval(formattedInterval);
                if (pointInput.positiveImbalancePrice() != null) {
                    imbPriceInterval.setPositiveImbalancePrice(
                            pointInput.positiveImbalancePrice());
                }
                if (pointInput.negativeImbalancePrice() != null) {
                    imbPriceInterval.setNegativeImbalancePrice(
                            pointInput.negativeImbalancePrice());
                }
                return imbPriceInterval;

            case "IMBALANCE_VOLUME":
                ImbalanceVolume.ImbalanceVolumeInterval imbVolInterval =
                        new ImbalanceVolume.ImbalanceVolumeInterval();
                imbVolInterval.setIntervalIndex(index);
                imbVolInterval.setInterval(formattedInterval);
                if (pointInput.positiveImbalanceVol() != null) {
                    imbVolInterval.setPositiveImbalanceVol(
                            pointInput.positiveImbalanceVol());
                }
                if (pointInput.negativeImbalanceVol() != null) {
                    imbVolInterval.setNegativeImbalanceVol(
                            pointInput.negativeImbalanceVol());
                }
                return imbVolInterval;

            default:
                return null;
        }
    }

    /**
     * Extracts the 15-minute interval index from an interval string.
     */
    public static int extractIntervalIndex(final String intervalStr) {
        if (intervalStr == null || !intervalStr.contains("-")) {
            return 1;
        }

        try {
            String startTime = intervalStr.split("-")[0].trim();
            String[] parts = startTime.split(":");

            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);

            int totalMinutes = hours * MINUTES_PER_HOUR + minutes;
            return (totalMinutes / INTERVAL_DURATION_MINUTES) + 1;
        } catch (Exception e) {
            return 1;
        }
    }
}
