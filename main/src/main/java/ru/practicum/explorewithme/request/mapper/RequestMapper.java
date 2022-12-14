package ru.practicum.explorewithme.request.mapper;

import ru.practicum.explorewithme.request.dto.RequestFullDto;
import ru.practicum.explorewithme.request.model.Request;

public class RequestMapper {

    public static RequestFullDto requestToRequestDto(Request request) {
        return new RequestFullDto(request.getCreated(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                String.valueOf(request.getState()));
    }
}
