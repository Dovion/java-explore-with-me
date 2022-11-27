package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.StatisticAdminDto;
import ru.practicum.explorewithme.dto.StatisticDto;
import ru.practicum.explorewithme.model.Statistic;

public class StatisticMapper {

    public static StatisticAdminDto statisticToStatisticAdminDto(Statistic statistic) {
        return new StatisticAdminDto(statistic.getApp(),
                statistic.getUri(),
                null);
    }

    public static Statistic statisticDtoToStatistic(StatisticDto statisticDto) {
        return new Statistic(statisticDto.getId(),
                statisticDto.getApp(),
                statisticDto.getUri(),
                statisticDto.getIp(),
                statisticDto.getTimestamp());
    }
}
