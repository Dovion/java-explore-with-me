package ru.practicum.explorewithme.adminAPI.service;

import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.user.dto.UserDto;

import java.util.List;

public interface AdminUserService {

    List<UserDto> getAllUsers(List<Long> ids,
                              Integer from,
                              Integer size);

    UserDto addUser(UserDto userDto) throws ValidationException, ConflictException;

    void removeUser(Long id) throws EntityNotFoundException;
}
