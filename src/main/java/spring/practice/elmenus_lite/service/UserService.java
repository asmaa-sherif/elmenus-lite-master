package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.user.UserResponseDto;
import java.util.List;

public interface UserService {

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    UserResponseDto addUser(UserResponseDto userDto);

    UserResponseDto updateUser(Long id, UserResponseDto userDto);

    void deleteUser(Long id);
}
