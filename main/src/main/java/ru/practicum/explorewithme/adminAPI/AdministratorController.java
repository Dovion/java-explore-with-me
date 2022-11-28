package ru.practicum.explorewithme.adminAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;
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
public class AdministratorController {

    private final AdministratorService administratorService;

    @GetMapping("/events")
    public List<EventFullDto> getAllEvents(@RequestParam(defaultValue = "", required = false) Long[] users,
                                           @RequestParam(required = false) String[] states,
                                           @RequestParam(defaultValue = "", required = false) Long[] categories,
                                           @RequestParam(required = false) String rangeStart,
                                           @RequestParam(required = false) String rangeEnd,
                                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size,
                                           HttpServletRequest request) throws EntityNotFoundException {
        log.info("Getting all events by administrator: " + request.getServletPath());
        return administratorService.getAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable @Positive long eventId, @RequestBody @Valid EventDto eventDto, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Updating event by administrator: " + request.getServletPath());
        return administratorService.updateEvent(eventId, eventDto);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable @Positive long eventId, HttpServletRequest request) throws EventStateException, EntityNotFoundException {
        log.info("Publish event by administrator: " + request.getServletPath());
        return administratorService.publishEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable @Positive long eventId, HttpServletRequest request) throws EventStateException, EntityNotFoundException {
        log.info("Rejecting event by administrator: " + request.getServletPath());
        return administratorService.rejectEvent(eventId);
    }

    @PatchMapping("/categories")
    public CategoryFullDto updateCategory(@RequestBody @Valid CategoryFullDto categoryFullDto, HttpServletRequest request) throws EntityNotFoundException, ValidationException, ConflictException {
        log.info("Updating category by administrator: " + request.getServletPath());
        return administratorService.updateCategory(categoryFullDto);
    }

    @PostMapping("/categories")
    public CategoryFullDto addCategory(@RequestBody @Valid CategoryDto categoryDto, HttpServletRequest request) throws ValidationException, ConflictException {
        log.info("Adding category by administrator: " + request.getServletPath());
        return administratorService.addCategory(categoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    public void removeCategory(@PathVariable @Positive long catId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Removing category by administrator: " + request.getServletPath());
        administratorService.removeCategoryById(catId);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers(@RequestParam(defaultValue = "") Long[] ids,
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

    @PostMapping("/compilations")
    public CompilationFullDto addCompilation(@RequestBody @Valid CompilationDto compilationDto, HttpServletRequest request) {
        log.info("Adding compilation by administrator: " + request.getServletPath());
        return administratorService.addCompilation(compilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    public void removeCompilation(@PathVariable @Positive long compId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Removing compilation by administrator: " + request.getServletPath());
        administratorService.removeCompilation(compId);
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void removeEventFromCompilation(@PathVariable @Positive long compId, @PathVariable long eventId, HttpServletRequest request) throws
            EntityNotFoundException {
        log.info("Removing event from compilation by administrator: " + request.getServletPath());
        administratorService.removeEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void addEventInCompilation(@PathVariable @Positive long compId, @PathVariable @Positive long eventId, HttpServletRequest request) throws
            EntityNotFoundException {
        log.info("Adding event into compilation by administrator: " + request.getServletPath());
        administratorService.addEventInCompilation(compId, eventId);
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unpinCompilation(@PathVariable @Positive long compId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Unpinning compilation by administrator: " + request.getServletPath());
        administratorService.unpinCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pinCompilation(@PathVariable @Positive long compId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Pinning compilation by administrator: " + request.getServletPath());
        administratorService.pinCompilation(compId);
    }
}
