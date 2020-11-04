package share.money.user.api.controller.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRest {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressRest> addresses;
    private WalletRest wallet;
}
