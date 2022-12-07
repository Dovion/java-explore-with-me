package ru.practicum.explorewithme.adminAPI.service;

import ru.practicum.explorewithme.comment.dto.CommentFullDto;
import ru.practicum.explorewithme.exception.CommentStatusException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;

import java.util.List;

public interface AdminCommentService {

    public List<CommentFullDto> getAllCommentsByEventAndUsersWithFilter(List<Long> users,
                                                                        List<Long> events,
                                                                        List<String> states,
                                                                        String text,
                                                                        Integer from,
                                                                        Integer size) throws EntityNotFoundException;

    public CommentFullDto publishComment(Long eventId, Long commentId) throws EntityNotFoundException, CommentStatusException, EventStateException;

    public CommentFullDto rejectComment(Long eventId, Long commentId) throws EntityNotFoundException, CommentStatusException;

    public void deleteComment(Long eventId, Long commentId) throws EntityNotFoundException;
}
