package share.money.commons.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import share.money.commons.dto.OfferDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserValidationResponseEvent {

    private OfferDto offerDto;
    private Boolean validationErrorExist;
    private String validationErrorReason;
}
