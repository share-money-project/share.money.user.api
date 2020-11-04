package share.money.commons.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class OfferDto {

    private Long id;
    private String offerId;
    private String userId;
    private Double amount;
    private OfferAllocationState offerAllocationState;
    private Timestamp createDate;
    private Timestamp lastModifiedDate;
}
