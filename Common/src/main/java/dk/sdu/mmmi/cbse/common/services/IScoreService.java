package dk.sdu.mmmi.cbse.common.services;

/**
 * Interface for a scoring service that allows updating and retrieving player scores.
 * This acts as a Service Provider Interface (SPI) for the game to interact with
 * any concrete scoring implementation (e.g., a local service or a microservice client).
 */
public interface IScoreService
{

    /**
     * Adds points to a specific player's score.
     *
     * @param playerId The ID of the player whose score is to be updated.
     * Pre-condition: playerId must not be null or empty.
     * @param points The number of points to add. Can be negative for deductions.
     * @return The new total score for the player after the points are added.
     * Post-condition: The player's score is updated.
     */
    int addScore(String playerId, int points);

    /**
     * Retrieves the current score for a specific player.
     *
     * @param playerId The ID of the player whose score is to be retrieved.
     * Pre-condition: playerId must not be null or empty.
     * @return The current score for the player, or 0 if the player has no recorded score.
     * Post-condition: The method returns the current score without modifying it.
     */
    int getScore(String playerId);

    /**
     * Resets the score for a specific player to zero.
     *
     * @param playerId The ID of the player whose score is to be reset.
     * Pre-condition: playerId must not be null or empty.
     * @return 0, indicating the score has been successfully reset.
     * Post-condition: The player's score is set to 0.
     */
    int resetScore(String playerId);
}
