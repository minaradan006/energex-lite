package commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import documents.BaseDocument;
import repository.MarketDataRepository;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImportCommand extends Command {
    private ImportParameters parameters;

    @Override
    public void execute(MarketDataRepository repository) {
        if (parameters != null && parameters.getData() != null) {
            for (BaseDocument doc : parameters.getData()) {
                if (doc.isValid()) {
                    repository.insert(doc);
                } else {
                    System.out.println(doc.getValidationError());
                }
            }
        }
    }

    public ImportParameters getParameters() {
        return parameters;
    }

    public void setParameters(ImportParameters parameters) {
        this.parameters = parameters;
    }
}
