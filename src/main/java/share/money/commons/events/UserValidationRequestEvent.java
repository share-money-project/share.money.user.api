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
public class UserValidationRequestEvent {

    private OfferDto offerDto;
}