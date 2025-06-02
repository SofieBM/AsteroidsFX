package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.springframework.stereotype.Component; // ADD THIS IMPORT

@Component
public class BulletPlugin implements IGamePluginService
{

    private Entity bullet;

    @Override
    public void start(GameData gameData, World world)
    {
    }

    @Override
    public void stop(GameData gameData, World world)
    {
        for (Entity bullet : world.getEntities(Bullet.class))
        {
            world.removeEntity(bullet);
        }
    }
}
