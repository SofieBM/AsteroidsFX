package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;


public class EnemyPlugin implements IGamePluginService

{
    private Entity enemy;
    private Random rnd = new Random();

    public EnemyPlugin()
    {

    }

    @Override
    public void start(GameData gameData, World world)
    {
    // Opreter et Enemy spaceship, når spillet starter
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }


    private Entity createEnemyShip(GameData gameData)
    {
        Entity enemyShip = new Entity();

        enemyShip.setPolygonCoordinates(-7,-7,10,0,-7,7);

        // det her gør at fjenden bliver tilføjet et tilfældigt sted på skærmen
        enemyShip.setX(rnd.nextInt(gameData.getDisplayWidth()));
        enemyShip.setY(rnd.nextInt(gameData.getDisplayHeight()));
        enemyShip.setRadius(7);
        return enemyShip;
    }


    @Override
    public void stop(GameData gameData, World world)
    {
        // det her gør så enemy spaceshipet bliver fjernet når plugin'et stopper
        world.removeEntity(enemy);
    }
}