package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid; // Import Asteroid class
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IScoreService; // NEW: Import IScoreService
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional; // NEW: Import Optional for cleaner service access

/**
 * The main game class for the Asteroids game.
 * Handles game initialization, updates, rendering, and input.
 * Now integrates with a scoring microservice.
 */
@Component
class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();

    // Injected service lists
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServiceList;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;
    private final List<IScoreService> scoreServices;

    private Text scoreText;
    private final String playerId = "player1";

    /**
     * Constructor for the Game class, receiving all necessary service lists.
     * @param gamePluginServices List of game plugin services.
     * @param entityProcessingServiceList List of entity processing services.
     * @param postEntityProcessingServices List of post-entity processing services.
     * @param scoreServices List of score services (expected to contain ScoreServiceClient).
     */
    Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServiceList,
         List<IPostEntityProcessingService> postEntityProcessingServices, List<IScoreService> scoreServices) { // NEW parameter
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServiceList = entityProcessingServiceList;
        this.postEntityProcessingServices = postEntityProcessingServices;
        this.scoreServices = scoreServices;
    }

    /**
     * Initializes the JavaFX stage and starts the game loop.
     * @param window The primary stage for this application.
     * @throws Exception If any error occurs during initialization.
     */
    public void start(Stage window) throws Exception {
        // Initialize the score display text
        scoreText = new Text(10, 20, "Score: 0");
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(scoreText);
        Scene scene = new Scene(gameWindow);
        // Set up keyboard input handlers
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
        });

        // Start all registered game plugins
        for (IGamePluginService iGamePlugin : getGamePluginServices()) {
            iGamePlugin.start(gameData, world);
        }

        // Add initial entities to the game window
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();

        //Reset player score at the beginning of the game
        getScoreService().ifPresent(service -> service.resetScore(playerId));
    }

    /**
     * Starts the animation timer for the game loop.
     */
    public void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
                updateScoreDisplay();
            }
        }.start();
    }

    /**
     * Updates the game state by processing entities and handling post-processing (like collisions).
     */
    private void update() {
        // Process all entities (e.g., player movement, bullet movement, asteroid movement)
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        // Capture entities before post-processing to detect removals (e.g., collisions)
        List<Entity> entitiesBeforePostProcessing = List.copyOf(world.getEntities());

        // Perform post-processing (e.g., collision detection and resolution)
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }

        //Detect which entities were removed during post-processing and update score
        List<Entity> entitiesAfterPostProcessing = List.copyOf(world.getEntities());
        for (Entity entity : entitiesBeforePostProcessing) {
            if (!entitiesAfterPostProcessing.contains(entity)) {
                // If an entity was present before but not after, it was removed
                if (entity instanceof Asteroid) {
                    // If the removed entity was an Asteroid, add score
                    getScoreService().ifPresent(service -> service.addScore(playerId, 100));
                }
            }
        }
    }

    /**
     * Draws or updates the visual representation of entities on the screen.
     */
    private void draw() {
        // Remove polygons for entities that are no longer in the world
        polygons.keySet().removeIf(entity -> {
            if (!world.getEntities().contains(entity)) {
                Polygon removedPolygon = polygons.get(entity);
                gameWindow.getChildren().remove(removedPolygon);
                return true;
            }
            return false;
        });

        // Add new polygons for new entities and update existing ones' positions/rotations
        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                // If polygon doesn't exist, create and add it
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            // Update position and rotation
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    /**
     * Updates the score display on the game window by fetching the current score
     * from the score service.
     */
    private void updateScoreDisplay() {
        getScoreService().ifPresent(service -> {
            int currentScore = service.getScore(playerId);
            scoreText.setText("Score: " + currentScore);
        });
    }

    /**
     * Helper method to retrieve an IScoreService instance.
     * Assumes there's at least one IScoreService available via ServiceLoader.
     * @return An Optional containing the first IScoreService found, or empty if none.
     */
    private Optional<IScoreService> getScoreService() {
        return scoreServices.stream().findFirst();
    }

    // Existing getters for service lists
    public List<IGamePluginService> getGamePluginServices() {
        return gamePluginServices;
    }

    public List<IEntityProcessingService> getEntityProcessingServices() {
        return entityProcessingServiceList;
    }

    public List<IPostEntityProcessingService> getPostEntityProcessingServices() {
        return postEntityProcessingServices;
    }

    /**
     * Getter for the list of IScoreService implementations.
     * @return The list of IScoreService instances.
     */
    public List<IScoreService> getScoreServices() {
        return scoreServices;
    }
}
