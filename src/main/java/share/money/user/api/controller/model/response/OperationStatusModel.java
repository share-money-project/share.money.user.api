package share.money.user.api.controller.model.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OperationStatusModel {

    private String status;
    private String operationName;

    public OperationStatusModel(String status, String operationName) {
        this.status = status;
        this.operationName = operationName;
    }
}