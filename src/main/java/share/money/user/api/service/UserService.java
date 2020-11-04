package share.money.user.api.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import share.money.user.api.service.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(String id) throws BusinessException;

    List<UserDto> getUsers(Integer page, Integer limit);

    UserDto updateUser(String id, UserDto userDto);

    void deleteUser(String id);

    UserDto getUserDetailsByEmail(String email);
}
