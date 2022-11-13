package ru.practicum.explorewithme.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    @NotBlank
    private String name;
}
