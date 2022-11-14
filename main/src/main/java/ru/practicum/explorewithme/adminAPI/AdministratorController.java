package ru.practicum.explorewithme.adminAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.authAPI.AuthorizeService;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.publicAPI.PublicService;
import ru.practicum.explorewithme.user.dto.UserDto;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdministratorController {

    private final AdministratorService administratorService;

    @GetMapping("/events")
    public List<EventFullDto> getAllEvents(@RequestParam Long[] users,
                                           @RequestParam String[] states,
                                           @RequestParam Long[] categories,
                                           @RequestParam String rangeStart,
                                           @RequestParam String rangeEnd,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size) {
        return administratorService.getAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable long eventId, @RequestBody EventDto eventDto) {
        return administratorService.updateEvent(eventId, eventDto);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable long eventId) {
        return administratorService.publishEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable long eventId) {
        return administratorService.rejectEvent(eventId);
    }

    @PatchMapping("/categories")
    public CategoryFullDto updateCategory(@RequestBody CategoryFullDto categoryFullDto) {
        return administratorService.updateCategory(categoryFullDto);
    }

    @PostMapping("/categories")
    public CategoryFullDto addCategory(@RequestBody CategoryDto categoryDto) {
        return administratorService.addCategory(categoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    public void removeCategory(@PathVariable long catId) {
        administratorService.removeCategoryById(catId);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers(Long[] users,
                                     Integer from,
                                     Integer size) {
        return administratorService.getAllUsers(users, from, size);
    }

    @PostMapping("/users")
    public UserDto addUser(@RequestBody UserDto userDto){
        return administratorService.addUser(userDto);
    }

    @DeleteMapping("/users/{userId}")
    public void removeUser(@PathVariable long userId){
        administratorService.removeUser(userId);
    }

    @PostMapping("/compilations")
    public CompilationFullDto addCompilation(@RequestBody CompilationDto compilationDto){
        return administratorService.addCompilation(compilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    public void removeCompilation(@PathVariable long compId){
        administratorService.removeCompilation(compId);
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void removeEventFromCompilation(@PathVariable long compId, @PathVariable long eventId){
        administratorService.removeEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void addEventInCompilation(@PathVariable long compId, @PathVariable long eventId){
        administratorService.addEventInCompilation(compId, eventId);
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unpinCompilation(@PathVariable long compId){
        administratorService.unpinCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pinCompilation(@PathVariable long compId){
        administratorService.pinCompilation(compId);
    }
}
