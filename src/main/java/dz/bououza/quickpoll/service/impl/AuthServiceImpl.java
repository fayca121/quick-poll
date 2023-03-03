package dz.bououza.quickpoll.service.impl;

import dz.bououza.quickpoll.domain.User;
import dz.bououza.quickpoll.dto.UserDto;
import dz.bououza.quickpoll.exception.PollApiException;
import dz.bououza.quickpoll.repository.UserRepository;
import dz.bououza.quickpoll.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public String register(UserDto userDto) {
        if(userRepository.existsByUserName(userDto.username()))
            throw new PollApiException(HttpStatus.BAD_REQUEST,"Username is already exists!.");
        User user=new User();
        user.setUserName(userDto.username());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "User registered successfully!.";
    }
}
