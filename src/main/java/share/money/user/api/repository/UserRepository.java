package share.money.user.api.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import share.money.user.api.repository.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    default Optional<UserEntity> findByUserIdAsOptional(String userId) {
        return Optional.ofNullable(findByUserId(userId));
    }

    default Optional<UserEntity> findByEmailAsOptional(String email){
        return Optional.ofNullable(findByEmail(email));
    }

    UserEntity findByUserId(String userId);

    UserEntity findByEmail(String email);
}
