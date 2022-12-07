package ru.practicum.explorewithme.authAPI.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.authAPI.service.AuthCommentService;
import ru.practicum.explorewithme.authAPI.service.AuthEventService;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentFullDto;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class AuthCommentController {

    private final AuthCommentService authorizeService;

    @GetMapping("/{userId}/events/{eventId}/comment")
    public List<CommentFullDto> getAllAuthorComments(@PathVariable @Positive long userId,
                                               @PathVariable @Positive long eventId,
                                               @RequestParam(defaultValue = "0",required = false) @PositiveOrZero Integer from,
                                               @RequestParam(defaultValue = "10", required = false) @Positive Integer size,
                                               HttpServletRequest request) throws EntityNotFoundException {
        log.info("Getting all comments to event:{} by user id:{} at: {}", eventId, userId, request.getServletPath());
        return authorizeService.getAllComments(eventId, userId, from, size);
    }


    @PostMapping("/{userId}/events/{eventId}/comment")
    public CommentFullDto addCommentByAuthor(@PathVariable @Positive long userId, @PathVariable @Positive long eventId, @RequestBody @Valid CommentDto commentDto, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Adding comment to event:{} by user id:{} at: {}", eventId, userId, request.getServletPath());
        return authorizeService.addComment(userId, eventId, commentDto);
    }

    @PatchMapping("/{userId}/events/{eventId}/comment/{commentId}")
    public CommentFullDto updateCommentByAuthor(@PathVariable @Positive long userId, @PathVariable @Positive long eventId, @PathVariable @Positive long commentId, @RequestBody @Valid CommentDto commentDto, HttpServletRequest request) throws EntityNotFoundException, ConflictException {
        log.info("Updating comment to event:{} by user id:{} at: {}", eventId, userId, request.getServletPath());
        return authorizeService.updateComment(userId, eventId, commentId, commentDto);
    }

    @DeleteMapping("/{userId}/events/{eventId}/comment/{commentId}")
    public void deleteCommentByAuthor(@PathVariable @Positive long userId, @PathVariable @Positive long eventId, @PathVariable @Positive long commentId, HttpServletRequest request) throws EntityNotFoundException, ConflictException {
        log.info("Deleting comment to event:{} by user id:{} at: {}", eventId, userId, request.getServletPath());
        authorizeService.deleteComment(userId, eventId, commentId);
    }
}
