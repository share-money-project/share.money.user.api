package share.money.user.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import share.money.user.api.repository.AddressRepository;
import share.money.user.api.repository.UserRepository;
import share.money.user.api.repository.entity.AddressEntity;
import share.money.user.api.repository.entity.UserEntity;
import share.money.user.api.service.AddressesService;
import share.money.user.api.service.dto.AddressDto;
import share.money.user.api.shared.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressesService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDto> getUserAddressesById(String userId) {

        UserEntity userEntity = userRepository.findByUserIdAsOptional(userId).orElseThrow(() -> new RuntimeException(String.format("No record found for user id [%s]", userId)));
        List<AddressEntity> addresses = userEntity.getAddresses();
        return addresses.stream().map(ad -> ModelMapper.map(ad, AddressDto.class)).collect(Collectors.toList());
    }

    @Override
    public AddressDto getAddressById(String addressId) {
        AddressEntity addressEntity = addressRepository.findByAddressIdAsOptional(addressId).orElseThrow(() -> new RuntimeException(String.format("No record found for address id [%s]", addressId)));
        return ModelMapper.map(addressEntity, AddressDto.class);
    }
}
