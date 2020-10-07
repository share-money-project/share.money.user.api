package share.money.user.api.service;

import share.money.user.api.service.dto.AddressDto;

import java.util.List;

public interface AddressesService {

    List<AddressDto> getUserAddressesById(String userId);

    AddressDto getAddressById(String addressId);
}
