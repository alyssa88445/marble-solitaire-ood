import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test suite for MarbleSolitaireControllerImpl.
 */
public class MarbleSolitaireControllerImplTest {

    /**
     * Tests constructor throws IllegalArgumentException for null model.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullModel() {
        new MarbleSolitaireControllerImpl(null,
                new MarbleSolitaireTextView(new EnglishSolitaireModel()),
                new StringReader("q"));
    }

    /**
     * Tests constructor throws IllegalArgumentException for null view.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullView() {
        new MarbleSolitaireControllerImpl(new EnglishSolitaireModel(),
                null,
                new StringReader("q"));
    }

    /**
     * Tests constructor throws IllegalArgumentException for null readable.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullReadable() {
        new MarbleSolitaireControllerImpl(new EnglishSolitaireModel(),
                new MarbleSolitaireTextView(new EnglishSolitaireModel()),
                null);
    }

    /**
     * Tests immediate quit with lowercase 'q'.
     */
    @Test
    public void testQuitLowercase() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader("q"));

        controller.playGame();

        assertTrue(output.toString().contains("Game quit!"));
        assertTrue(output.toString().contains("State of game when quit:"));
        assertTrue(output.toString().contains("Score: 32"));
    }

    /**
     * Tests immediate quit with uppercase 'Q'.
     */
    @Test
    public void testQuitUppercase() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader("Q"));

        controller.playGame();

        assertTrue(output.toString().contains("Game quit!"));
    }

    /**
     * Tests quit after entering some valid inputs.
     */
    @Test
    public void testQuitMidInput() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader("2 4 q"));

        controller.playGame();

        assertTrue(output.toString().contains("Game quit!"));
        assertEquals(32, model.getScore()); // No move was made
    }

    /**
     * Tests a single valid move.
     */
    @Test
    public void testValidMove() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader("2 4 4 4 q"));

        controller.playGame();

        assertEquals(31, model.getScore()); // One marble removed
        assertTrue(output.toString().contains("Game quit!"));
    }

    /**
     * Tests invalid move handling.
     */
    @Test
    public void testInvalidMove() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        // Try to move from empty center position
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader("4 4 4 6 q"));

        controller.playGame();

        assertTrue(output.toString().contains("Invalid move. Play again."));
        assertEquals(32, model.getScore()); // No successful move
    }

    /**
     * Tests handling of negative number input.
     */
    @Test
    public void testNegativeInput() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader("-1 2 4 4 4 q"));

        controller.playGame();

        assertTrue(output.toString().contains("Re-enter a positive integer"));
        assertEquals(31, model.getScore()); // Move eventually succeeds
    }

    /**
     * Tests handling of zero input.
     */
    @Test
    public void testZeroInput() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader("0 2 q"));

        controller.playGame();

        assertTrue(output.toString().contains("Re-enter a positive integer"));
    }

    /**
     * Tests handling of non-numeric input.
     */
    @Test
    public void testNonNumericInput() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader("abc 2 def 4 xyz 4 @ 4 q"));

        controller.playGame();

        // Count occurrences of "Re-enter the value"
        String outputStr = output.toString();
        int count = outputStr.split("Re-enter a positive integer").length - 1;
        assertTrue(count >= 4); // At least 4 invalid inputs
    }

    /**
     * Tests IllegalStateException when input runs out.
     */
    @Test(expected = IllegalStateException.class)
    public void testNoMoreInput() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader("2 4")); // Only 2 inputs, need 4

        controller.playGame();
    }

    /**
     * Tests multiple moves in sequence.
     */
    @Test
    public void testMultipleMoves() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader("2 4 4 4  5 4 3 4  q"));

        controller.playGame();

        assertEquals(30, model.getScore()); // Two moves made
    }

    /**
     * Tests game can run to completion.
     */
    @Test
    public void testGameRunsToCompletion() {
        // Create a simple game that ends quickly
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        String moves = "2 4 4 4 " +
                "3 6 3 4 " +
                "5 5 3 5 " +
                "3 4 3 6 " +
                "5 7 5 5 " +
                "4 7 4 5 " +
                "3 2 3 4 " +
                "1 3 3 3 " +
                "3 4 3 2 " +
                "1 5 1 3 " +
                "3 1 3 3 " +
                "3 7 3 5 " +
                "4 3 2 3 " +
                "1 3 3 3 " +
                "3 5 1 5 " +
                "5 5 3 5 " +
                "4 1 4 3 " +
                "4 3 4 5 " +
                "4 5 2 5 " +
                "6 3 4 3 " +
                "5 1 5 3 " +
                "4 3 6 3 " +
                "1 5 3 5 " +
                "6 4 4 4 " +
                "7 3 5 3 " +
                "7 5 5 5";
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader(moves));

        controller.playGame();

        assertTrue(output.toString().contains("Game over!"));
        assertTrue(model.isGameOver());
        assertEquals(6, model.getScore());
    }

    /**
     * Tests inputs separated by newlines.
     */
    @Test
    public void testNewlineSeparatedInputs() {
        MarbleSolitaireModel model = new EnglishSolitaireModel();
        StringBuilder output = new StringBuilder();
        MarbleSolitaireView view = new MarbleSolitaireTextView(model, output);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
                model, view, new StringReader("2\n4\n4\n4\nq"));

        controller.playGame();

        assertEquals(31, model.getScore()); // Move succeeded
    }
}