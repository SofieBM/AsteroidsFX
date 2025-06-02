package dk.sdu.mmmi.cbse.scoreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Main Spring Boot application for the Scoring MicroService.
 * This service provides REST endpoints to manage player scores.
 */
@SpringBootApplication
@RestController
@RequestMapping("/api/scores")
public class ScoreApplication
{
    private final Map<String, Integer> playerScores = new ConcurrentHashMap<>();

    /**
     * Main method to run the Spring Boot application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(ScoreApplication.class, args);
    }

    /**
     * Endpoint to add points to a specific player's score.
     * Example usage (POST request): http://localhost:8080/api/scores/add?playerId=player1&points=100
     *
     * @param playerId The unique identifier for the player.
     * @param points The number of points to add to the player's score.
     * @return The updated total score for the specified player.
     */
    @PostMapping("/add")
    public int addScore(@RequestParam String playerId, @RequestParam int points)
    {
        // Atomically updates the score: if player exists, adds points; otherwise, sets initial points.
        playerScores.merge(playerId, points, Integer::sum);
        System.out.println("Score updated for " + playerId + ": " + playerScores.get(playerId));
        return playerScores.get(playerId);
    }

    /**
     * Endpoint to retrieve the current score for a specific player.
     * Example usage (GET request): http://localhost:8080/api/scores/player1
     *
     * @param playerId The unique identifier for the player.
     * @return The current score for the specified player, or 0 if the player has no recorded score.
     */
    @GetMapping("/{playerId}")
    public int getScore(@PathVariable String playerId) {
        return playerScores.getOrDefault(playerId, 0);
    }

    /**
     * Endpoint to reset a specific player's score to zero.
     * Example usage (POST request): http://localhost:8080/api/scores/reset?playerId=player1
     *
     * @param playerId The unique identifier for the player.
     * @return 0, indicating the score has been reset.
     */
    @PostMapping("/reset")
    public int resetScore(@RequestParam String playerId)
    {
        playerScores.put(playerId, 0); // Set score to 0
        System.out.println("Score reset for " + playerId);
        return 0;
    }

    /**
     * Optional: Endpoint to retrieve a simple leaderboard of top scores.
     * Example usage (GET request): http://localhost:8080/api/scores/leaderboard
     *
     * @return A map of player IDs to their scores, sorted in descending order by score,
     * limited to the top 5 entries.
     */
    @GetMapping("/leaderboard")
    public Map<String, Integer> getLeaderboard()
    {
        return playerScores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5) // Get top 5
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
