package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlayerControlSystem implements IEntityProcessingService {

    private final List<BulletSPI> bulletSPIs;
    /**
     * Constructor for PlayerControlSystem. Spring will automatically inject
     * the list of available BulletSPI implementations.
     * @param bulletSPIs A list of BulletSPI implementations.
     */
    @Autowired
    public PlayerControlSystem(List<BulletSPI> bulletSPIs) {
        this.bulletSPIs = bulletSPIs;
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            // Player rotation based on LEFT/RIGHT keys
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 5);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 5);
            }

            // Player movement based on UP key
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }

            // Player shooting based on SPACE key
            if (gameData.getKeys().isDown(GameKeys.SPACE)) {
                if (!bulletSPIs.isEmpty()) {
                    getBulletSPI().ifPresent(
                            spi -> {
                                world.addEntity(spi.createBullet(player, gameData));
                            }
                    );
                } else
                {
                }
            }

            // Screen wrapping for the player
            if (player.getX() < 0) {
                player.setX(gameData.getDisplayWidth());
            }
            if (player.getX() > gameData.getDisplayWidth()) {
                player.setX(0);
            }
            if (player.getY() < 0) {
                player.setY(gameData.getDisplayHeight());
            }
            if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(0);
            }
        }
    }

    /**
     * Helper method to retrieve the first available BulletSPI.
     * @return An Optional containing the first BulletSPI found, or empty if none.
     */
    private Optional<BulletSPI> getBulletSPI() {
        return bulletSPIs.stream().findFirst();
    }
}