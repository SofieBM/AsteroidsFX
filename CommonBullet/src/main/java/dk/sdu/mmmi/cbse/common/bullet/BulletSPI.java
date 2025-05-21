package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author corfixen
 */
public interface BulletSPI
{
    //Interface-niveau: Beskriv generelt, hvad dette interface repræsenterer.
    //F.eks.: "Service Provider Interface (SPI) for oprettelse af kugleenheder. Dette interface tillader andre moduler (f.eks. Player og Enemy) at anmode om oprettelse af en kugle-entitet uden at have en direkte kompileringsafhængighed til den specifikke implementering af en kugle-entitet."

    Entity createBullet(Entity e, GameData gameData);
    //Formål: Beskriv, hvad denne metode gør. F.eks.: "Opretter og returnerer en ny kugle-entitet, der er affyret fra en given 'launcher' entitet."
    //@param launcher: Beskriv launcher parameteren. F.eks.: "Den entitet (f.eks. Player eller Enemy), der affyrer kuglen. Dette bruges til at bestemme startposition, retning og eventuelt til at forhindre kollision med affyreren selv i de første øjeblikke."
    //@param gameData: Beskriv GameData objektet. F.eks.: "Indeholder globale spildata, såsom deltatid og skærmdimensioner, som kan bruges til at initialisere kuglens bevægelse."
    //@return: Beskriv returværdien. F.eks.: "En ny Entity instans, der repræsenterer den oprettede kugle. Returnerer null, hvis kuglen ikke kunne oprettes."
    //Pre-conditions: Hvad skal være sandt, før denne metode kaldes? F.eks.: "launcher og gameData må ikke være null. launcher entiteten skal have en gyldig position og rotation."
    //Post-conditions: Hvad er sandt efter denne metode er udført? F.m.: "En ny kugle-entitet er oprettet med en initial position, hastighed og retning baseret på launcher entitetens data. Den returnerede entitet er klar til at blive tilføjet til spilverdenen."
}
