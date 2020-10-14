package share.money.user.api.externalservice.model;

public class WalletCreationRequestModel {
    private Double total;

    public WalletCreationRequestModel(Double total) {
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
