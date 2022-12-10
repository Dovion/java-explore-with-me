package ru.practicum.explorewithme.authAPI.service;

import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentFullDto;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import java.util.List;

public interface AuthCommentService {

    public List<CommentFullDto> getAllComments(Long eventId, Long userId, Integer from, Integer size) throws EntityNotFoundException;

    public CommentFullDto addComment(Long userId, Long eventId, CommentDto commentDto) throws EntityNotFoundException;

    public CommentFullDto updateComment(Long userId, Long eventId, Long commentId, CommentDto commentDto) throws EntityNotFoundException, ConflictException;

    public void deleteComment(Long userId, Long eventId, Long commentId) throws EntityNotFoundException, ConflictException;
}
