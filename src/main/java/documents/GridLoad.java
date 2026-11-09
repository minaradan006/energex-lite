package documents;

public class GridLoad extends BaseGridLoad {
    @Override
    public boolean isValid() {
        if (!super.isValid()) {
            return false;
        }

        Double previousQuantity = null;
        for (GridLoadInterval point : getPoints()) {
            if (previousQuantity != null) {
                double diff = Math.abs(point.getQuantity() - previousQuantity);
                if (diff > 10000.0) {
                    setValidationError("Quantity change exceeds 10000 MW compared with the previous interval; interval skipped.");
                    return false;
                }
            }
            previousQuantity = point.getQuantity();
        }

        return true;
    }
}