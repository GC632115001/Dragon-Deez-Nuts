import controller.DrawingLoop;
import controller.GameLoop;
import model.Character;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import view.Platform;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class CharacterTest2 {
    private Character floatingCharacter0, floatingCharacter1;
    private ArrayList<Character> characterListUnderTest;
    private Platform platformUnderTest;
    private GameLoop gameLoopUnderTest;
    private DrawingLoop drawingLoopUnderTest;
    private Method updateMethod;
    private Method redrawMethod;

    @Before
    public void setup() {
        floatingCharacter0 = new Character(30, platformUnderTest.GROUND - floatingCharacter0.CHARACTER_HEIGHT, 0, 0, KeyCode.A, KeyCode.D, KeyCode.W);
        floatingCharacter1 = new Character(Platform.WIDTH - 60, 30, 0, 96, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP);
        characterListUnderTest = new ArrayList<Character>();
        characterListUnderTest.add(floatingCharacter0);
        characterListUnderTest.add(floatingCharacter1);
        platformUnderTest = new Platform();
        gameLoopUnderTest = new GameLoop(platformUnderTest);
        drawingLoopUnderTest = new DrawingLoop(platformUnderTest);
        try {
            updateMethod = GameLoop.class.getDeclaredMethod("update", ArrayList.class);
            redrawMethod = DrawingLoop.class.getDeclaredMethod("paint", ArrayList.class);
            updateMethod.setAccessible(true);
            redrawMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            updateMethod = null;
            redrawMethod = null;
        }
    }

    @Test
    public void characterInitialValuesShouldMatchConstructorArguments() {
        assertEquals("Initial x", 30, floatingCharacter0.getX(), 0);
        assertEquals("Initial y", platformUnderTest.GROUND - floatingCharacter0.CHARACTER_HEIGHT, floatingCharacter0.getY(), 0);
        assertEquals("Offset x", 0, floatingCharacter0.getOffsetX(), 0.0);
        assertEquals("Offset y", 0, floatingCharacter0.getOffsetY(), 0.0);
        assertEquals("Left key", KeyCode.A, floatingCharacter0.getLeftKey());
        assertEquals("Right key", KeyCode.D, floatingCharacter0.getRightKey());
        assertEquals("Up key", KeyCode.W, floatingCharacter0.getUpKey());

        assertEquals("Initial x", Platform.WIDTH - 60, floatingCharacter1.getX(), 0);
        assertEquals("Initial y", 30, floatingCharacter1.getY(), 0);
        assertEquals("Offset x", 0, floatingCharacter1.getOffsetX(), 0.0);
        assertEquals("Offset y", 96, floatingCharacter1.getOffsetY(), 0.0);
        assertEquals("Left key", KeyCode.LEFT, floatingCharacter1.getLeftKey());
        assertEquals("Right key", KeyCode.RIGHT, floatingCharacter1.getRightKey());
        assertEquals("Up key", KeyCode.UP, floatingCharacter1.getUpKey());
    }

    @Test
    public void characterShouldMoveToTheLeftAfterTheLeftKeyIsPressed() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
        Character characterUnderTest = characterListUnderTest.get(0);
        int startX = characterUnderTest.getX();
        platformUnderTest.getKeys().add(KeyCode.A);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
        Field isMoveLeft = characterUnderTest.getClass().getDeclaredField("isMoveLeft");
        isMoveLeft.setAccessible(true);
        assertTrue("Controller: Left key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.A));
        assertTrue("Model: Character moving left state is set", isMoveLeft.getBoolean(characterUnderTest));
        assertTrue("View: Character is moving left", characterUnderTest.getX() < startX);
    }

    @Test
    public void characterShouldMoveToTheRightAfterTheRightKeyIsPressed() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
        Character characterUnderTest = characterListUnderTest.get(0);
        int startX = characterUnderTest.getX();
        platformUnderTest.getKeys().add(KeyCode.D);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
        Field isMoveRight = characterUnderTest.getClass().getDeclaredField("isMoveRight");
        isMoveRight.setAccessible(true);
        assertTrue("Controller: Right key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.D));
        assertTrue("Model: Character moving right state is set", isMoveRight.getBoolean(characterUnderTest));
        assertTrue("View: Character is moving right", characterUnderTest.getX() > startX);
    }

    @Test
    public void characterShouldJumpAfterTheUpKeyIsPressedOnTheGround() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
        Character characterUnderTest = characterListUnderTest.get(0);
        platformUnderTest.getKeys().add(KeyCode.W);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
        Field falling = characterUnderTest.getClass().getDeclaredField("falling");
        Field canJump = characterUnderTest.getClass().getDeclaredField("canJump");
        Field jumping = characterUnderTest.getClass().getDeclaredField("jumping");
        falling.setAccessible(true);
        canJump.setAccessible(true);
        jumping.setAccessible(true);
        assertTrue("Controller: Right key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.W));
        assertFalse("Model: Character can jump state is set", canJump.getBoolean(characterUnderTest));
        assertFalse("Model: Character jumping state is set", jumping.getBoolean(characterUnderTest));
        assertTrue("Model: Character falling state is set", falling.getBoolean(characterUnderTest));
        assertTrue("View: Character is jump", characterUnderTest.getY() > platformUnderTest.GROUND - characterUnderTest.CHARACTER_HEIGHT);
    }

    @Test
    public void characterShouldJumpAfterTheUpKeyIsPressedInAir() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
        Character characterUnderTest = characterListUnderTest.get(1);
        platformUnderTest.getKeys().add(KeyCode.UP);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
        Field falling = characterUnderTest.getClass().getDeclaredField("falling");
        Field canJump = characterUnderTest.getClass().getDeclaredField("canJump");
        Field jumping = characterUnderTest.getClass().getDeclaredField("jumping");
        falling.setAccessible(true);
        canJump.setAccessible(true);
        jumping.setAccessible(true);
        assertTrue("Controller: Right key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.UP));
        assertFalse("Model: Character can jump state is set", canJump.getBoolean(characterUnderTest));
        assertFalse("Model: Character jumping state is set", jumping.getBoolean(characterUnderTest));
        assertTrue("Model: Character falling state is set", falling.getBoolean(characterUnderTest));
        assertTrue("View: Character is falling", characterUnderTest.getY() < platformUnderTest.GROUND + characterUnderTest.CHARACTER_HEIGHT);
    }

    @Test
    public void characterIsHittingABorder() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
        Character characterUnderTest0 = characterListUnderTest.get(0);
        platformUnderTest.getKeys().add(KeyCode.A);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
        Field isMoveLeft = characterUnderTest0.getClass().getDeclaredField("isMoveLeft");
        isMoveLeft.setAccessible(true);
        assertTrue("Controller: Left key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.A));
        assertTrue("Model: Character moving left state is set", isMoveLeft.getBoolean(characterUnderTest0));

        if (characterUnderTest0.getX() <= 0) {
            assertTrue("View: Character is moving left border", characterUnderTest0.getX() == 0);
            System.out.println(characterUnderTest0.getX());
        }

        Character characterUnderTest1 = characterListUnderTest.get(1);
        platformUnderTest.getKeys().add(KeyCode.RIGHT);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
        Field isMoveLeft1 = characterUnderTest1.getClass().getDeclaredField("isMoveRight");
        isMoveLeft1.setAccessible(true);
        assertTrue("Controller: Right key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.A));
        assertTrue("Model: Character moving right state is set", isMoveLeft1.getBoolean(characterUnderTest1));

        if (characterUnderTest1.getX() >= platformUnderTest.WIDTH) {
            assertTrue("View: Character is moving right border", characterUnderTest1.getX() == Platform.WIDTH - characterUnderTest1.CHARACTER_WIDTH);
            System.out.println(characterUnderTest1.getX());
        }
    }

    @Test
    public void characterIsCollidedWithTheOtherCharacter() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
        Character characterUnderTest0 = characterListUnderTest.get(0);
        platformUnderTest.getKeys().add(KeyCode.D);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
        Field isMoveRight = characterUnderTest0.getClass().getDeclaredField("isMoveRight");
        isMoveRight.setAccessible(true);

        Character characterUnderTest1 = characterListUnderTest.get(1);
        platformUnderTest.getKeys().add(KeyCode.LEFT);
//        updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
//        redrawMethod.invoke(drawingLoopUnderTest,characterListUnderTest);
        Field isMoveLeft = characterUnderTest1.getClass().getDeclaredField("isMoveLeft");
        isMoveLeft.setAccessible(true);

        if (characterUnderTest1.getX() == characterUnderTest0.getX() - characterUnderTest1.CHARACTER_WIDTH + 1 && characterUnderTest0.getX() == characterUnderTest1.getX() - characterUnderTest0.CHARACTER_WIDTH - 1) {
            assertFalse("Character 1 collided Character 2", isMoveRight.getBoolean(characterUnderTest0));
            assertFalse("Character 2 collided Character 1", isMoveLeft.getBoolean(characterUnderTest1));
        }
    }

    @Test
    public void characterStompOtherCharacter() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
        Character characterUnderTest0 = characterListUnderTest.get(0);
        Character characterUnderTest1 = characterListUnderTest.get(1);
        characterUnderTest0.setTranslateX(Platform.WIDTH - 60);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);

        if (characterUnderTest1.getY() <= characterUnderTest0.getY() && Math.abs(characterUnderTest1.getY() - characterUnderTest0.getY()) <= characterUnderTest1.CHARACTER_HEIGHT + 1)
            assertTrue("Character2 stomp character1 check character2 score", characterUnderTest1.getScore() > 0);
    }

    @Test
    public void characterIsStompByOtherCharacter() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
        Character characterUnderTest0 = characterListUnderTest.get(0);
//        Character characterUnderTest1 = characterListUnderTest.get(1);
        int startX = characterUnderTest0.getX();
        characterUnderTest0.setTranslateX(Platform.WIDTH - 60);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);

        assertTrue("Character2 stomp character1 check character1 respawn", characterUnderTest0.getX() == startX);
    }
}