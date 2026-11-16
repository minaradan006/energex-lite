package output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutputResponse {
    private String command;
    private String timestamp;
    private String status;
    private String id;
    private String date;
    private String interval;
    private Integer intervalIndex;
    private String message;

    private String from;
    private String to;
    private Object data;
}
