package ru.practicum.explorewithme.authAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentFullDto;
import ru.practicum.explorewithme.comment.mapper.CommentMapper;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.comment.model.CommentStatus;
import ru.practicum.explorewithme.comment.repository.CommentRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthCommentServiceImpl implements AuthCommentService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CommentFullDto> getAllComments(Long eventId, Long userId, Integer from, Integer size) throws EntityNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t get all comments: user not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t get all comments: event not found"));
        Pageable pageable = PageRequest.of(from / size, size);
        List<CommentFullDto> commentFullDtoList = new ArrayList<>();
        for (var comment : commentRepository.getAllByEventIdAndUserId(eventId, userId, pageable)) {
            commentFullDtoList.add(CommentMapper.commentToCommentFullDto(comment));
        }
        log.info("Getting success");
        return commentFullDtoList;
    }

    @Override
    public CommentFullDto addComment(Long userId, Long eventId, CommentDto commentDto) throws EntityNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t add comment: user not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t add comment: event not found"));
        Comment comment = CommentMapper.commentDtoToComment(commentDto);
        comment.setAuthor(user);
        comment.setStatus(CommentStatus.WAITING);
        comment.setEvent(event);
        event.getComments().add(comment);
        commentRepository.saveAndFlush(comment);
        log.info("Saving success");
        var result = CommentMapper.commentToCommentFullDto(comment);
        return result;
    }

    @Override
    public CommentFullDto updateComment(Long userId, Long eventId, Long commentId, CommentDto commentDto) throws EntityNotFoundException, ConflictException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t update comment: user not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t update comment: event not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Can`t update comment: comment not found"));
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ConflictException("Can`t update comment: only author can edit his comment");
        }
        if (comment.getStatus() == CommentStatus.PUBLISHED || comment.getStatus() == CommentStatus.REJECTED) {
            comment.setStatus(CommentStatus.WAITING);
        }
        if (comment.getPublishedOn() != null) {
            comment.setPublishedOn(null);
        }
        comment.setText(commentDto.getText());
        commentRepository.saveAndFlush(comment);
        log.info("Updating success");
        return CommentMapper.commentToCommentFullDto(comment);
    }

    @Override
    public void deleteComment(Long userId, Long eventId, Long commentId) throws EntityNotFoundException, ConflictException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t delete comment: user not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t delete comment: event not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Can`t delete comment: comment not found"));
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ConflictException("Can`t delete comment: only author can delete his comment");
        }
        commentRepository.deleteById(commentId);
        log.info("Removing success");
    }
}
