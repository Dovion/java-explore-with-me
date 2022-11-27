package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.StatisticAdminDto;
import ru.practicum.explorewithme.dto.StatisticDto;
import ru.practicum.explorewithme.service.StatisticService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class StatisticController {

    private final StatisticService statisticService;

    @PostMapping("/hit")
    public void save(@RequestBody StatisticDto statisticDto) {
        statisticService.save(statisticDto);
    }

    @GetMapping("/stats")
    public List<StatisticAdminDto> getAll(@NotNull @RequestParam String start,
                                          @NotNull @RequestParam String end,
                                          @Valid @RequestParam(required = false) List<String> uris,
                                          @RequestParam(defaultValue = "false") Boolean unique) {
        return statisticService.getAll(start, end, uris, unique);
    }
}
