package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntityProcessingService {
    //Interface-niveau: Beskriv generelt, hvad dette interface repræsenterer. (F.eks., "Interface for services, der behandler spilenheders logik hvert spil-tick.")

    /**
     *
     *
     *
     * @param gameData
     * @param world
     * @throws
     */
    void process(GameData gameData, World world);
    //Formål: Beskriv, hvad denne metode gør (f.eks., "Opdaterer tilstand for spilenheder (f.eks. position, hastighed, orientering) baseret på spillets logik og input.").
    //@param gameData: Samme som ovenfor.
    //@param world: Samme som ovenfor.
    //Pre-conditions: (F.eks., "gameData og world må ikke være null. world skal indeholde de entiteter, der skal behandles af denne service.")
    //Post-conditions: (F.eks., "Entiteternes tilstand er opdateret, og eventuelle nye entiteter (f.eks. skud) er tilføjet, mens ødelagte entiteter (f.eks. fjerne skud) er markeret til fjernelse.")
}
