open module Player.test
{
    requires Player;
    requires Common;
    requires CommonBullet;
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;
    requires org.mockito;
    requires org.mockito.junit.jupiter;
    requires java.management;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
}