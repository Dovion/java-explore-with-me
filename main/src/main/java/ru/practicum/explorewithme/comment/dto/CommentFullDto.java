package ru.practicum.explorewithme.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentFullDto {

    private Long id;
    private Long author;
    private String text;
    private String status;
    private Long event;
    private LocalDateTime publishedOn;
}
