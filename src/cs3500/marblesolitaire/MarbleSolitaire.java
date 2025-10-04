package cs3500.marblesolitaire;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

import java.io.InputStreamReader;

/**
 * Main class for the Marble Solitaire game.
 * Handles command-line arguments to configure different game variants.
 */
public final class MarbleSolitaire {

    /**
     * Main entry point for the Marble Solitaire game.
     * <p>
     * Command line arguments:
     * - Board type (required): english, european, or triangular
     * - Size (optional): -size N where N is the board size
     * - Hole position (optional): -hole R C where R and C are row and column
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: Must specify game type (english, european, or triangular)");
            return;
        }

        // Parse command line arguments
        String gameType = args[0].toLowerCase();
        Integer size = null;
        Integer holeRow = null;
        Integer holeCol = null;

        // Parse optional arguments
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-size") && i + 1 < args.length) {
                try {
                    size = Integer.parseInt(args[i + 1]);
                    i++; // Skip the next argument since we've processed it
                } catch (NumberFormatException e) {
                    System.err.println("Error: Invalid size value");
                    return;
                }
            } else if (args[i].equals("-hole") && i + 2 < args.length) {
                try {
                    holeRow = Integer.parseInt(args[i + 1]);
                    holeCol = Integer.parseInt(args[i + 2]);
                    i += 2; // Skip the next two arguments
                } catch (NumberFormatException e) {
                    System.err.println("Error: Invalid hole position");
                    return;
                }
            }
        }

        // Create the appropriate model based on game type and arguments
        MarbleSolitaireModel model;
        MarbleSolitaireView view;

        try {
            switch (gameType) {
                case "english":
                    model = createEnglishModel(size, holeRow, holeCol);
                    view = new MarbleSolitaireTextView(model, System.out);
                    break;

                case "european":
                    model = createEuropeanModel(size, holeRow, holeCol);
                    view = new MarbleSolitaireTextView(model, System.out);
                    break;

                case "triangular":
                    model = createTriangularModel(size, holeRow, holeCol);
                    view = new TriangleSolitaireTextView(model, System.out);
                    break;

                default:
                    System.err.println("Error: Unknown game type. Use english, european, or triangular");
                    return;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating game: " + e.getMessage());
            return;
        }

        // Create controller and start the game
        Readable input = new InputStreamReader(System.in);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(model, view, input);

        // Play the game
        controller.playGame();
    }

    /**
     * Creates an English Solitaire model based on provided parameters.
     */
    private static MarbleSolitaireModel createEnglishModel(Integer size, Integer row, Integer col) {
        if (size != null && row != null && col != null) {
            // Convert from 1-based to 0-based indexing for hole position
            return new EnglishSolitaireModel(size, row - 1, col - 1);
        } else if (size != null) {
            return new EnglishSolitaireModel(size);
        } else if (row != null && col != null) {
            // Convert from 1-based to 0-based indexing
            return new EnglishSolitaireModel(row - 1, col - 1);
        } else {
            return new EnglishSolitaireModel();
        }
    }

    /**
     * Creates a European Solitaire model based on provided parameters.
     */
    private static MarbleSolitaireModel createEuropeanModel(Integer size, Integer row, Integer col) {
        if (size != null && row != null && col != null) {
            // Convert from 1-based to 0-based indexing for hole position
            return new EuropeanSolitaireModel(size, row - 1, col - 1);
        } else if (size != null) {
            return new EuropeanSolitaireModel(size);
        } else if (row != null && col != null) {
            // Convert from 1-based to 0-based indexing
            return new EuropeanSolitaireModel(row - 1, col - 1);
        } else {
            return new EuropeanSolitaireModel();
        }
    }

    /**
     * Creates a Triangular Solitaire model based on provided parameters.
     */
    private static MarbleSolitaireModel createTriangularModel(Integer size, Integer row, Integer col) {
        if (size != null && row != null && col != null) {
            // Convert from 1-based to 0-based indexing for hole position
            return new TriangleSolitaireModel(size, row - 1, col - 1);
        } else if (size != null) {
            return new TriangleSolitaireModel(size);
        } else if (row != null && col != null) {
            // Convert from 1-based to 0-based indexing
            return new TriangleSolitaireModel(row - 1, col - 1);
        } else {
            return new TriangleSolitaireModel();
        }
    }
}