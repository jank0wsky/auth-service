package authservice.controllers;

import authservice.configs.Constants;
import authservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.API_ROUTE + "/users")
public class UserController {
    private final UserService userService;
}
