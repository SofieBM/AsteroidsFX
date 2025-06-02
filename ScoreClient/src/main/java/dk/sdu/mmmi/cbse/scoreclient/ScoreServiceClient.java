package dk.sdu.mmmi.cbse.scoreclient;

import dk.sdu.mmmi.cbse.common.services.IScoreService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ScoreServiceClient implements IScoreService
{

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/scores";

    public ScoreServiceClient() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public int addScore(String playerId, int points)
    {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/add")
                .queryParam("playerId", playerId)
                .queryParam("points", points)
                .toUriString();
        Integer newScore = restTemplate.postForObject(uri, null, Integer.class);
        return newScore != null ? newScore : 0;
    }

    @Override
    public int getScore(String playerId)
    {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/{playerId}")
                .buildAndExpand(playerId)
                .toUriString();
        Integer score = restTemplate.getForObject(uri, Integer.class);
        return score != null ? score : 0;
    }

    @Override
    public int resetScore(String playerId)
    {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/reset")
                .queryParam("playerId", playerId)
                .toUriString();
        Integer resetValue = restTemplate.postForObject(uri, null, Integer.class);
        return resetValue != null ? resetValue : 0;
    }
}