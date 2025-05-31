import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Asteroid {
    requires Common;
    requires CommonAsteroids;
    requires spring.context;
    requires spring.beans;
    //provides IGamePluginService with dk.sdu.mmmi.cbse.asteroid.AsteroidPlugin;
    //provides IEntityProcessingService with dk.sdu.mmmi.cbse.asteroid.AsteroidProcessor;
    exports dk.sdu.mmmi.cbse.asteroid;
}