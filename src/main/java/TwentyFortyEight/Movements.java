package TwentyFortyEight;

import java.util.ArrayList;
import java.util.Random;

public class Movements {
    
    /**
     * Moves all tiles to the left, merging where possible
     * @param board The game board
     * @return true if any tile moved, false otherwise
     */
    public static boolean moveLeft(Cell[][] board) {
        boolean moved = false;
        int gridSize = board.length;
    
        for (int row = 0; row < gridSize; row++) {
            int[] values = new int[gridSize];
            int index = 0;
    
            // Step 1: Collect non-zero values
            for (int col = 0; col < gridSize; col++) {
                int val = board[row][col].getValue();
                if (val != 0) values[index++] = val;
            }
    
            // Step 2: Merge
            for (int i = 0; i < index - 1; i++) {
                if (values[i] == values[i + 1]) {
                    values[i] *= 2;
                    values[i + 1] = 0;
                    moved = true;
                }
            }
    
            // Step 3: Compress again
            int[] newValues = new int[gridSize];
            index = 0;
            for (int val : values) {
                if (val != 0) newValues[index++] = val;
            }
    
            // Step 4: Update board
            for (int col = 0; col < gridSize; col++) {
                if (board[row][col].getValue() != newValues[col]) moved = true;
                board[row][col].setValue(newValues[col]);
            }
        }
    
        return moved;
    }

    /**
     * Moves all tiles to the right, merging where possible
     * @param board The game board
     * @return true if any tile moved, false otherwise
     */
    public static boolean moveRight(Cell[][] board) {
        boolean moved = false;
        int gridSize = board.length;
    
        for (int row = 0; row < gridSize; row++) {
            int[] values = new int[gridSize];
            int index = 0;
    
            // Step 1: Collect non-zero values from right to left
            for (int col = gridSize - 1; col >= 0; col--) {
                int val = board[row][col].getValue();
                if (val != 0) {
                    values[index] = val;
                    index++;
                }
            }
    
            // Step 2: Merge adjacent same values
            for (int i = 0; i < index - 1; i++) {
                if (values[i] == values[i + 1]) {
                    values[i] *= 2;
                    values[i + 1] = 0;
                    moved = true;
                }
            }
    
            // Step 3: Compress again
            int[] newValues = new int[gridSize];
            int newIndex = 0;
            for (int val : values) {
                if (val != 0) {
                    newValues[newIndex++] = val;
                }
            }
    
            // Step 4: Update board from right to left
            for (int col = 0; col < gridSize; col++) {
                // Determine the value for the current column
                int value;
                if (col < newIndex) {
                    value = newValues[col];  // Get the new value from newValues array
                } else {
                    value = 0;  // Set value to 0 if col is not less than newIndex
                }

                // Check if the value on the board is different from the calculated value
                if (board[row][gridSize - 1 - col].getValue() != value) {
                    moved = true;  // Mark that something has moved
                }

                // Update the value on the board
                board[row][gridSize - 1 - col].setValue(value);
            }
        }
    
        return moved;
    }

    /**
     * Moves all tiles upward, merging where possible
     * @param board The game board
     * @return true if any tile moved, false otherwise
     */
    public static boolean moveUp(Cell[][] board) {
        boolean moved = false;
        int gridSize = board.length;
    
        // Loop through each column
        for (int col = 0; col < gridSize; col++) {
            int[] values = new int[gridSize];
            int index = 0;
    
            // Step 1: Collect non-zero values from top to bottom
            for (int row = 0; row < gridSize; row++) {
                int val = board[row][col].getValue();
                if (val != 0) {
                    values[index++] = val;
                }
            }
    
            // Step 2: Merge adjacent same values
            for (int i = 0; i < index - 1; i++) {
                if (values[i] == values[i + 1]) {
                    values[i] *= 2;
                    values[i + 1] = 0;
                    moved = true;
                }
            }
    
            // Step 3: Compress again (remove zeros)
            int[] newValues = new int[gridSize];
            int newIndex = 0;
            for (int val : values) {
                if (val != 0) {
                    newValues[newIndex++] = val;
                }
            }
    
            // Step 4: Update board from top to bottom
            for (int row = 0; row < gridSize; row++) {
                int value;
                if (row < newIndex) {
                    value = newValues[row];  // Get the new value
                } else {
                    value = 0;  // Set value to 0 if row is not less than newIndex
                }
    
                // Check if the value on the board is different from the calculated value
                if (board[row][col].getValue() != value) {
                    moved = true;  // Mark that something has moved
                }
    
                // Update the value on the board
                board[row][col].setValue(value);
            }
        }
    
        return moved;
    }
    
    /**
     * Moves all tiles downward, merging where possible
     * @param board The game board
     * @return true if any tile moved, false otherwise
     */
    public static boolean moveDown(Cell[][] board) {
        boolean moved = false;
        int gridSize = board.length;
    
        for (int col = 0; col < gridSize; col++) {
            int[] values = new int[gridSize];
            int index = 0;
    
            // Step 1: Collect non-zero values from bottom to top
            for (int row = gridSize - 1; row >= 0; row--) {
                int val = board[row][col].getValue();
                if (val != 0) {
                    values[index++] = val;
                }
            }
    
            // Step 2: Merge adjacent same values
            for (int i = 0; i < index - 1; i++) {
                if (values[i] == values[i + 1]) {
                    values[i] *= 2;
                    values[i + 1] = 0;
                    moved = true;
                }
            }
    
            // Step 3: Compress again (remove zeros)
            int[] newValues = new int[gridSize];
            int newIndex = 0;
            for (int i = 0; i < index; i++) {
                if (values[i] != 0) {
                    newValues[newIndex++] = values[i];
                }
            }
    
            // Step 4: Update board from bottom to top
            for (int row = 0; row < gridSize; row++) {
                int newValue;
                if (row >= gridSize - newIndex) {
                    // Fill bottom rows with values
                    newValue = newValues[gridSize - 1 - row];
                } else {
                    // Fill top rows with zeros
                    newValue = 0;
                }
    
                // Check if the value on the board is different from the calculated value
                if (board[row][col].getValue() != newValue) {
                    moved = true;
                }
    
                // Update the value on the board
                board[row][col].setValue(newValue);
            }
        }
    
        return moved;
    }

    public static void spawnRandomTile(Cell[][] board) {
        int gridSize = board.length;
        ArrayList<Cell> emptyCells = new ArrayList<>();
        
        // Find all empty cells
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (board[row][col].getValue() == 0) {
                    emptyCells.add(board[row][col]);
                }
            }
        }
        
        // Spawn a tile only if there are empty cells
        if (!emptyCells.isEmpty()) {
            Random random = new Random();
            Cell chosen = emptyCells.get(random.nextInt(emptyCells.size()));
            
            // 90% chance for a 2, 10% chance for a 4
            int value = random.nextDouble() < 0.9 ? 2 : 4;
            chosen.setValue(value);
        }
    }

}