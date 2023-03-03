package dz.bououza.quickpoll.controller;

import dz.bououza.quickpoll.dto.UserDto;
import dz.bououza.quickpoll.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService,
                          AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager=authenticationManager;
    }

    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<String> login(@RequestBody UserDto userDto){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.username(),userDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("User successfully authenticated");
    }

    @PostMapping( value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody UserDto userDto){
        String response = authService.register(userDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
