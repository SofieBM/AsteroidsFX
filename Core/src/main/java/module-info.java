module Core {
    requires Common;
    requires CommonBullet;    
    requires javafx.graphics;   
    requires spring.context;
    requires spring.core;
    requires spring.beans;

    requires Player; // Kræv Player-modulet
    requires Asteroid; // Kræv Asteroid-modulet
    requires Bullet; // Kræv Bullet-modulet
    requires Collision;
    requires Enemy;

    exports dk.sdu.mmmi.cbse.main;
    opens dk.sdu.mmmi.cbse.main to javafx.graphics,spring.core;
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
}


