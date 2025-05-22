package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.List;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author jcs
 */
@Configuration
class ModuleConfig
{
    
    public ModuleConfig()
    {

    }

    @Bean
    public Game game(List<IGamePluginService> gamePluginServices,
                     List<IEntityProcessingService> entityProcessingServiceList,
                     List<IPostEntityProcessingService> postEntityProcessingServices,
                     List<BulletSPI> bulletSPIs)
    {
        return new Game(gamePluginServices, entityProcessingServiceList, postEntityProcessingServices, bulletSPIs);
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServiceList()
    {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    @Bean
    public List<IGamePluginService> gamePluginServices()
    {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices()
    {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    @Bean
    public List<BulletSPI> bulletSPIs()
    {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
