package dz.bououza.quickpoll.service;

import dz.bououza.quickpoll.dto.UserDto;

public interface AuthService {
    String register(UserDto userDto);
}
