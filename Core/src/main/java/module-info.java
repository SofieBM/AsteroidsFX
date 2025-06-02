module Core
{
    requires CommonBullet;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.base;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.web;
    requires com.fasterxml.jackson.databind;

    requires Player;
    requires Asteroid;
    requires Bullet;
    requires Collision;
    requires Enemy;
    requires ScoreClient;
    requires CommonAsteroids;
    requires Common;

    exports dk.sdu.mmmi.cbse.main;
    opens dk.sdu.mmmi.cbse.main to javafx.graphics, spring.core;
    //uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    //uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    //uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
    //uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    //uses dk.sdu.mmmi.cbse.common.services.IScoreService;
}


