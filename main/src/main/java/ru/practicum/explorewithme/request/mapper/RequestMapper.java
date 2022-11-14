package ru.practicum.explorewithme.request.mapper;

import ru.practicum.explorewithme.request.dto.RequestDto;
import ru.practicum.explorewithme.request.model.Request;

public class RequestMapper {

    public static RequestDto requestToRequestDto(Request request){
        return new RequestDto(request.getCreated(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                String.valueOf(request.getState()));
    }
}
