package ru.practicum.explorewithme.adminAPI.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.adminAPI.service.AdminUserService;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.user.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminUserController {

    private final AdminUserService administratorService;

    @GetMapping("/users")
    public List<UserDto> getAllUsers(@RequestParam(defaultValue = "") List<Long> ids,
                                     @PositiveOrZero
                                     @RequestParam(defaultValue = "0") Integer from,
                                     @Positive
                                     @RequestParam(defaultValue = "10") Integer size,
                                     HttpServletRequest request) {
        log.info("Getting all users by administrator: " + request.getServletPath());
        return administratorService.getAllUsers(ids, from, size);
    }

    @PostMapping("/users")
    public UserDto addUser(@RequestBody @Valid UserDto userDto, HttpServletRequest request) throws ValidationException, ConflictException {
        log.info("Adding user by administrator: " + request.getServletPath());
        return administratorService.addUser(userDto);
    }

    @DeleteMapping("/users/{userId}")
    public void removeUser(@PathVariable @Positive @Valid long userId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Removing user by administrator: " + request.getServletPath());
        administratorService.removeUser(userId);
    }
}
