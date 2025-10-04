package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Implementation of Triangle Marble Solitaire with a triangular board.
 * The board has a triangular shape where row i has i+1 positions.
 */
public class TriangleSolitaireModel implements MarbleSolitaireModel {
    private final int dimensions;
    private final SlotState[][] board;

    /**
     * Default constructor: creates a 5-row game with empty slot at (0,0).
     */
    public TriangleSolitaireModel() {
        this(5, 0, 0);
    }

    /**
     * Constructor with dimensions: creates a game with specified dimensions,
     * empty slot at (0,0).
     */
    public TriangleSolitaireModel(int dimensions) {
        this(dimensions, 0, 0);
    }

    /**
     * Constructor with position: creates a 5-row game,
     * empty slot at (row, col).
     */
    public TriangleSolitaireModel(int row, int col) {
        this(5, row, col);
    }

    /**
     * Full constructor: creates a game with specified dimensions,
     * empty slot at (sRow, sCol).
     */
    public TriangleSolitaireModel(int dimensions, int sRow, int sCol) {
        if (dimensions <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive");
        }

        this.dimensions = dimensions;
        this.board = new SlotState[dimensions][dimensions];

        // Initialize entire board
        for (int r = 0; r < dimensions; r++) {
            for (int c = 0; c < dimensions; c++) {
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
     * Check if a position is valid on the triangular board.
     * Row r has r+1 valid positions (from column 0 to column r).
     */
    private boolean isValidPosition(int row, int col) {
        return row < dimensions && col >= 0 && col <= row;
    }

    @Override
    public void move(int fromRow, int fromCol, int toRow, int toCol) {
        // Validate positions
        if (!isValidPosition(fromRow, fromCol) || !isValidPosition(toRow, toCol)) {
            throw new IllegalArgumentException("Invalid position");
        }

        // Check source has a marble and destination is empty
        if (board[fromRow][fromCol] != SlotState.Marble) {
            throw new IllegalArgumentException("No marble at source position");
        }
        if (board[toRow][toCol] != SlotState.Empty) {
            throw new IllegalArgumentException("Destination is not empty");
        }

        // For triangular board, valid moves are:
        // 1. Horizontal: same row, 2 columns apart
        // 2. Diagonal left: 2 rows apart, 2 columns apart (both decrease)
        // 3. Diagonal right: 2 rows apart, same column (row changes by 2)

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;
        int midRow = 0;
        int midCol = 0;
        boolean validMove = false;

        // Horizontal move (same row, jump 2 columns)
        if (rowDiff == 0 && Math.abs(colDiff) == 2) {
            midRow = fromRow;
            midCol = (fromCol + toCol) / 2;
            validMove = true;
        }
        // Diagonal moves (up or down)
        else if (Math.abs(rowDiff) == 2) {
            // Moving down-right or up-left (row and col change by same amount)
            if (rowDiff == colDiff) {
                midRow = (fromRow + toRow) / 2;
                midCol = (fromCol + toCol) / 2;
                validMove = true;
            }
            // Moving down-left or up-right (row changes, col stays same)
            else if (colDiff == 0) {
                midRow = (fromRow + toRow) / 2;
                midCol = fromCol;
                validMove = true;
            }
        }

        if (!validMove) {
            throw new IllegalArgumentException("Invalid move: not a valid jump");
        }

        // Check middle position has a marble
        if (!isValidPosition(midRow, midCol) || board[midRow][midCol] != SlotState.Marble) {
            throw new IllegalArgumentException("No marble to jump over");
        }

        // Execute move
        board[fromRow][fromCol] = SlotState.Empty;
        board[midRow][midCol] = SlotState.Empty;
        board[toRow][toCol] = SlotState.Marble;
    }

    @Override
    public boolean isGameOver() {
        // Check all positions for possible moves
        for (int r = 0; r < dimensions; r++) {
            for (int c = 0; c <= r; c++) {
                if (board[r][c] == SlotState.Marble) {
                    // Check all 6 possible move directions
                    if (canMoveTo(r, c, r, c - 2) ||      // Left
                            canMoveTo(r, c, r, c + 2) ||      // Right
                            canMoveTo(r, c, r - 2, c - 2) ||  // Up-left
                            canMoveTo(r, c, r - 2, c) ||      // Up-right
                            canMoveTo(r, c, r + 2, c) ||      // Down-left
                            canMoveTo(r, c, r + 2, c + 2)) {  // Down-right
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Helper method to check if a move is valid.
     */
    private boolean canMoveTo(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isValidPosition(toRow, toCol)) {
            return false;
        }
        if (board[toRow][toCol] != SlotState.Empty) {
            return false;
        }

        // Check for valid jump with marble in middle
        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;
        int midRow, midCol;

        // Horizontal move
        if (rowDiff == 0 && Math.abs(colDiff) == 2) {
            midRow = fromRow;
            midCol = (fromCol + toCol) / 2;
        }
        // Diagonal moves
        else if (Math.abs(rowDiff) == 2) {
            if (rowDiff == colDiff) {  // Down-right or up-left
                midRow = (fromRow + toRow) / 2;
                midCol = (fromCol + toCol) / 2;
            } else if (colDiff == 0) {  // Down-left or up-right
                midRow = (fromRow + toRow) / 2;
                midCol = fromCol;
            } else {
                return false;
            }
        } else {
            return false;
        }

        return isValidPosition(midRow, midCol) && board[midRow][midCol] == SlotState.Marble;
    }

    @Override
    public int getBoardSize() {
        return dimensions;
    }

    @Override
    public SlotState getSlotAt(int row, int col) {
        if (row < 0 || row >= dimensions || col < 0 || col >= dimensions) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        return board[row][col];
    }

    @Override
    public int getScore() {
        int count = 0;
        for (int r = 0; r < dimensions; r++) {
            for (int c = 0; c <= r; c++) {
                if (board[r][c] == SlotState.Marble) {
                    count++;
                }
            }
        }
        return count;
    }
}