package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.AbstractSolitaireModel;

/**
 * Implementation of European Marble Solitaire with an octagonal board.
 *
 * Design Decision (HW4):
 * This class extends AbstractSolitaireModel from hw02 package to maximize code reuse.
 * The European variant differs from English only in board shape (octagonal vs plus-shaped),
 * while sharing identical move rules, game-over logic, and scoring.
 *
 * By extending the abstract class, we avoid duplication
 */
public class EuropeanSolitaireModel extends AbstractSolitaireModel {

    /**
     * Default constructor: creates an octagonal board with side length 3,
     * empty slot at center.
     */
    public EuropeanSolitaireModel() {
        this(3, 3, 3);
    }

    /**
     * Constructor with side length: creates an octagonal board with specified side length,
     * empty slot at center.
     *
     * @param sideLength the side length of the octagon (must be odd and positive)
     * @throws IllegalArgumentException if side length is invalid
     */
    public EuropeanSolitaireModel(int sideLength) {
        this(sideLength, (sideLength * 3 - 2) / 2, (sideLength * 3 - 2) / 2);
    }

    /**
     * Constructor with position: creates an octagonal board with side length 3,
     * empty slot at specified position.
     *
     * @param sRow the row of the empty slot
     * @param sCol the column of the empty slot
     * @throws IllegalArgumentException if position is invalid
     */
    public EuropeanSolitaireModel(int sRow, int sCol) {
        this(3, sRow, sCol);
    }

    /**
     * Full constructor: creates an octagonal board with specified side length,
     * empty slot at (sRow, sCol).
     *
     * @param sideLength the side length of the octagon (must be odd and positive)
     * @param sRow the row of the empty slot
     * @param sCol the column of the empty slot
     * @throws IllegalArgumentException if parameters are invalid
     */
    public EuropeanSolitaireModel(int sideLength, int sRow, int sCol) {
        // Note: armThickness in parent class serves as sideLength for European variant
        super(sideLength, sRow, sCol);
    }

    /**
     * Check if a position is valid on the octagonal board.
     * The octagonal shape is created by cutting off the four corners of a square board.
     *
     * This is the ONLY method that differs from EnglishSolitaireModel.
     *
     * @param row the row to check
     * @param col the column to check
     * @return true if the position is valid on the octagonal board
     */
    @Override
    protected boolean isValidPosition(int row, int col) {
        if (row < 0 || row >= getBoardSize() || col < 0 || col >= getBoardSize()) {
            return false;
        }

        // For an octagonal board with side length n:
        // The board size is 3n - 2
        // The corners are cut off in a diagonal pattern
        int cutOff = armThickness - 1;  // armThickness serves as sideLength
        int boardSize = getBoardSize();

        // Top-left corner cutoff
        if (row < cutOff && col < cutOff && (row + col) < cutOff) {
            return false;
        }

        // Top-right corner cutoff
        if (row < cutOff && col >= boardSize - cutOff && (col - row) >= boardSize - cutOff) {
            return false;
        }

        // Bottom-left corner cutoff
        if (row >= boardSize - cutOff && col < cutOff && (row - col) >= boardSize - cutOff) {
            return false;
        }

        // Bottom-right corner cutoff
        return row < boardSize - cutOff || col < boardSize - cutOff ||
                (row + col) <= (boardSize + cutOff + 1);
    }
}