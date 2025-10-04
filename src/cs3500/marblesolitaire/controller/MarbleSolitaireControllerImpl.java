package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
    private final MarbleSolitaireModel model;
    private final MarbleSolitaireView view;
    private final Readable readable;

    /**
     * Constructs a new Marble Solitaire controller.
     *
     * @param model    the game model to use
     * @param view     the view to use for rendering
     * @param readable the input source for user commands
     * @throws IllegalArgumentException if any parameter is null
     */
    public MarbleSolitaireControllerImpl(MarbleSolitaireModel model, MarbleSolitaireView view,
                                         Readable readable) throws IllegalArgumentException {
        if (model == null || view == null || readable == null) {
            throw new IllegalArgumentException("Model, view, and readable cannot be null");
        }
        this.model = model;
        this.view = view;
        this.readable = readable;
    }

    /**
     * Plays a new game of Marble Solitaire.
     * The game continues until it ends naturally or the user quits.
     *
     * @throws IllegalStateException if unable to read input or transmit output
     */
    @Override
    public void playGame() throws IllegalStateException {
        Scanner scanner = new Scanner(readable);
        while (!model.isGameOver()) {
            //  Render the current state
            renderGameState();
            // Get user input (4 values)
            int fromRow, fromColumn, toRow, toColumn;
            try {
                // Get from-row
                fromRow = getNextValidInput(scanner);
                // User quit
                if (fromRow == -1) {
                    handleQuit();
                    return;
                }
                // Get from-column
                fromColumn = getNextValidInput(scanner);
                // User quit
                if (fromColumn == -1) {
                    handleQuit();
                    return;
                }
                // Get to-row
                toRow = getNextValidInput(scanner);
                // User quit
                if (toRow == -1) {
                    handleQuit();
                    return;
                }
                // Get to-column
                toColumn = getNextValidInput(scanner);
                // User quit
                if (toColumn == -1) {
                    handleQuit();
                    return;
                }
            } catch (NoSuchElementException e) {
                throw new IllegalStateException("Unable to read input", e);
            }

            // Make the move (convert from 1-based to 0-based indexing)
            try {
                model.move(fromRow - 1, fromColumn - 1, toRow - 1, toColumn - 1);
            } catch (Exception e) {
                String errorMsg = e.getMessage() != null ? e.getMessage() : "";
                transmitMessage("Invalid move. Play again. " + errorMsg + "\n");
            }
        }

        // Game is over
        handleGameOver();
    }

    /**
     * Gets the next valid input from the scanner.
     *
     * @param scanner the scanner to read from
     * @return the next positive integer, or -1 if user quits
     * @throws NoSuchElementException if no more input is available
     */
    private int getNextValidInput(Scanner scanner) {
        while (true) {
            if (!scanner.hasNext()) {
                throw new NoSuchElementException("No more input available");
            }

            String input = scanner.next();
            // Check for quit
            if (input.equalsIgnoreCase("q")) {
                return -1;
            }
            // Try to parse as positive integer
            try {
                int val = Integer.parseInt(input);
                if (val > 0) {
                    return val;
                }

            } catch (NumberFormatException e) {
                // Not a valid number, continues loop
            }
            // Invalid input, ask for re-entry
            transmitMessage("Re-enter a positive integer greater than 0: ");
        }
    }

    /**
     * Renders the current game state including board and score.
     *
     * @throws IllegalStateException if transmission fails
     */
    private void renderGameState() throws IllegalStateException {
        try {
            view.renderBoard();
            view.renderMessage("\n");
            view.renderMessage("Score: " + model.getScore() + "\n");
        } catch (IOException e) {
            throw new IllegalStateException("Could not transmit output", e);
        }
    }

    /**
     * Transmits a message to the view.
     *
     * @param message the message to transmit
     * @throws IllegalStateException if transmission fails
     */
    private void transmitMessage(String message) throws IllegalStateException {
        try {
            view.renderMessage(message);
        } catch (IOException e) {
            throw new IllegalStateException("Could not transmit output", e);
        }
    }

    /**
     * Handles the quit scenario
     */
    private void handleQuit() {
        transmitMessage("Game quit!\n");
        transmitMessage("State of game when quit:\n");
        try {
            view.renderBoard();
        } catch (IOException e) {
            throw new IllegalStateException("Could not transmit output", e);
        }
        transmitMessage("\n");
        transmitMessage("Score: " + model.getScore() + "\n");
    }

    /**
     * Handles the game over scenario
     */
    private void handleGameOver() {
        transmitMessage("Game over!\n");
        try {
            view.renderBoard();

        } catch (IOException e) {
            throw new IllegalStateException("Could not transmit output", e);
        }
        transmitMessage("\n");
        transmitMessage("Score: " + model.getScore() + "\n");
    }
}
