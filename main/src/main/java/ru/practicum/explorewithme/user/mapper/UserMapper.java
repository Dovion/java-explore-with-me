package ru.practicum.explorewithme.user.mapper;

import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.model.User;

public class UserMapper {

    public static User userDtoToUser(UserDto userDto){
        return new User(userDto.getId(), userDto.getEmail(), userDto.getName());
    }

    public static UserDto userToUserDto(User user){
        return new UserDto(user.getEmail(), user.getId(), user.getName());
    }
}
