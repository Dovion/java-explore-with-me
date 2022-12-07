package ru.practicum.explorewithme.comment.mapper;

import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentFullDto;
import ru.practicum.explorewithme.comment.dto.CommentPubDto;
import ru.practicum.explorewithme.comment.model.Comment;

public class CommentMapper {

    public static Comment commentDtoToComment(CommentDto commentDto) {
        return new Comment(null,
                null,
                commentDto.getText(),
                null,
                null,
                null);
    }

    public static CommentFullDto commentToCommentFullDto(Comment comment) {
        return new CommentFullDto(comment.getId(),
                comment.getAuthor().getId(),
                comment.getText(),
                String.valueOf(comment.getStatus()),
                comment.getEvent().getId(),
                comment.getPublishedOn());
    }

    public static CommentPubDto commentToCommentPubDto(Comment comment) {
        return new CommentPubDto(comment.getAuthor().getId(),
                comment.getText(),
                comment.getPublishedOn());
    }
}
