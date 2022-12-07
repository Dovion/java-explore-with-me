package ru.practicum.explorewithme.adminAPI.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.adminAPI.service.AdminCommentService;
import ru.practicum.explorewithme.comment.dto.CommentFullDto;
import ru.practicum.explorewithme.exception.CommentStatusException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminCommentController {

    private final AdminCommentService administratorService;

    @GetMapping("/events/comment")
    public List<CommentFullDto> getAllComments(@RequestParam(required = false) List<Long> users,
                                               @RequestParam(required = false) List<Long> events,
                                               @RequestParam(required = false) List<String> states,
                                               @RequestParam(defaultValue = "") String text,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                               @RequestParam(defaultValue = "10") @Positive Integer size,
                                               HttpServletRequest request) throws EntityNotFoundException {
        log.info("Getting comments by administrator:{}", request.getServletPath());
        return administratorService.getAllUserComments(users, events, states, text, from, size);
    }

    @PatchMapping("/events/{eventId}/comment/{commentId}/publish")
    public CommentFullDto publishComment(@PathVariable @Positive long eventId, @PathVariable @Positive long commentId, HttpServletRequest request) throws EventStateException, EntityNotFoundException, CommentStatusException {
        log.info("Publishing comment:{} for event:{} by administrator:{}", commentId, eventId, request.getServletPath());
        return administratorService.publishComment(eventId, commentId);
    }

    @PatchMapping("/events/{eventId}/comment/{commentId}/reject")
    public CommentFullDto rejectComment(@PathVariable @Positive long eventId, @PathVariable @Positive long commentId, HttpServletRequest request) throws EntityNotFoundException, CommentStatusException {
        log.info("Rejecting comment:{} for event:{} by administrator:{}", commentId, eventId, request.getServletPath());
        return administratorService.rejectComment(eventId, commentId);
    }

    @DeleteMapping("/events/{eventId}/comment/{commentId}")
    public void deleteCommentByAdmin(@PathVariable @Positive long eventId, @PathVariable @Positive long commentId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Deleting comment to event:{} by administrator at: {}", eventId, request.getServletPath());
        administratorService.deleteComment(eventId, commentId);
    }
}
