package dk.sdu.mmmi.cbse;



import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;



import java.util.Collection;

import java.util.Random;

import java.util.ServiceLoader;

import java.util.stream.Collectors;



public class EnemyControlSystem implements IEntityProcessingService

{

    private final Random random = new Random();



    @Override

    public void process(GameData gameData, World world)
    {
        for (Entity enemy : world.getEntities(Enemy.class))
        {
            int[] rotationValues = {-5,0,5}; // det her ændre enemy spaceships rotation

            // dette gør at det bevæger sig fremad
            double speed = 1.5;
            double changeX = Math.cos(Math.toRadians(enemy.getRotation())) * speed;
            double changeY = Math.sin(Math.toRadians(enemy.getRotation())) * speed;
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);


            // det her gør at fjenden wrappes rundt om skærmen
            if (enemy.getX() < 0) enemy.setX(1);
            if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(gameData.getDisplayWidth() - 1);
            if (enemy.getY() < 0) enemy.setY(1);
            if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(gameData.getDisplayHeight() - 1);


            //det her gør at der er en tilfældig skydelogik
            double shootingProbability = 0.35;
            if (random.nextDouble() < shootingProbability)
            {
                shootBullet(enemy, gameData, world);
            }
        }
    }

    private void shootBullet(Entity enemy, GameData gameData, World world)
    {
        getBulletSPIs().stream().findFirst().ifPresent(
                spi -> world.addEntity(spi.createBullet(enemy, gameData))
        );
    }


    private Collection<?extends BulletSPI> getBulletSPIs()
    {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }
}