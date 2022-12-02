package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.StatisticAdminDto;
import ru.practicum.explorewithme.dto.StatisticDto;

import java.util.List;

public interface StatisticService {

    void save(StatisticDto statisticDto);

    List<StatisticAdminDto> getAll(String start, String end, List<String> uris, Boolean unique);
}
