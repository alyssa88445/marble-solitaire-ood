package cs3500.marblesolitaire.model.hw02;

/**
 * Implementation of English Marble Solitaire with plus-shaped board.
 *
 * REFACTORING NOTES (HW4):
 * - Modified to extend AbstractSolitaireModel to eliminate code duplication
 * - All public interface remains unchanged - fully backward compatible
 * - Only the board shape validation (isValidPosition) remains in this class
 * - Common logic (move, isGameOver, getScore) is now inherited from base class
 * - This change reduces code duplication with EuropeanSolitaireModel by ~100 lines
 *
 * The refactoring is justified because English and European variants share:
 * - Same move rules (orthogonal jumps over adjacent marbles)
 * - Same game-over detection logic
 * - Same scoring system
 * - Same board size calculation (armThickness * 3 - 2)
 * They differ only in board shape, which is handled by isValidPosition()
 */
public class EnglishSolitaireModel extends AbstractSolitaireModel {

    /**
     * First constructor: no parameters, arm thickness 3, empty slot at center
     */
    public EnglishSolitaireModel() {
        this(3, 3, 3);  // Center is at (3,3) for arm thickness 3
    }

    /**
     * Second constructor: arm thickness 3, empty slot at (sRow, sCol)
     */
    public EnglishSolitaireModel(int sRow, int sCol) {
        this(3, sRow, sCol);
    }

    /**
     * Third constructor: custom arm thickness, empty slot at center
     */
    public EnglishSolitaireModel(int armThickness) {
        this(armThickness, (armThickness * 3 - 2) / 2, (armThickness * 3 - 2) / 2);
    }

    /**
     * Fourth constructor: custom arm thickness, empty slot at (sRow, sCol)
     */
    public EnglishSolitaireModel(int armThickness, int sRow, int sCol) {
        super(armThickness, sRow, sCol);
    }

    /**
     * Check if a position is valid on the plus-shaped board.
     * The plus shape consists of a vertical and horizontal bar of width armThickness.
     *
     * @param row the row to check
     * @param col the column to check
     * @return true if the position is valid on the plus-shaped board
     */
    @Override
    protected boolean isValidPosition(int row, int col) {
        if (row < 0 || row >= getBoardSize() || col < 0 || col >= getBoardSize()) {
            return false;
        }

        int inset = armThickness - 1;
        // Position is valid if it's in the vertical bar OR horizontal bar
        return (row >= inset && row < getBoardSize() - inset) ||
                (col >= inset && col < getBoardSize() - inset);
    }
}