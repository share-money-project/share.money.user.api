package share.money.user.api.controller.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestModel {

    private String email;
    private String password;

}
