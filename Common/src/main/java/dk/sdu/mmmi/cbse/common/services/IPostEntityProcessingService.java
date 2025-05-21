package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService
{
    //Interface-niveau: Beskriv generelt, hvad dette interface repræsenterer. (F.eks., "Interface for services, der udfører handlinger efter alle entiteter er blevet behandlet, typisk til kollisionsdetektion eller oprydning.")

    void process(GameData gameData, World world);
    //Formål: Beskriv, hvad denne metode gør (f.eks., "Håndterer interaktioner mellem spilenheder, såsom kollisioner, og opdaterer spilverdenen baseret på disse interaktioner.").
    //@param gameData: Samme som ovenfor.
    //@param world: Samme som ovenfor.
    //Pre-conditions: (F.eks., "gameData og world må ikke være null. Entiteternes positioner skal være opdateret af IEntityProcessingService services.")
    //Post-conditions: (F.eks., "Kolliderende entiteter er blevet behandlet (f.eks. ødelagt, delt, tildelt skade), og world er opdateret i overensstemmelse hermed.")
}
