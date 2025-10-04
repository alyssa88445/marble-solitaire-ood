package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for TriangleSolitaireModel.
 */
public class TriangleSolitaireModelTest {
    private MarbleSolitaireModel model;

    @Before
    public void setUp() {
        model = new TriangleSolitaireModel();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(5, model.getBoardSize());
        assertEquals(SlotState.Empty, model.getSlotAt(0, 0));
        assertEquals(14, model.getScore()); // 15 positions - 1 empty
    }

    @Test
    public void testSingleParameterConstructor() {
        MarbleSolitaireModel model7 = new TriangleSolitaireModel(7);
        assertEquals(7, model7.getBoardSize());
        assertEquals(SlotState.Empty, model7.getSlotAt(0, 0));
        assertEquals(27, model7.getScore()); // (7*8)/2 - 1 = 28 - 1 = 27
    }

    @Test
    public void testTwoParameterConstructor() {
        MarbleSolitaireModel modelCustom = new TriangleSolitaireModel(2, 1);
        assertEquals(5, modelCustom.getBoardSize());
        assertEquals(SlotState.Empty, modelCustom.getSlotAt(2, 1));
        assertEquals(SlotState.Marble, modelCustom.getSlotAt(0, 0));
    }

    @Test
    public void testThreeParameterConstructor() {
        MarbleSolitaireModel modelFull = new TriangleSolitaireModel(6, 3, 2);
        assertEquals(6, modelFull.getBoardSize());
        assertEquals(SlotState.Empty, modelFull.getSlotAt(3, 2));
        assertEquals(20, modelFull.getScore()); // (6*7)/2 - 1 = 21 - 1 = 20
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDimensions() {
        new TriangleSolitaireModel(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDimensions() {
        new TriangleSolitaireModel(-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmptyPosition() {
        new TriangleSolitaireModel(0, 1); // Row 0 only has position 0
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmptyPositionBeyondRow() {
        new TriangleSolitaireModel(3, 4); // Row 3 only has positions 0-3
    }

    @Test
    public void testTriangularShape() {
        // Test valid positions for each row
        // Row 0: only column 0 is valid
        assertEquals(SlotState.Empty, model.getSlotAt(0, 0)); // Empty in default
        assertEquals(SlotState.Invalid, model.getSlotAt(0, 1));

        // Row 1: columns 0-1 are valid
        assertEquals(SlotState.Marble, model.getSlotAt(1, 0));
        assertEquals(SlotState.Marble, model.getSlotAt(1, 1));
        assertEquals(SlotState.Invalid, model.getSlotAt(1, 2));

        // Row 2: columns 0-2 are valid
        assertEquals(SlotState.Marble, model.getSlotAt(2, 0));
        assertEquals(SlotState.Marble, model.getSlotAt(2, 1));
        assertEquals(SlotState.Marble, model.getSlotAt(2, 2));
        assertEquals(SlotState.Invalid, model.getSlotAt(2, 3));

        // Row 4: columns 0-4 are valid
        for (int c = 0; c <= 4; c++) {
            assertEquals(SlotState.Marble, model.getSlotAt(4, c));
        }
    }

    @Test
    public void testHorizontalMove() {
        MarbleSolitaireModel testModel = new TriangleSolitaireModel(4, 1);
        testModel.move(4, 3, 4, 1);
        assertEquals(SlotState.Empty, testModel.getSlotAt(4, 3));
        assertEquals(SlotState.Empty, testModel.getSlotAt(4, 2));
        assertEquals(SlotState.Marble, testModel.getSlotAt(4, 1));
    }

    @Test
    public void testDiagonalUpLeftMove() {
        MarbleSolitaireModel testModel = new TriangleSolitaireModel(0, 0);
        testModel.move(2, 2, 0, 0);
        assertEquals(SlotState.Empty, testModel.getSlotAt(2, 2));
        assertEquals(SlotState.Empty, testModel.getSlotAt(1, 1));
        assertEquals(SlotState.Marble, testModel.getSlotAt(0, 0));
    }

    @Test
    public void testDiagonalUpRightMove() {
        MarbleSolitaireModel testModel = new TriangleSolitaireModel(0, 0);
        testModel.move(2, 0, 0, 0);
        assertEquals(SlotState.Empty, testModel.getSlotAt(2, 0));
        assertEquals(SlotState.Empty, testModel.getSlotAt(1, 0));
        assertEquals(SlotState.Marble, testModel.getSlotAt(0, 0));
    }

    @Test
    public void testDiagonalDownLeftMove() {
        MarbleSolitaireModel testModel = new TriangleSolitaireModel(2, 0);
        testModel.move(0, 0, 2, 0);
        assertEquals(SlotState.Empty, testModel.getSlotAt(0, 0));
        assertEquals(SlotState.Empty, testModel.getSlotAt(1, 0));
        assertEquals(SlotState.Marble, testModel.getSlotAt(2, 0));
    }

    @Test
    public void testDiagonalDownRightMove() {
        MarbleSolitaireModel testModel = new TriangleSolitaireModel(2, 2);
        testModel.move(0, 0, 2, 2);
        assertEquals(SlotState.Empty, testModel.getSlotAt(0, 0));
        assertEquals(SlotState.Empty, testModel.getSlotAt(1, 1));
        assertEquals(SlotState.Marble, testModel.getSlotAt(2, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveNoMarble() {
        model.move(0, 0, 2, 0); // From position has no marble
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveDestinationOccupied() {
        model.move(2, 0, 4, 0); // Destination has a marble
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveWrongDistance() {
        MarbleSolitaireModel testModel = new TriangleSolitaireModel(1, 0);
        testModel.move(0, 0, 1, 0); // Moving only 1 space
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveNoMiddleMarble() {
        MarbleSolitaireModel testModel = new TriangleSolitaireModel(1, 1);
        testModel.move(1, 0, 1, 1); // Would jump, but middle is already empty
        testModel.move(3, 1, 1, 1); // No marble in middle position
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveDiagonal() {
        MarbleSolitaireModel testModel = new TriangleSolitaireModel(2, 1);
        // Invalid diagonal move (not one of the 6 valid directions)
        testModel.move(0, 0, 2, 2); // This would be diagonal but wrong pattern
    }

    @Test
    public void testIsGameOverVariousSizes() {
        // Size 5 (default) - not game over
        assertFalse(model.isGameOver());

        // Size 1 - immediately game over (only 1 position)
        MarbleSolitaireModel size1 = new TriangleSolitaireModel(1, 0, 0);
        assertTrue(size1.isGameOver());

        // Size 2 - immediately game over (no 2-space jumps possible)
        MarbleSolitaireModel size2 = new TriangleSolitaireModel(2, 0, 0);
        assertTrue(size2.isGameOver());

        // Size 3 - has valid moves
        MarbleSolitaireModel size3 = new TriangleSolitaireModel(3, 0, 0);
        assertFalse(size3.isGameOver());

        // Play size 3 to completion
        size3.move(2, 2, 0, 0); // Jump diagonal up-left
        assertFalse(size3.isGameOver()); // Still has moves

        size3.move(2, 0, 2, 2); // Jump horizontal right
        assertFalse(size3.isGameOver()); // Still has moves

        size3.move(0, 0, 2, 0); // Jump diagonal down-left
        assertTrue(size3.isGameOver()); // Now game over
    }

    @Test
    public void testGetScore() {
        assertEquals(14, model.getScore());
        MarbleSolitaireModel testModel = new TriangleSolitaireModel(0, 0);
        testModel.move(2, 0, 0, 0);
        assertEquals(13, testModel.getScore());
        testModel.move(2, 2, 2, 0);
        assertEquals(12, testModel.getScore());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSlotAtOutOfBounds() {
        model.getSlotAt(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSlotAtOutOfBoundsPositive() {
        model.getSlotAt(5, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSlotAtColumnTooHigh() {
        model.getSlotAt(2, 5); // Row 2 only has columns 0-2
    }

    @Test
    public void testMultipleMoves() {
        MarbleSolitaireModel game = new TriangleSolitaireModel(0, 0);

        // Move 1: Jump from (2,0) to (0,0)
        game.move(2, 0, 0, 0);
        assertEquals(SlotState.Marble, game.getSlotAt(0, 0));
        assertEquals(SlotState.Empty, game.getSlotAt(1, 0));
        assertEquals(SlotState.Empty, game.getSlotAt(2, 0));

        // Move 2: Jump from (2,2) to (2,0)
        game.move(2, 2, 2, 0);
        assertEquals(SlotState.Marble, game.getSlotAt(2, 0));
        assertEquals(SlotState.Empty, game.getSlotAt(2, 1));
        assertEquals(SlotState.Empty, game.getSlotAt(2, 2));

        // Move 3: Jump from (0,0) to (2,2)
        game.move(0, 0, 2, 2);
        assertEquals(SlotState.Empty, game.getSlotAt(0, 0));
        assertEquals(SlotState.Empty, game.getSlotAt(1, 1));
        assertEquals(SlotState.Marble, game.getSlotAt(2, 2));

        assertEquals(11, game.getScore());
    }
}