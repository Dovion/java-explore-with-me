package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.dto.StatisticAdminDto;
import ru.practicum.explorewithme.model.Statistic;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    @Query("SELECT new ru.practicum.explorewithme.dto.StatisticAdminDto(s.app, s.uri, COUNT(s.id))" +
            " FROM Statistic as s " +
            " WHERE s.timestamp BETWEEN :start AND :end " +
            " GROUP BY s.app, s.uri "
    )
    List<StatisticAdminDto> getAllByDates(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.explorewithme.dto.StatisticAdminDto(s.app, s.uri, COUNT(s.id))" +
            " FROM Statistic as s " +
            " WHERE s.timestamp BETWEEN :start AND :end " +
            " GROUP BY s.app, s.uri, s.ip "
    )
    List<StatisticAdminDto> getAllByDatesWithUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.explorewithme.dto.StatisticAdminDto(s.app, s.uri, COUNT(s.id))" +
            " FROM Statistic as s " +
            " WHERE s.uri IN :uris AND s.timestamp BETWEEN :start AND :end " +
            " GROUP BY s.app, s.uri "
    )
    List<StatisticAdminDto> getAllByDatesAndUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.explorewithme.dto.StatisticAdminDto(s.app, s.uri, COUNT(s.id))" +
            " FROM Statistic as s " +
            " WHERE s.uri IN :uris AND s.timestamp BETWEEN :start AND :end " +
            " GROUP BY s.app, s.uri, s.ip "
    )
    List<StatisticAdminDto> getAllByDatesAndUrisWithUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
