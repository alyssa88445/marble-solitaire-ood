package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test class for TriangleSolitaireTextView.
 */
public class TriangleSolitaireTextViewTest {
    private MarbleSolitaireModel model;
    private MarbleSolitaireView view;
    private StringBuilder output;

    @Before
    public void setUp() {
        model = new TriangleSolitaireModel();
        output = new StringBuilder();
        view = new TriangleSolitaireTextView(model, output);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullModel() {
        new TriangleSolitaireTextView(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullAppendable() {
        new TriangleSolitaireTextView(model, null);
    }

    @Test
    public void testToStringDefault() {
        String expected =
                "    _\n" +
                        "   O O\n" +
                        "  O O O\n" +
                        " O O O O\n" +
                        "O O O O O";
        assertEquals(expected, view.toString());
    }

    @Test
    public void testToStringCustomPosition() {
        MarbleSolitaireModel customModel = new TriangleSolitaireModel(2, 1);
        MarbleSolitaireView customView = new TriangleSolitaireTextView(customModel);
        String expected =
                "    O\n" +
                        "   O O\n" +
                        "  O _ O\n" +
                        " O O O O\n" +
                        "O O O O O";
        assertEquals(expected, customView.toString());
    }

    @Test
    public void testToStringSize3() {
        MarbleSolitaireModel smallModel = new TriangleSolitaireModel(3);
        MarbleSolitaireView smallView = new TriangleSolitaireTextView(smallModel);
        String expected =
                "  _\n" +
                        " O O\n" +
                        "O O O";
        assertEquals(expected, smallView.toString());
    }

    @Test
    public void testToStringSize7() {
        MarbleSolitaireModel largeModel = new TriangleSolitaireModel(7, 3, 3);
        MarbleSolitaireView largeView = new TriangleSolitaireTextView(largeModel);
        String expected =
                "      O\n" +
                        "     O O\n" +
                        "    O O O\n" +
                        "   O O O _\n" +
                        "  O O O O O\n" +
                        " O O O O O O\n" +
                        "O O O O O O O";
        assertEquals(expected, largeView.toString());
    }

    @Test
    public void testRenderBoard() throws IOException {
        view.renderBoard();
        String expected =
                "    _\n" +
                        "   O O\n" +
                        "  O O O\n" +
                        " O O O O\n" +
                        "O O O O O";
        assertEquals(expected, output.toString());
    }

    @Test
    public void testRenderMessage() throws IOException {
        String message = "Score: 14\n";
        view.renderMessage(message);
        assertEquals(message, output.toString());
    }

    @Test
    public void testRenderBoardAndMessage() throws IOException {
        view.renderBoard();
        view.renderMessage("\n");
        view.renderMessage("Score: 14\n");
        String expected =
                "    _\n" +
                        "   O O\n" +
                        "  O O O\n" +
                        " O O O O\n" +
                        "O O O O O\n" +
                        "Score: 14\n";
        assertEquals(expected, output.toString());
    }

    @Test
    public void testRenderAfterMove() throws IOException {
        model.move(2, 0, 0, 0);
        view.renderBoard();
        String expected =
                "    O\n" +
                        "   _ O\n" +
                        "  _ O O\n" +
                        " O O O O\n" +
                        "O O O O O";
        assertEquals(expected, output.toString());
    }

    @Test
    public void testNoTrailingSpaces() {
        // Ensure no trailing spaces on any line
        String result = view.toString();
        String[] lines = result.split("\n");
        for (String line : lines) {
            assertFalse("Line should not end with space: '" + line + "'",
                    line.endsWith(" "));
        }
    }

    @Test
    public void testNoTrailingNewline() {
        // Ensure the string doesn't end with a newline
        String result = view.toString();
        assertFalse("String should not end with newline", result.endsWith("\n"));
    }

    @Test
    public void testCorrectSpacing() {
        MarbleSolitaireModel size4Model = new TriangleSolitaireModel(4);
        MarbleSolitaireView size4View = new TriangleSolitaireTextView(size4Model);
        String expected =
                "   _\n" +     // 3 spaces before row 0
                        "  O O\n" +    // 2 spaces before row 1
                        " O O O\n" +   // 1 space before row 2
                        "O O O O";     // 0 spaces before row 3
        assertEquals(expected, size4View.toString());
    }

    @Test(expected = IOException.class)
    public void testRenderBoardIOException() throws IOException {
        Appendable badAppendable = new Appendable() {
            @Override
            public Appendable append(CharSequence csq) throws IOException {
                throw new IOException("Test exception");
            }

            @Override
            public Appendable append(CharSequence csq, int start, int end) throws IOException {
                throw new IOException("Test exception");
            }

            @Override
            public Appendable append(char c) throws IOException {
                throw new IOException("Test exception");
            }
        };

        MarbleSolitaireView badView = new TriangleSolitaireTextView(model, badAppendable);
        badView.renderBoard();
    }

    @Test(expected = IOException.class)
    public void testRenderMessageIOException() throws IOException {
        Appendable badAppendable = new Appendable() {
            @Override
            public Appendable append(CharSequence csq) throws IOException {
                throw new IOException("Test exception");
            }

            @Override
            public Appendable append(CharSequence csq, int start, int end) throws IOException {
                throw new IOException("Test exception");
            }

            @Override
            public Appendable append(char c) throws IOException {
                throw new IOException("Test exception");
            }
        };

        MarbleSolitaireView badView = new TriangleSolitaireTextView(model, badAppendable);
        badView.renderMessage("Test");
    }
}