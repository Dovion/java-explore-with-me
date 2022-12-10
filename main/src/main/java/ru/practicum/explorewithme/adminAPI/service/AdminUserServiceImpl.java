package ru.practicum.explorewithme.adminAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.mapper.UserMapper;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<User> userList = userRepository.findAllById(ids, pageable);
        List<UserDto> userDtoList = new ArrayList<>();
        for (var user : userList) {
            userDtoList.add(UserMapper.userToUserDto(user));
        }
        log.info("Getting success");
        return userDtoList;
    }

    @Override
    @Transactional(rollbackFor = ConflictException.class)
    public UserDto addUser(UserDto userDto) throws ConflictException {
        User user = UserMapper.userDtoToUser(userDto);
        try {
            userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Can`t add user: user`s name already exist");
        }
        log.info("Adding success");
        return UserMapper.userToUserDto(user);
    }

    @Override
    public void removeUser(Long id) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Can`t remove user: user not found");
        }
        userRepository.deleteById(id);
        log.info("Removing success");
    }
}
