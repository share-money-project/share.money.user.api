package share.money.user.api.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import share.money.user.api.controller.model.response.WalletRest;
import share.money.user.api.repository.RoleRepository;
import share.money.user.api.repository.UserRepository;
import share.money.user.api.repository.entity.RoleEntity;
import share.money.user.api.repository.entity.UserEntity;
import share.money.user.api.security.UserPrincipal;
import share.money.user.api.service.UserService;
import share.money.user.api.service.dto.UserDto;
import share.money.user.api.shared.ModelMapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private Environment environment;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(UserDto userDto) {
        userRepository.findByEmailAsOptional(userDto.getEmail()).ifPresent((e) -> {
            throw new RuntimeException(String.format("Record with email [%s] already exist", e.getEmail()));
        });

        userDto.getAddresses().forEach(ad -> {
            ad.setAddressId(UUID.randomUUID().toString());
            ad.setUserDetails(userDto);
        });

        UserEntity userEntity = ModelMapper.map(userDto, UserEntity.class);

        userEntity.setUserId((UUID.randomUUID().toString()));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        Collection<RoleEntity> roleEntities = new HashSet<>();
        for (String role : userDto.getRoles()) {
            RoleEntity roleEntity = roleRepository.findByName(role);
            if (roleEntity != null) roleEntities.add(roleEntity);
        }

        userEntity.setRoles(roleEntities);

        UserEntity savedUser = userRepository.save(userEntity);

        return ModelMapper.map(savedUser, UserDto.class);
    }

    public UserDto getUserById(String id) {
        UserEntity userEntity = userRepository.findByUserIdAsOptional(id).orElseThrow(() -> new RuntimeException(String.format("User with id [%s] wasn't find", id)));

        String walletUrl = String.format(environment.getProperty("wallet.url"), id);
        ResponseEntity<WalletRest> walletRestResponseEntity = restTemplate.exchange(walletUrl, HttpMethod.GET, null, new ParameterizedTypeReference<WalletRest>() {});
        WalletRest walletRest = walletRestResponseEntity.getBody();

        UserDto userDto = ModelMapper.map(userEntity, UserDto.class);
        userDto.setWallet(walletRest);
        return userDto;
    }

    public List<UserDto> getUsers(Integer page, Integer limit) {

        if (page > 0) --page;

        List<UserEntity> content = userRepository.findAll(PageRequest.of(page, limit)).getContent();

        return content.stream().map(entity -> ModelMapper.map(entity, UserDto.class)).collect(Collectors.toList());
    }

    public UserDto updateUser(String id, UserDto userDto) {
        UserEntity userEntity = userRepository.findByUserIdAsOptional(id).orElseThrow(() -> new RuntimeException(String.format("User with id [%s] wasn't find", id)));

        if (!StringUtils.isEmpty(userDto.getFirstName())) userEntity.setFirstName(userDto.getFirstName());
        if (!StringUtils.isEmpty(userDto.getLastName())) userEntity.setLastName(userDto.getLastName());
        if (!StringUtils.isEmpty(userDto.getEmail())) userEntity.setEmail(userDto.getEmail());

        UserEntity updatedUser = userRepository.save(userEntity);

        return ModelMapper.map(updatedUser, UserDto.class);
    }

    public void deleteUser(String id) {
        UserEntity userEntity = userRepository.findByUserIdAsOptional(id).orElseThrow(() -> new RuntimeException(String.format("User with id [%s] wasn't find", id)));
        userRepository.delete(userEntity);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmailAsOptional(email).orElseThrow(() -> new RuntimeException(String.format("User with id [%s] wasn't find", email)));
        return ModelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmailAsOptional(email).orElseThrow(() -> new RuntimeException(String.format("User with id [%s] wasn't find", email)));
        return new UserPrincipal(userEntity);
    }
}
