import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Asteroid
{
    requires Common;
    requires CommonAsteroids;
    provides IGamePluginService with dk.sdu.mmmi.cbse.asteroid.AsteroidPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.asteroid.AsteroidProcessor;
    exports dk.sdu.mmmi.cbse.asteroid;
    // Ingen 'uses', da Asteroids modulet ikke forbruger andre services (udover det den har direkte afhængighed til)
    // Ingen 'exports' af interne pakker.
}