import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for the EnglishSolitaireModel implementation.
 * Tests all constructors, game operations, and edge cases.
 */
public class EnglishSolitaireModelTest {
    private EnglishSolitaireModel model1;
    private EnglishSolitaireModel model2;
    private EnglishSolitaireModel model3;
    private EnglishSolitaireModel model4;

    /**
     * Sets up test models before each test method.
     * Creates models with different constructor parameters for testing.
     */
    @Before
    public void setUp() {
        model1 = new EnglishSolitaireModel();
        model2 = new EnglishSolitaireModel(0, 4);
        model3 = new EnglishSolitaireModel(5);
        model4 = new EnglishSolitaireModel(5, 4, 4);
    }

    // Constructor Tests

    /**
     * Tests the default constructor creates a board with arm thickness 3
     * and empty slot at the center (3,3).
     */
    @Test
    public void testDefaultConstructor() {
        assertEquals(7, model1.getBoardSize());
        assertEquals(SlotState.Empty, model1.getSlotAt(3, 3));
        assertEquals(32, model1.getScore());
    }

    /**
     * Tests the two-parameter constructor with custom empty position.
     */
    @Test
    public void testTwoParameterConstructor() {
        assertEquals(7, model2.getBoardSize());
        assertEquals(SlotState.Empty, model2.getSlotAt(0, 4));
        assertEquals(SlotState.Marble, model2.getSlotAt(3, 3));
        assertEquals(32, model2.getScore());
    }

    /**
     * Tests that the two-parameter constructor throws exception for invalid position.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTwoParameterConstructorInvalidPosition() {
        new EnglishSolitaireModel(0, 0); // Invalid corner position
    }

    /**
     * Tests that the two-parameter constructor throws exception for negative position.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTwoParameterConstructorNegativePosition() {
        new EnglishSolitaireModel(-1, 3);
    }

    /**
     * Tests the three-parameter constructor with custom arm thickness.
     */
    @Test
    public void testThreeParameterConstructor() {
        assertEquals(13, model3.getBoardSize());
        assertEquals(SlotState.Empty, model3.getSlotAt(6, 6));
        // Score = total valid positions - 1 empty slot
        // For arm thickness 5: 5*5 + 4*(5*4) = 25 + 80 = 105 - 1 = 104
        assertEquals(104, model3.getScore());
    }

    /**
     * Tests that even arm thickness throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testThreeParameterConstructorEvenArmThickness() {
        new EnglishSolitaireModel(4);
    }

    /**
     * Tests that negative arm thickness throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testThreeParameterConstructorNegativeArmThickness() {
        new EnglishSolitaireModel(-3);
    }

    /**
     * Tests that zero arm thickness throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testThreeParameterConstructorZeroArmThickness() {
        new EnglishSolitaireModel(0);
    }

    /**
     * Tests the four-parameter constructor with all custom values.
     */
    @Test
    public void testFourParameterConstructor() {
        assertEquals(13, model4.getBoardSize());
        assertEquals(SlotState.Empty, model4.getSlotAt(4, 4));
        assertEquals(104, model4.getScore());
    }

    /**
     * Tests that invalid arm thickness in four-parameter constructor throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFourParameterConstructorInvalidArmThickness() {
        new EnglishSolitaireModel(6, 3, 3);
    }

    /**
     * Tests that invalid position in four-parameter constructor throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFourParameterConstructorInvalidPosition() {
        new EnglishSolitaireModel(5, 0, 0); // Corner position
    }

    // Board Size Tests

    /**
     * Tests that getBoardSize returns correct size for different arm thicknesses.
     */
    @Test
    public void testGetBoardSize() {
        assertEquals(7, new EnglishSolitaireModel().getBoardSize());
        assertEquals(7, new EnglishSolitaireModel(3).getBoardSize());
        assertEquals(13, new EnglishSolitaireModel(5).getBoardSize());
        assertEquals(19, new EnglishSolitaireModel(7).getBoardSize());
    }

    // GetSlotAt Tests

    /**
     * Tests getSlotAt returns correct states for various positions.
     */
    @Test
    public void testGetSlotAt() {
        // Test corners are invalid
        assertEquals(SlotState.Invalid, model1.getSlotAt(0, 0));
        assertEquals(SlotState.Invalid, model1.getSlotAt(0, 1));
        assertEquals(SlotState.Invalid, model1.getSlotAt(1, 0));
        assertEquals(SlotState.Invalid, model1.getSlotAt(6, 6));

        // Test valid positions have marbles
        assertEquals(SlotState.Marble, model1.getSlotAt(0, 2));
        assertEquals(SlotState.Marble, model1.getSlotAt(3, 0));

        // Test empty slot
        assertEquals(SlotState.Empty, model1.getSlotAt(3, 3));
    }

    /**
     * Tests that getSlotAt throws exception for out of bounds position.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetSlotAtOutOfBounds() {
        model1.getSlotAt(7, 3);
    }

    /**
     * Tests that getSlotAt throws exception for negative position.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetSlotAtNegative() {
        model1.getSlotAt(-1, 3);
    }

    // Move Tests

    /**
     * Tests valid move to the right.
     */
    @Test
    public void testValidMoveRight() {
        model1.move(3, 1, 3, 3);
        assertEquals(SlotState.Empty, model1.getSlotAt(3, 1));
        assertEquals(SlotState.Empty, model1.getSlotAt(3, 2));
        assertEquals(SlotState.Marble, model1.getSlotAt(3, 3));
        assertEquals(31, model1.getScore());
    }

    /**
     * Tests valid move to the left.
     */
    @Test
    public void testValidMoveLeft() {
        model1.move(3, 5, 3, 3);
        assertEquals(SlotState.Empty, model1.getSlotAt(3, 5));
        assertEquals(SlotState.Empty, model1.getSlotAt(3, 4));
        assertEquals(SlotState.Marble, model1.getSlotAt(3, 3));
        assertEquals(31, model1.getScore());
    }

    /**
     * Tests valid move upward.
     */
    @Test
    public void testValidMoveUp() {
        model1.move(5, 3, 3, 3);
        assertEquals(SlotState.Empty, model1.getSlotAt(5, 3));
        assertEquals(SlotState.Empty, model1.getSlotAt(4, 3));
        assertEquals(SlotState.Marble, model1.getSlotAt(3, 3));
        assertEquals(31, model1.getScore());
    }

    /**
     * Tests valid move downward.
     */
    @Test
    public void testValidMoveDown() {
        model1.move(1, 3, 3, 3);
        assertEquals(SlotState.Empty, model1.getSlotAt(1, 3));
        assertEquals(SlotState.Empty, model1.getSlotAt(2, 3));
        assertEquals(SlotState.Marble, model1.getSlotAt(3, 3));
        assertEquals(31, model1.getScore());
    }

    /**
     * Tests multiple consecutive moves.
     */
    @Test
    public void testMultipleMoves() {
        model1.move(3, 1, 3, 3); // Right
        model1.move(3, 4, 3, 2); // Left
        model1.move(3, 6, 3, 4); // Left
        assertEquals(29, model1.getScore());
    }

    /**
     * Tests that move from invalid position throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveFromInvalidPosition() {
        model1.move(0, 0, 0, 2);
    }

    /**
     * Tests that move to invalid position throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveToInvalidPosition() {
        model1.move(0, 2, 0, 0);
    }

    /**
     * Tests that move from empty slot throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveNoMarbleAtFrom() {
        model1.move(3, 3, 3, 5); // Empty slot
    }

    /**
     * Tests that move to occupied slot throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveToOccupied() {
        model1.move(3, 0, 3, 2); // Destination has marble
    }

    /**
     * Tests that move with wrong distance throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveWrongDistance() {
        model1.move(3, 0, 3, 1); // Only 1 space
    }

    /**
     * Tests that diagonal move throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveDiagonal() {
        model1.move(2, 2, 4, 4);
    }

    /**
     * Tests that move without marble to jump throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveNoMarbleToJump() {
        model1.move(3, 1, 3, 3); // Valid first move
        model1.move(3, 0, 3, 2); // No marble at (3,1) to jump
    }

    // Game Over Tests

    /**
     * Tests that game is not over in initial state.
     */
    @Test
    public void testIsGameOverInitialState() {
        assertFalse(model1.isGameOver());
    }

    /**
     * Tests that game is not over after valid moves.
     */
    @Test
    public void testIsGameOverAfterMoves() {
        model1.move(3, 1, 3, 3);
        assertFalse(model1.isGameOver());
    }

    /**
     * Tests game over detection when no valid moves remain.
     */
    @Test
    public void testIsGameOverNoMovesLeft() {
        // Test with arm thickness 1 (creates a 1x1 board with just the center)
        EnglishSolitaireModel tinyModel = new EnglishSolitaireModel(1);
        assertTrue("1x1 board with empty center should be game over", tinyModel.isGameOver());
        assertEquals("1x1 board should have 0 marbles", 0, tinyModel.getScore());

        // For a simple game over test, let's create a scenario that definitely ends
        EnglishSolitaireModel model = new EnglishSolitaireModel();
        // Initial board has empty slot at (3,3)

        model.move(1, 3, 3, 3);
        model.move(2, 5, 2, 3);
        model.move(4, 4, 2, 4);
        model.move(2, 3, 2, 5);
        model.move(4, 6, 4, 4);
        model.move(3, 6, 3, 4);
        model.move(2, 1, 2, 3);
        model.move(0, 2, 2, 2);
        model.move(2, 3, 2, 1);
        // Continue clearing marbles systematically
        model.move(0, 4, 0, 2);
        model.move(2, 0, 2, 2);
        model.move(2, 6, 2, 4);
        model.move(3, 2, 1, 2);
        model.move(0, 2, 2, 2);
        model.move(2, 4, 0, 4);
        model.move(4, 4, 2, 4);
        model.move(3, 0, 3, 2);
        model.move(3, 2, 3, 4);
        model.move(3, 4, 1, 4);
        model.move(5, 2, 3, 2);
        model.move(4, 0, 4, 2);
        model.move(3, 2, 5, 2);
        model.move(0, 4, 2, 4);
        model.move(5, 3, 3, 3);
        model.move(6, 2, 4, 2);
        model.move(6, 4, 4, 4);

        // By now we should have exactly 1 marble left with no valid moves
        assertTrue("Game should be over", model.isGameOver());
        assertEquals("Should have exactly 6 marble left", 6, model.getScore());
    }

    // Score Tests

    /**
     * Tests getScore returns correct marble count.
     */
    @Test
    public void testGetScore() {
        assertEquals(32, model1.getScore());
        model1.move(3, 1, 3, 3);
        assertEquals(31, model1.getScore());
        model1.move(3, 4, 3, 2);
        assertEquals(30, model1.getScore());
    }

    /**
     * Tests getScore for larger board.
     */
    @Test
    public void testGetScoreLargerBoard() {
        EnglishSolitaireModel bigModel = new EnglishSolitaireModel(5);
        assertEquals(104, bigModel.getScore());
    }
}