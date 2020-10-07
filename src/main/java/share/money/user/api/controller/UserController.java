package share.money.user.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import share.money.user.api.controller.model.response.UserRest;
import share.money.user.api.controller.model.request.UserRequestModel;
import share.money.user.api.service.AddressesService;
import share.money.user.api.service.UserService;
import share.money.user.api.service.dto.UserDto;
import share.money.user.api.shared.ModelMapper;
import share.money.user.api.shared.Roles;

import java.util.Collections;
import java.util.HashSet;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressesService addressesService;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {

        UserDto userDto = userService.getUserById(id);
        return ModelMapper.map(userDto, UserRest.class);
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserRequestModel userRequestModel) {

        UserDto userDto = ModelMapper.map(userRequestModel, UserDto.class);
        userDto.setRoles(new HashSet<>(Collections.singletonList(Roles.ROLE_USER.name())));
        UserDto userDtoOutcome = userService.createUser(userDto);

        return ModelMapper.map(userDtoOutcome, UserRest.class);
    }
}
