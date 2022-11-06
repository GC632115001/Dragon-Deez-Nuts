//import controller.DrawingLoop;
//import controller.GameLoop;
//import model.Character;
//import javafx.scene.input.KeyCode;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
//import view.Platform;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//
//@RunWith(JfxRunner.class)
//public class CharacterTest {
//    private Character floatingCharacter;
//    private ArrayList<Character> characterListUnderTest;
//    private Platform platformUnderTest;
//    private GameLoop gameLoopUnderTest;
//    private DrawingLoop drawingLoopUnderTest;
//    private Method updateMethod;
//    private Method redrawMethod;
//
//    @Before
//    public void setup() {
//        floatingCharacter = new Character(30, 30, 0, 0, KeyCode.A, KeyCode.D, KeyCode.W);
//        characterListUnderTest = new ArrayList<Character>();
//        characterListUnderTest.add(floatingCharacter);
//        platformUnderTest = new Platform();
//        gameLoopUnderTest = new GameLoop(platformUnderTest);
//        drawingLoopUnderTest = new DrawingLoop(platformUnderTest);
//        try {
//            updateMethod = GameLoop.class.getDeclaredMethod("update", ArrayList.class);
//            redrawMethod = DrawingLoop.class.getDeclaredMethod("paint", ArrayList.class);
//            updateMethod.setAccessible(true);
//            redrawMethod.setAccessible(true);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//            updateMethod = null;
//            redrawMethod = null;
//        }
//    }
//
//    @Test
//    public void characterInitialValuesShouldMatchConstructorArguments() {
//        assertEquals("Initial x", 30, floatingCharacter.getX(), 0);
//        assertEquals("Initial y", 30, floatingCharacter.getY(), 0);
//        assertEquals("Offset x", 0, floatingCharacter.getOffsetX(), 0.0);
//        assertEquals("Offset y", 0, floatingCharacter.getOffsetY(), 0.0);
//        assertEquals("Left key", KeyCode.A, floatingCharacter.getLeftKey());
//        assertEquals("Right key", KeyCode.D, floatingCharacter.getRightKey());
//        assertEquals("Up key", KeyCode.W, floatingCharacter.getUpKey());
//    }
//
//    @Test
//    public void characterShouldMoveToTheLeftAfterTheLeftKeyIsPressed() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
//        Character characterUnderTest = characterListUnderTest.get(0);
//        int startX = characterUnderTest.getX();
//        platformUnderTest.getKeys().add(KeyCode.A);
//        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
//        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
//        Field isMoveLeft = characterUnderTest.getClass().getDeclaredField("isMoveLeft");
//        isMoveLeft.setAccessible(true);
//        assertTrue("Controller: Left key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.A));
//        assertTrue("Model: Character moving left state is set", isMoveLeft.getBoolean(characterUnderTest));
//        assertTrue("View: Character is moving left", characterUnderTest.getX() < startX);
//    }
//
//    @Test
//    public void characterShouldMoveToTheRightAfterTheRightKeyIsPressed() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
//        Character characterUnderTest = characterListUnderTest.get(0);
//        int startX = characterUnderTest.getX();
//        platformUnderTest.getKeys().add(KeyCode.D);
//        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
//        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
//        Field isMoveRight = characterUnderTest.getClass().getDeclaredField("isMoveRight");
//        isMoveRight.setAccessible(true);
//        assertTrue("Controller: Right key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.D));
//        assertTrue("Model: Character moving right state is set", isMoveRight.getBoolean(characterUnderTest));
//        assertTrue("View: Character is moving right", characterUnderTest.getX() > startX);
//    }
//    @Test
//    public void characterShouldBeingOnTheGroundAfterKeyWIsPressed() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
//        Character characterUnderTest = characterListUnderTest.get(0);
//        int startY = characterUnderTest.getY();
//        platformUnderTest.getKeys().add(KeyCode.W);
//        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
//        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
//        Field jumping = characterUnderTest.getClass().getDeclaredField("jumping");
//        jumping.setAccessible(true);
//        assertTrue("Controller: Up key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.W));
//        assertTrue("Model: Character moving Up state is set", jumping.getBoolean(characterUnderTest));
//        assertTrue("View: Character is moving up", characterUnderTest.getY() > startY);
//    }
//
////@Test
////    public void characterShouldNotBeingOnTheGroundAfterKeyWIsPressed() throws IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, NoSuchFieldException {
////        Character characterUnderTest = characterListUnderTest.get(0);
////        int startY = characterUnderTest.getY();
////        platformUnderTest.getKeys().add(KeyCode.W);
////        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
////        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
////        Field jumping = characterUnderTest.getClass().getDeclaredField("canJump");
////        jumping.setAccessible(true);
////        assertTrue("Controller: Up key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.W));
////        assertTrue("Model: Character moving Up state is set", jumping.getBoolean(characterUnderTest));
////        assertTrue("View: Character is moving up", characterUnderTest.getY() > startY);
////    }
//}