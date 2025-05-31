package dk.sdu.mmmi.cbse; // Double-check this package name!

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;
    private Random rnd = new Random();

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // Create an Enemy spaceship when the game starts
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-7, -7, 10, 0, -7, 7);

        // This adds the enemy at a random location on the screen
        enemyShip.setX(rnd.nextInt(gameData.getDisplayWidth()));
        enemyShip.setY(rnd.nextInt(gameData.getDisplayHeight()));
        enemyShip.setRadius(7);
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // This removes the enemy spaceship when the plugin stops
        world.removeEntity(enemy);
    }
}