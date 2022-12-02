package ru.practicum.explorewithme.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explorewithme.dto.StatisticDto;

import java.util.List;
import java.util.Map;

@Service
public class StatisticClient extends BaseClient {

    @Autowired
    public StatisticClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> getAll(String start, String end, List<String> uris, Boolean unique) {
        Map<String, Object> parameters = Map.of("start", start, "end", end, "uris", uris, "unique", unique);
        return get("/stats/?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

    public void save(StatisticDto statisticDto) {
        post("/hit", statisticDto);
    }
}
