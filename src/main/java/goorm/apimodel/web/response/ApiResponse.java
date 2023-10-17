package goorm.apimodel.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiResponse<T> {

    private ResponseStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseMetadata metadata;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> results;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseErrorData data;

    public ApiResponse(T result) {
        this.status = ResponseStatus.createNormalStatus();
        this.metadata = new ResponseMetadata(1);

        List<T> results = new ArrayList<>();
        results.add(result);
        this.results = results;
        this.data = null;
    }

    public ApiResponse(List<T> results) {
        this.status = ResponseStatus.createNormalStatus();
        this.metadata = new ResponseMetadata(results.size());
        this.results = results;
        this.data = null;
    }

    public ApiResponse(int code, String message, String moreInfoMessage) {

        this.status = new ResponseStatus(code, message);
        this.metadata = null;
        this.results = null;

        if (moreInfoMessage.isEmpty()) {
            this.data = null;
        } else {
            this.data = new ResponseErrorData(moreInfoMessage);
        }
    }
}
