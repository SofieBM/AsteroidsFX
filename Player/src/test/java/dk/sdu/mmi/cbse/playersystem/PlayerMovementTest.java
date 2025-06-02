package dk.sdu.mmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerMovementTest
{
    private PlayerControlSystem playerControlSystem;
    private GameData gameData;
    private World world;
    private Player player;
    private GameKeys gameKeys;

    @Mock
    private BulletSPI mockedBulletSPI;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);

        gameData = new GameData();
        gameData.setDisplayWidth(800);
        gameData.setDisplayHeight(600);
        gameKeys = gameData.getKeys();

        world = new World();
        player = new Player();
        player.setX((double) gameData.getDisplayWidth() / 2);
        player.setY((double) gameData.getDisplayHeight() / 2);
        player.setRotation(0.0);
        world.addEntity(player);

        playerControlSystem = new PlayerControlSystem(List.of(mockedBulletSPI));

        when(mockedBulletSPI.createBullet(any(Entity.class), any(GameData.class))).thenReturn(new Entity());
    }

    @Test
    @DisplayName("Player should rotate right when RIGHT key is pressed")
    void testPlayerRotatesRight()
    {
        double initialRotation = player.getRotation();
        gameKeys.setKey(GameKeys.RIGHT, true);
        playerControlSystem.process(gameData, world);
        assertEquals(initialRotation + 5.0, player.getRotation(), 0.001, "Player rotation should increase by 5");
        playerControlSystem.process(gameData, world); // Process again to check cumulative effect
        assertEquals(initialRotation + 10.0, player.getRotation(), 0.001, "Player rotation should increase by 5 again");
    }

    @Test
    @DisplayName("Player should rotate left when LEFT key is pressed")
    void testPlayerRotaesLeft()
    {
        double initialRotation = player.getRotation();
        gameKeys.setKey(GameKeys.LEFT, true);
        playerControlSystem.process(gameData, world);
        assertEquals(initialRotation - 5.0, player.getRotation(), 0.001, "Player rotation should decrease by 5");
    }

    @Test
    @DisplayName("Player should move forward when UP key is pressed (facing right)")
    void testPlayerMovesForwardFacingRight()
    {
        double initialX = player.getX();
        double initialY = player.getY();
        player.setRotation(0.0);
        gameKeys.setKey(GameKeys.UP, true);
        playerControlSystem.process(gameData, world);
        assertEquals(initialX + 1.0, player.getX(), 0.001, "Player X position should increase by 1.0");
        assertEquals(initialY, player.getY(), 0.001, "Player Y position should remain the same");
    }

    @Test
    @DisplayName("Player should move forward when UP key is pressed (facing up)")
    void testPlayerMovesForwardFacingUp()
    {
        double initialX = player.getX();
        double initialY = player.getY();
        player.setRotation(90.0);
        gameKeys.setKey(GameKeys.UP, true);
        playerControlSystem.process(gameData, world);
        assertEquals(initialX, player.getX(), 0.001, "Player X position should remain the same");
        assertEquals(initialY + 1.0, player.getY(), 0.001, "Player Y position should increase by 1.0");
    }

    @Test
    @DisplayName("Player should not move or rotate when no keys are pressed")
    void testPlayerStaysStillWithoutInput()
    {
        double initialX = player.getX();
        double initialY = player.getY();
        double initialRotation = player.getRotation();
        gameKeys.setKey(GameKeys.UP, false);
        gameKeys.setKey(GameKeys.LEFT, false);
        gameKeys.setKey(GameKeys.RIGHT, false);
        playerControlSystem.process(gameData, world);
        assertEquals(initialX, player.getX(), "Player X position should not change");
        assertEquals(initialY, player.getY(), "Player Y position should not change");
        assertEquals(initialRotation, player.getRotation(), "Player rotation should not change");
    }

    @Test
    @DisplayName("Player should wrap around screen horizontally if X goes out of bounds")
    void testPlayerWrapsHorizontally()
    {
        // For X > displayWidth
        player.setX(gameData.getDisplayWidth() + 1);
        playerControlSystem.process(gameData, world);
        // player.setX(0) in your current code
        assertEquals(0.0, player.getX(), "Player X should wrap to 0 when exceeding displayWidth");

        // For X < 0
        player.setX(-1);
        playerControlSystem.process(gameData, world);
        // player.setX(gameData.getDisplayWidth()) in your current code
        assertEquals(gameData.getDisplayWidth(), player.getX(), "Player X should wrap to displayWidth when going below 0");
    }

    @Test
    @DisplayName("Player should wrap around screen vertically if Y goes out of bounds")
    void testPlayerWrapsVertically()
    {
        // For Y > displayHeight
        player.setY(gameData.getDisplayHeight() + 1);
        player.setX(100); // Keep X constant for this test
        player.setRotation(0); // Keep rotation constant
        playerControlSystem.process(gameData, world);
        // player.setY(0) in your current code
        assertEquals(0.0, player.getY(), "Player Y should wrap to 0 when exceeding displayHeight");

        // For Y < 0
        player.setY(-1);
        player.setX(100); // Keep X constant
        player.setRotation(0); // Keep rotation constant
        playerControlSystem.process(gameData, world);
        // player.setY(gameData.getDisplayHeight()) in your current code
        assertEquals(gameData.getDisplayHeight(), player.getY(), "Player Y should wrap to displayHeight when going below 0");
    }

    @Test
    @DisplayName("BulletSPI should be called when SPACE key is pressed")
    void testBulletSPIIsCalledOnSpacePress()
    {
        gameKeys.setKey(GameKeys.SPACE, true);
        playerControlSystem.process(gameData, world);
        verify(mockedBulletSPI).createBullet(player, gameData);
    }

    @Test
    @DisplayName("BulletSPI should not be called when SPACE key is not pressed")
    void testBulletSPIIsNotCalledWithoutSpacePress()
    {
        gameKeys.setKey(GameKeys.SPACE, false);
        playerControlSystem.process(gameData, world);
        verify(mockedBulletSPI, never()).createBullet(any(Entity.class), any(GameData.class));
    }
}
