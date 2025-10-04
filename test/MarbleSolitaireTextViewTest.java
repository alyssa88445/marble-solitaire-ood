import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test class for the MarbleSolitaireTextView implementation.
 * Tests rendering of game boards in various states and configurations.
 */
public class MarbleSolitaireTextViewTest {
    private MarbleSolitaireModel model1;
    private MarbleSolitaireModel model2;
    private MarbleSolitaireModel model3;
    private MarbleSolitaireTextView view1;
    private MarbleSolitaireTextView view2;
    private MarbleSolitaireTextView view3;

    /**
     * Sets up test models and views before each test method.
     * Creates models with different configurations and corresponding views.
     */
    @Before
    public void setUp() {
        model1 = new EnglishSolitaireModel();
        model2 = new EnglishSolitaireModel(0, 4);
        model3 = new EnglishSolitaireModel(5);
        view1 = new MarbleSolitaireTextView(model1);
        view2 = new MarbleSolitaireTextView(model2);
        view3 = new MarbleSolitaireTextView(model3);
    }

    /**
     * Tests that constructor throws exception for null model.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullModelConstructor() {
        new MarbleSolitaireTextView(null);
    }

    /**
     * Tests that two-parameter constructor validates parameters correctly.
     */
    @Test
    public void testTwoParameterConstructorValidation() {
        // Test null model
        try {
            new MarbleSolitaireTextView(null, new StringBuilder());
            fail("Should throw IllegalArgumentException for null model");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test null appendable
        try {
            new MarbleSolitaireTextView(model1, null);
            fail("Should throw IllegalArgumentException for null appendable");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test valid construction
        StringBuilder output = new StringBuilder();
        MarbleSolitaireTextView view = new MarbleSolitaireTextView(model1, output);
        assertNotNull(view);
    }

    /**
     * Tests toString renders default board correctly.
     */
    @Test
    public void testToStringDefaultBoard() {
        String expected =
                        "    O O O\n" +
                        "    O O O\n" +
                        "O O O O O O O\n" +
                        "O O O _ O O O\n" +
                        "O O O O O O O\n" +
                        "    O O O\n" +
                        "    O O O";
        assertEquals(expected, view1.toString());
    }

    /**
     * Tests toString with custom empty position.
     */
    @Test
    public void testToStringCustomEmptyPosition() {
        String expected =
                        "    O O _\n" +
                        "    O O O\n" +
                        "O O O O O O O\n" +
                        "O O O O O O O\n" +
                        "O O O O O O O\n" +
                        "    O O O\n" +
                        "    O O O";
        assertEquals(expected, view2.toString());
    }

    /**
     * Tests toString after a move has been made.
     */
    @Test
    public void testToStringAfterMove() {
        model1.move(3, 1, 3, 3);
        String expected =
                        "    O O O\n" +
                        "    O O O\n" +
                        "O O O O O O O\n" +
                        "O _ _ O O O O\n" +
                        "O O O O O O O\n" +
                        "    O O O\n" +
                        "    O O O";
        assertEquals(expected, view1.toString());
    }

    /**
     * Tests toString after multiple moves.
     */
    @Test
    public void testToStringMultipleMoves() {
        model1.move(3, 1, 3, 3);
        model1.move(3, 4, 3, 2);
        model1.move(3, 6, 3, 4);
        String expected =
                        "    O O O\n" +
                        "    O O O\n" +
                        "O O O O O O O\n" +
                        "O _ O _ O _ _\n" +
                        "O O O O O O O\n" +
                        "    O O O\n" +
                        "    O O O";
        assertEquals(expected, view1.toString());
    }

    /**
     * Tests toString with larger board (arm thickness 5).
     */
    @Test
    public void testToStringLargerBoard() {
        String line1 = "        O O O O O";
        String line2 = "        O O O O O";
        String line3 = "        O O O O O";
        String line4 = "        O O O O O";
        String line5 = "O O O O O O O O O O O O O";
        String line6 = "O O O O O O O O O O O O O";
        String line7 = "O O O O O O _ O O O O O O";
        String line8 = "O O O O O O O O O O O O O";
        String line9 = "O O O O O O O O O O O O O";
        String line10 = "        O O O O O";
        String line11 = "        O O O O O";
        String line12 = "        O O O O O";
        String line13 = "        O O O O O";

        String expected = String.join("\n",
                line1, line2, line3, line4, line5, line6, line7,
                line8, line9, line10, line11, line12, line13);

        assertEquals(expected, view3.toString());
    }

    /**
     * Tests formatting constraints: no trailing spaces or newlines.
     */
    @Test
    public void testToStringFormatting() {
        String result = view1.toString();

        // No trailing newline
        assertFalse("Result should not end with newline", result.endsWith("\n"));

        // No trailing spaces on any line
        String[] lines = result.split("\n");
        for (int i = 0; i < lines.length; i++) {
            assertFalse("Line " + i + " has trailing spaces", lines[i].endsWith(" "));
        }

        // Correct spacing
        assertTrue(lines[0].startsWith("    ")); // First line indented
        assertFalse(lines[2].startsWith(" ")); // Full row not indented
        assertTrue(lines[0].contains("O O O")); // Single spaces between slots
    }

    /**
     * Tests rendering of minimal board (arm thickness 1).
     */
    @Test
    public void testToStringMinimalBoard() {
        MarbleSolitaireModel smallModel = new EnglishSolitaireModel(1);
        MarbleSolitaireTextView smallView = new MarbleSolitaireTextView(smallModel);
        assertEquals("_", smallView.toString());
    }

    /**
     * Tests renderBoard with a working Appendable.
     */
    @Test
    public void testRenderBoardWithAppendable() throws IOException {
        StringBuilder output = new StringBuilder();
        MarbleSolitaireTextView viewWithAppendable = new MarbleSolitaireTextView(model1, output);

        viewWithAppendable.renderBoard();

        assertEquals(view1.toString(), output.toString());
    }

    /**
     * Tests renderMessage with various inputs.
     */
    @Test
    public void testRenderMessage() throws IOException {
        StringBuilder output = new StringBuilder();
        MarbleSolitaireTextView viewWithAppendable = new MarbleSolitaireTextView(model1, output);

        // Normal message
        viewWithAppendable.renderMessage("Score: 32");
        assertEquals("Score: 32", output.toString());

        // Additional message
        viewWithAppendable.renderMessage("\n");
        assertEquals("Score: 32\n", output.toString());

        // Empty message
        viewWithAppendable.renderMessage("");
        assertEquals("Score: 32\n", output.toString());
    }

    /**
     * Tests typical game rendering sequence.
     */
    @Test
    public void testGameRenderingSequence() throws IOException {
        StringBuilder output = new StringBuilder();
        MarbleSolitaireTextView viewWithAppendable = new MarbleSolitaireTextView(model1, output);

        // Initial state
        viewWithAppendable.renderBoard();
        viewWithAppendable.renderMessage("\n");
        viewWithAppendable.renderMessage("Score: 32\n");

        // Make move and render again
        model1.move(3, 1, 3, 3);
        viewWithAppendable.renderBoard();
        viewWithAppendable.renderMessage("\n");
        viewWithAppendable.renderMessage("Score: 31\n");

        String result = output.toString();
        assertTrue("Should contain initial score", result.contains("Score: 32"));
        assertTrue("Should contain updated score", result.contains("Score: 31"));
        assertTrue("Should show moved marble", result.contains("O _ _ O"));
    }

    /**
     * Tests renderBoard throws IOException when Appendable fails.
     */
    @Test(expected = IOException.class)
    public void testRenderBoardIOException() throws IOException {
        Appendable failingAppendable = new FailingAppendable();
        MarbleSolitaireTextView view = new MarbleSolitaireTextView(model1, failingAppendable);
        view.renderBoard();
    }

    /**
     * Tests renderMessage throws IOException when Appendable fails.
     */
    @Test(expected = IOException.class)
    public void testRenderMessageIOException() throws IOException {
        Appendable failingAppendable = new FailingAppendable();
        MarbleSolitaireTextView view = new MarbleSolitaireTextView(model1, failingAppendable);
        view.renderMessage("This should fail");
    }

    /**
     * Tests that toString works independently of Appendable operations.
     */
    @Test
    public void testToStringIndependence() throws IOException {
        StringBuilder output = new StringBuilder();
        MarbleSolitaireTextView view = new MarbleSolitaireTextView(model1, output);

        String before = view.toString();
        view.renderBoard();
        view.renderMessage("\nExtra content\n");
        String after = view.toString();

        assertEquals("toString should not be affected by render operations", before, after);
    }

    /**
     * Mock Appendable that always throws IOException.
     */
    private static class FailingAppendable implements Appendable {
        @Override
        public Appendable append(CharSequence csq) throws IOException {
            throw new IOException("Simulated IO failure");
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) throws IOException {
            throw new IOException("Simulated IO failure");
        }

        @Override
        public Appendable append(char c) throws IOException {
            throw new IOException("Simulated IO failure");
        }
    }
}