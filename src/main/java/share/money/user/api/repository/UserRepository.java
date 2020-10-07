package share.money.user.api.repository;

import org.springframework.data.repository.CrudRepository;
import share.money.user.api.repository.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    default Optional<UserEntity> findByUserIdAsOptional(String userId) {
        return Optional.ofNullable(findByUserId(userId));
    }

    default Optional<UserEntity> findByEmailAsOptional(String email){
        return Optional.ofNullable(findByEmail(email));
    }

    UserEntity findByUserId(String userId);

    UserEntity findByEmail(String email);
}
