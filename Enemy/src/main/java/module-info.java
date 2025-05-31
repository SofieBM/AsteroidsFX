module Enemy
{
    exports dk.sdu.mmmi.cbse;
    requires Common;
    requires CommonBullet;
    requires spring.context;
    requires spring.beans;
    //uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    //provides dk.sdu.mmmi.cbse.common.services.IGamePluginService with dk.sdu.mmmi.cbse.EnemyPlugin;
    //provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService with dk.sdu.mmmi.cbse.EnemyControlSystem;
}