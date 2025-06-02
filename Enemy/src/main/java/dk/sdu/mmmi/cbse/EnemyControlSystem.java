package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class EnemyControlSystem implements IEntityProcessingService
{

    private final Random random = new Random();
    private final List<BulletSPI> bulletSPIs;

    /**
     * Constructor for EnemyControlSystem. Spring will automatically inject
     * the list of available BulletSPI implementations.
     * @param bulletSPIs A list of BulletSPI implementations.
     */
    @Autowired
    public EnemyControlSystem(List<BulletSPI> bulletSPIs) {
        this.bulletSPIs = bulletSPIs;
    }

    @Override
    public void process(GameData gameData, World world)
    {
        for (Entity enemy : world.getEntities(Enemy.class))
        {
            // Enemy movement (forward)
            double speed = 1.5;
            double changeX = Math.cos(Math.toRadians(enemy.getRotation())) * speed;
            double changeY = Math.sin(Math.toRadians(enemy.getRotation())) * speed;
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

            // Enemy screen wrapping
            if (enemy.getX() < 0) enemy.setX(gameData.getDisplayWidth());
            if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(0);
            if (enemy.getY() < 0) enemy.setY(gameData.getDisplayHeight());
            if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(0);

            // Random shooting logic
            double shootingProbability = 0.05;
            if (random.nextDouble() < shootingProbability)
            {
                if (!bulletSPIs.isEmpty())
                {
                    shootBullet(enemy, gameData, world);
                } else {
                }
            }
        }
    }

    /**
     * Fires a bullet from the enemy.
     * @param enemy The enemy entity firing the bullet.
     * @param gameData GameData object.
     * @param world World object.
     */
    private void shootBullet(Entity enemy, GameData gameData, World world)
    {
        getBulletSPI().ifPresent(
                spi -> world.addEntity(spi.createBullet(enemy, gameData))
        );
    }

    /**
     * Helper method to retrieve the first available BulletSPI.
     * @return An Optional containing the first BulletSPI found, or empty if none.
     */
    private Optional<BulletSPI> getBulletSPI() {
        return bulletSPIs.stream().findFirst();
    }
}