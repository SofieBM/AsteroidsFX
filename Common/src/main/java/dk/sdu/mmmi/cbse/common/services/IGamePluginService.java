package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService
{
    //Interface-niveau: Beskriv generelt, hvad dette interface repræsenterer. (F.eks., "Interface for spilplugins, der håndterer opstart og nedlukning af spilkomponenter.")

    void start(GameData gameData, World world);
    //Formål: Beskriv, hvad denne metode gør (f.eks., "Initialiserer og tilføjer spilenheder til spilverdenen ved spillets start eller når plugin'en aktiveres.").
    //@param gameData: Beskriv GameData objektet (f.eks., "Indeholder global spildata som skærmdimensioner og deltatid.").
    //@param world: Beskriv World objektet (f.eks., "Representerer spilverdenen og indeholder alle spilenheder.").
    //Pre-conditions: Hvad skal være sandt, før denne metode kaldes? (F.eks., " gameData og world må ikke være null.")
    //Post-conditions: Hvad er sandt efter denne metode er udført? (F.eks., "Nye entiteter er tilføjet til world.").

    void stop(GameData gameData, World world);
    //Formål: Beskriv, hvad denne metode gør (f.eks., "Fjerner spilenheder relateret til dette plugin fra spilverdenen ved spillets afslutning eller når plugin'en deaktiveres.").
    //@param gameData: Samme som ovenfor.
    //@param world: Samme som ovenfor.
    //Pre-conditions: Hvad skal være sandt, før denne metode kaldes? (F.eks., "gameData og world må ikke være null.")
    //Post-conditions: Hvad er sandt efter denne metode er udført? (F.eks., "Entiteter relateret til dette plugin er fjernet fra world.").
}
