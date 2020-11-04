package share.money.user.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import share.money.user.api.controller.model.request.UserRequestModel;
import share.money.user.api.controller.model.response.AddressRest;
import share.money.user.api.controller.model.response.OperationStatusModel;
import share.money.user.api.controller.model.response.UserRest;
import share.money.user.api.service.AddressesService;
import share.money.user.api.service.BusinessException;
import share.money.user.api.service.UserService;
import share.money.user.api.service.dto.AddressDto;
import share.money.user.api.service.dto.UserDto;
import share.money.user.api.shared.ModelMapper;
import share.money.user.api.shared.Roles;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private UserService userService;
    private AddressesService addressesService;

    @Autowired
    public UserController(UserService userService, AddressesService addressesService) {
        this.userService = userService;
        this.addressesService = addressesService;
    }

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) throws BusinessException {

        UserDto userDto = userService.getUserById(id);
        return ModelMapper.map(userDto, UserRest.class);
    }

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                   @RequestParam(name = "limit", defaultValue = "25") Integer limit) {

        List<UserDto> userDto = userService.getUsers(page, limit);
        return userDto.stream().map(dto -> ModelMapper.map(dto, UserRest.class)).collect(Collectors.toList());
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserRequestModel userRequestModel) {

        UserDto userDto = ModelMapper.map(userRequestModel, UserDto.class);
        userDto.setRoles(new HashSet<>(Collections.singletonList(Roles.ROLE_USER.name())));
        UserDto userDtoOutcome = userService.createUser(userDto);

        return ModelMapper.map(userDtoOutcome, UserRest.class);
    }

    @PutMapping(path = "/{id}")
    public UserRest updateUser(@PathVariable String id, @RequestBody UserRequestModel userRequestModel) {

        UserDto userDtoIncome = ModelMapper.map(userRequestModel, UserDto.class);
        UserDto userDtoOutcome = userService.updateUser(id, userDtoIncome);

        return ModelMapper.map(userDtoOutcome, UserRest.class);
    }

    //    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new OperationStatusModel("Success", "Delete");
    }

    @GetMapping(path = "/{user_id}/addresses")
    public List<AddressRest> getUserAddresses(@PathVariable(name = "user_id") String userId) {

        List<AddressDto> addresses = addressesService.getUserAddressesById(userId);

        return addresses.stream().map(ad -> ModelMapper.map(ad, AddressRest.class)).collect(Collectors.toList());
    }

    @GetMapping(path = "/{user_id}/addresses/{address_id}")
    public AddressRest getAddress(@PathVariable(name = "address_id") String addressId) {

        AddressDto addresses = addressesService.getAddressById(addressId);
        return ModelMapper.map(addresses, AddressRest.class);
    }
}
