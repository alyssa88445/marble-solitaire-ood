package cs3500.marblesolitaire.model.hw02;

/**
 * Abstract base class for Solitaire models with rectangular grids (English and European).
 * <p>
 * REFACTORING JUSTIFICATION (HW4):
 * This abstract class was created to minimize code duplication between English
 * and European solitaire models. Both variants share identical move logic,
 * game-over checking, and scoring - they differ only in board shape validation.
 * <p>
 * NOTE: TriangleSolitaireModel does NOT extend this class because it has:
 * - Different coordinate system (triangular vs rectangular)
 * - Different move rules (6 directions vs 4 directions)
 * - Different board size calculation (dimensions vs armThickness * 3 - 2)
 * <p>
 * By extracting common functionality into this base class, we:
 * 1. Eliminate ~100 lines of duplicated code
 * 2. Ensure consistent behavior across rectangular variants
 * 3. Make future maintenance easier (fix bugs in one place)
 * 4. Follow the DRY (Don't Repeat Yourself) principle
 * <p>
 * This refactoring does not change any public interfaces or add new public methods,
 * maintaining full backward compatibility.
 */
public abstract class AbstractSolitaireModel implements MarbleSolitaireModel {
    protected final int armThickness;
    protected final SlotState[][] board;

    /**
     * Constructor for abstract solitaire model.
     *
     * @param armThickness the arm thickness/side length (must be odd and positive)
     * @param sRow         the row of the empty slot
     * @param sCol         the column of the empty slot
     * @throws IllegalArgumentException if parameters are invalid
     */
    protected AbstractSolitaireModel(int armThickness, int sRow, int sCol) {
        if (armThickness <= 0 || armThickness % 2 == 0) {
            throw new IllegalArgumentException();
        }

        this.armThickness = armThickness;
        this.board = new SlotState[getBoardSize()][getBoardSize()];

        // Initialize entire board
        for (int r = 0; r < getBoardSize(); r++) {
            for (int c = 0; c < getBoardSize(); c++) {
                board[r][c] = isValidPosition(r, c) ? SlotState.Marble : SlotState.Invalid;
            }
        }

        // Validate and set empty position
        if (!isValidPosition(sRow, sCol)) {
            throw new IllegalArgumentException("Invalid empty cell position (" + sRow + "," + sCol + ")");
        }
        board[sRow][sCol] = SlotState.Empty;
    }

    /**
     * Abstract method to check if a position is valid on the specific board shape.
     * Must be implemented by subclasses to define their specific board shapes.
     *
     * @param row the row to check
     * @param col the column to check
     * @return true if the position is valid for this board shape
     */
    protected abstract boolean isValidPosition(int row, int col);

    /**
     * Move implementation for rectangular grid-based solitaire games.
     * Handles orthogonal moves (up, down, left, right) with a jump of 2 spaces.
     */
    @Override
    public void move(int fromRow, int fromCol, int toRow, int toCol) {
        // Validate move distance (must be 2 positions horizontally or vertically)
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        if (!((rowDiff == 2 && colDiff == 0) || (rowDiff == 0 && colDiff == 2))) {
            throw new IllegalArgumentException("Invalid move");
        }

        int midRow = (fromRow + toRow) / 2;
        int midCol = (fromCol + toCol) / 2;

        // Validate all positions and states
        if (!isValidPosition(fromRow, fromCol) || !isValidPosition(toRow, toCol) ||
                board[fromRow][fromCol] != SlotState.Marble ||
                board[toRow][toCol] != SlotState.Empty ||
                board[midRow][midCol] != SlotState.Marble) {
            throw new IllegalArgumentException("Invalid move");
        }

        // Execute move
        board[fromRow][fromCol] = SlotState.Empty;
        board[midRow][midCol] = SlotState.Empty;
        board[toRow][toCol] = SlotState.Marble;
    }

    /**
     * Game over check for rectangular grid-based games.
     * Checks all four orthogonal directions for possible moves.
     */
    @Override
    public boolean isGameOver() {
        // Directions: up, right, down, left
        int[] rowOffsets = {-2, 0, 2, 0};
        int[] colOffsets = {0, 2, 0, -2};

        for (int r = 0; r < getBoardSize(); r++) {
            for (int c = 0; c < getBoardSize(); c++) {
                if (board[r][c] == SlotState.Marble) {
                    // Check all 4 directions
                    for (int i = 0; i < 4; i++) {
                        int newR = r + rowOffsets[i];
                        int newC = c + colOffsets[i];
                        int midR = r + rowOffsets[i] / 2;
                        int midC = c + colOffsets[i] / 2;

                        if (isValidPosition(newR, newC) &&
                                board[newR][newC] == SlotState.Empty &&
                                board[midR][midC] == SlotState.Marble) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int getBoardSize() {
        return armThickness * 3 - 2;
    }

    @Override
    public SlotState getSlotAt(int row, int col) {
        if (row < 0 || row >= getBoardSize() || col < 0 || col >= getBoardSize()) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        return board[row][col];
    }

    @Override
    public int getScore() {
        int count = 0;
        for (SlotState[] row : board) {
            for (SlotState slot : row) {
                if (slot == SlotState.Marble) count++;
            }
        }
        return count;
    }
}