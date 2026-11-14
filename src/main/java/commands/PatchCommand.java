package commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import documents.DamPrice.DamPriceInterval;
import documents.BaseGridLoad.GridLoadInterval;
import documents.ImbalancePrice.ImbalancePriceInterval;
import documents.ImbalanceVolume.ImbalanceVolumeInterval;
import repository.MarketDataRepository;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatchCommand extends Command {
    private PatchParameters parameters;

    @Override
    public void execute(MarketDataRepository repository) {
        if (parameters == null || parameters.getData() == null) {
            return;
        }

        String dataType = parameters.getDataType();

        for (Object obj : parameters.getData()) {
            Map<String, Object> rawPoint = (Map<String, Object>) obj;
            if (dataType == null) continue;

            switch (dataType) {
                case "DAM_PRICE": {
                    DamPriceInterval interval = new DamPriceInterval();
                    if (rawPoint.get("intervalIndex") != null) {
                        interval.setIntervalIndex(((Number) rawPoint.get("intervalIndex")).intValue());
                    }
                    if (rawPoint.get("priceAmount") != null) {
                        interval.setPriceAmount(((Number) rawPoint.get("priceAmount")).doubleValue());
                    }

                    if (interval.isValid()) {
                        repository.patchPoint(dataType, interval);
                    } else {
                        System.out.println("[WARNING] " + interval.getValidationError());
                    }
                    break;
                }
                case "GRID_LOAD": {
                    GridLoadInterval interval = new GridLoadInterval();
                    if (rawPoint.get("intervalIndex") != null) {
                        interval.setIntervalIndex(((Number) rawPoint.get("intervalIndex")).intValue());
                    }
                    if (rawPoint.get("quantity") != null) {
                        interval.setQuantity(((Number) rawPoint.get("quantity")).doubleValue());
                    }
                    if (rawPoint.get("reservesRequiredMW") != null) {
                        interval.setReservesRequiredMW(((Number) rawPoint.get("reservesRequiredMW")).doubleValue());
                    }

                    if (interval.isValid()) {
                        repository.patchPoint(dataType, interval);
                    } else {
                        System.out.println("[WARNING] " + interval.getValidationError());
                    }
                    break;
                }
                case "IMBALANCE_PRICE": {
                    ImbalancePriceInterval interval = new ImbalancePriceInterval();
                    if (rawPoint.get("intervalIndex") != null) {
                        interval.setIntervalIndex(((Number) rawPoint.get("intervalIndex")).intValue());
                    }
                    if (rawPoint.get("positiveImbalancePrice") != null) {
                        interval.setPositiveImbalancePrice(((Number) rawPoint.get("positiveImbalancePrice")).doubleValue());
                    }
                    if (rawPoint.get("negativeImbalancePrice") != null) {
                        interval.setNegativeImbalancePrice(((Number) rawPoint.get("negativeImbalancePrice")).doubleValue());
                    }

                    if (interval.isValid()) {
                        repository.patchPoint(dataType, interval);
                    } else {
                        System.out.println("[WARNING] " + interval.getValidationError());
                    }
                    break;
                }
                case "IMBALANCE_VOLUME": {
                    ImbalanceVolumeInterval interval = new ImbalanceVolumeInterval();
                    if (rawPoint.get("intervalIndex") != null) {
                        interval.setIntervalIndex(((Number) rawPoint.get("intervalIndex")).intValue());
                    }
                    if (rawPoint.get("positiveImbalanceVol") != null) {
                        interval.setPositiveImbalanceVol(((Number) rawPoint.get("positiveImbalanceVol")).doubleValue());
                    }
                    if (rawPoint.get("negativeImbalanceVol") != null) {
                        interval.setNegativeImbalanceVol(((Number) rawPoint.get("negativeImbalanceVol")).doubleValue());
                    }

                    if (interval.isValid()) {
                        repository.patchPoint(dataType, interval);
                    } else {
                        System.out.println("[WARNING] " + interval.getValidationError());
                    }
                    break;
                }
                default:
                    System.out.println("[WARNING] Unknown dataType for patch: " + dataType);
            }
        }
    }

    public PatchParameters getParameters() {
        return parameters;
    }

    public void setParameters(PatchParameters parameters) {
        this.parameters = parameters;
    }
}