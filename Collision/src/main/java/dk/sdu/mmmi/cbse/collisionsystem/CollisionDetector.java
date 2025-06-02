package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CollisionDetector implements IPostEntityProcessingService
{
    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world)
    {
        Set<Entity> entitiesToRemove = new HashSet<>();

        for (Entity entity1 : world.getEntities())
        {
            for (Entity entity2 : world.getEntities())
            {
                if (entity1.getID().equals(entity2.getID()))
                {
                    continue;
                }

                if (this.collides(entity1, entity2))
                {
                    entitiesToRemove.add(entity1);
                    entitiesToRemove.add(entity2);
                }
            }
        }
        // Fjern alle markerede entiteter efter iterationen
        for (Entity entity : entitiesToRemove) {
            world.removeEntity(entity);
        }
    }

    public Boolean collides(Entity entity1, Entity entity2)
    {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }
}
