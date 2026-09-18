package authservice.controllers;

import authservice.configs.Constants;
import authservice.entities.UserMapper;
import authservice.services.UserService;
import authservice.utils.auths.JwtUtil;
import authservice.utils.auths.dtos.JwtDto;
import authservice.utils.auths.dtos.LoginDto;
import authservice.utils.auths.dtos.SignInDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(Constants.AUTH_ROUTE)
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("User logged in successfully with {}", SecurityContextHolder.getContext().getAuthentication());
            return ResponseEntity.ok(new JwtDto(jwtUtil.generateToken(dto.email())));
        } catch (Exception e) {
            log.error("Error while logging in user {}", dto.email());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(@RequestBody SignInDto dto) {
        try {
            userService.create(UserMapper.INSTANCE.toRequestDto(dto));

            log.info("User signed in successfully with {}", dto.email());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error while signing up user {}", dto.email());
        }
        return ResponseEntity.badRequest().build();
    }
}
