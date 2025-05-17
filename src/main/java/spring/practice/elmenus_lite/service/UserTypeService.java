package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.UserTypeResponseDto;
import java.util.List;

public interface UserTypeService {

    UserTypeResponseDto getUserTypeById(Long id);

    List<UserTypeResponseDto> getAllUserTypes();

    UserTypeResponseDto addUserType(UserTypeResponseDto userTypeDto);

    UserTypeResponseDto updateUserType(Long id, UserTypeResponseDto userTypeDto);

    void deleteUserType(Long id);
}
