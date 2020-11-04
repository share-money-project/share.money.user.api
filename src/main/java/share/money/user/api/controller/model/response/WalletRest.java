package share.money.user.api.controller.model.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletRest {

    private String userId;
    private Double total;
}
