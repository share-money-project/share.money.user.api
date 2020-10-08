package share.money.user.api.controller.model.response;

public class OperationStatusModel {

    private String status;
    private String operationName;

    public OperationStatusModel() {
    }

    public OperationStatusModel(String status, String operation) {
        this.status = status;
        this.operationName = operation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}