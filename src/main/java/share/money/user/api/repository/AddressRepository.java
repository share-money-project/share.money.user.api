package share.money.user.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import share.money.user.api.repository.entity.AddressEntity;

import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

    AddressEntity findByAddressId(String addressId);

    default Optional<AddressEntity> findByAddressIdAsOptional(String addressId){
        return Optional.ofNullable(findByAddressId(addressId));
    }
}

