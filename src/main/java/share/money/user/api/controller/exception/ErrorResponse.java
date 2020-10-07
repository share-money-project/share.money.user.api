package share.money.user.api.controller.exception;

public class ErrorResponse {

    private Integer statusCode;
    private String description;

    public ErrorResponse(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.description = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}
