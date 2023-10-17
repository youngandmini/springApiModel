package goorm.apimodel.web.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseStatus {

    public static ResponseStatus createNormalStatus() {
        return new ResponseStatus(2000, "OK");
    }

    private int code;
    private String Message;

}
