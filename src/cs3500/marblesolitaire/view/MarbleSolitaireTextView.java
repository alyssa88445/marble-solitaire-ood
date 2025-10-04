package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

import java.io.IOException;

public class MarbleSolitaireTextView implements MarbleSolitaireView {
    private final MarbleSolitaireModelState model;
    private final Appendable appendable;

    /**
     * Constructor that takes a MarbleSolitaireModelState to view
     *
     * @param model the model to render
     * @throws IllegalArgumentException if the model is null
     */
    public MarbleSolitaireTextView(MarbleSolitaireModelState model) {
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null");
        }
        this.model = model;
        this.appendable = System.out;
    }

    /**
     * Constructor that takes both a model and an appendable destination.
     *
     * @param model the model to render
     * @param appendable the destination for output
     * @throws IllegalArgumentException if either the model or appendable is null
     */
    public MarbleSolitaireTextView(MarbleSolitaireModelState model, Appendable appendable) {
        if (model == null || appendable == null) {
            throw new IllegalArgumentException("Model and appendable object cannot be null");
        }
        this.model = model;
        this.appendable = appendable;
    }

    /**
     * Return a string that represents the current state of the board. The
     * string should have one line per row of the game board. Each slot on the
     * game board is a single character (O, _ or space for a marble, empty and
     * invalid position respectively). Slots in a row should be separated by a
     * space. Each row has no space before the first slot and after the last slot.
     *
     * @return the game state as a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int boardSize = model.getBoardSize();

        for (int row = 0; row < boardSize; row++) {
            StringBuilder rowBuilder = new StringBuilder();

            for (int col = 0; col < boardSize; col++) {
                MarbleSolitaireModelState.SlotState slot = model.getSlotAt(row, col);

                switch (slot) {
                    case Marble:
                        rowBuilder.append("O");
                        break;
                    case Empty:
                        rowBuilder.append("_");
                        break;
                    case Invalid:
                        rowBuilder.append(" ");
                        break;
                }

                // Add space after each slot except the last
                if (col < boardSize - 1) {
                    rowBuilder.append(" ");
                }
            }

            // Remove trailing spaces from the row
            String rowString = rowBuilder.toString().replaceAll("\\s+$", "");

            // Add the row to the result
            sb.append(rowString);

            // Add newline after each row except the last
            if (row < boardSize - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Render the board to the provided data destination. The board should be rendered exactly
     * in the format produced by the toString method above
     *
     * @throws IOException if transmission of the board to the provided data destination fails
     */
    @Override
    public void renderBoard() throws IOException {
        appendable.append(this.toString());
    }

    /**
     * Render a specific message to the provided data destination.
     *
     * @param message the message to be transmitted
     * @throws IOException if transmission of the board to the provided data destination fails
     */
    @Override
    public void renderMessage(String message) throws IOException {
        appendable.append(message);
    }
}