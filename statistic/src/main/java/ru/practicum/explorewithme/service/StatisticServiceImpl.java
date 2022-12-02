package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.StatisticAdminDto;
import ru.practicum.explorewithme.dto.StatisticDto;
import ru.practicum.explorewithme.mapper.StatisticMapper;
import ru.practicum.explorewithme.repository.StatisticRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;

    @Override
    public void save(StatisticDto statisticDto) {
        statisticRepository.saveAndFlush(StatisticMapper.statisticDtoToStatistic(statisticDto));
    }

    @Override
    public List<StatisticAdminDto> getAll(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startDate = LocalDateTime.now();
        if (start != null) {
            startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        LocalDateTime endDate = LocalDateTime.now();
        if (end != null) {
            endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        List<StatisticAdminDto> result = new ArrayList<>();
        if (unique && uris != null) {
            result = statisticRepository.getAllByDatesAndUrisWithUniqueIp(startDate, endDate, uris);
        } else if (!unique && uris != null) {
            result = statisticRepository.getAllByDatesAndUris(startDate, endDate, uris);
        } else if (unique && uris == null) {
            result = statisticRepository.getAllByDatesWithUniqueIp(startDate, endDate);
        } else {
            result = statisticRepository.getAllByDates(startDate, endDate);
        }
        return result;
    }
}
