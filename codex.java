import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class Game2048 extends Application {
    private static final int SIZE = 4;
    private static final int TARGET = 2048;

    private int[][] board = new int[SIZE][SIZE];
    private Random random = new Random();

    private Label[][] labels = new Label[SIZE][SIZE];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initializeGame();
        updateLabels();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid.add(labels[i][j], j, i);
            }
        }

        Scene scene = new Scene(grid, 300, 300);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                moveUp();
            } else if (event.getCode() == KeyCode.DOWN) {
                moveDown();
            } else if (event.getCode() == KeyCode.LEFT) {
                moveLeft();
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight();
            }

            updateLabels();
            checkGameOver();
        });

        primaryStage.setTitle("2048 Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeGame() {
        // Initialize the board with two random values (2 or 4)
        placeRandomValue();
        placeRandomValue();
    }

    private void placeRandomValue() {
        int value = (random.nextInt(2) + 1) * 2; // Either 2 or 4
        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (board[row][col] != 0);

        board[row][col] = value;
    }

    private void updateLabels() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                labels[i][j] = new Label(String.valueOf(board[i][j]));
            }
        }
    }
private void moveUp() {
    for (int col = 0; col < SIZE; col++) {
        for (int row = 1; row < SIZE; row++) {
            if (board[row][col] != 0) {
                int currentRow = row;
                while (currentRow > 0 && board[currentRow - 1][col] == 0) {
                    // Move the tile up
                    board[currentRow - 1][col] = board[currentRow][col];
                    board[currentRow][col] = 0;
                    currentRow--;
                }
                if (currentRow > 0 && board[currentRow - 1][col] == board[currentRow][col]) {
                    // Merge tiles
                    board[currentRow - 1][col] *= 2;
                    board[currentRow][col] = 0;
                }
            }
        }
    }
    placeRandomValue();
}

private void moveDown() {
    for (int col = 0; col < SIZE; col++) {
        for (int row = SIZE - 2; row >= 0; row--) {
            if (board[row][col] != 0) {
                int currentRow = row;
                while (currentRow < SIZE - 1 && board[currentRow + 1][col] == 0) {
                    // Move the tile down
                    board[currentRow + 1][col] = board[currentRow][col];
                    board[currentRow][col] = 0;
                    currentRow++;
                }
                if (currentRow < SIZE - 1 && board[currentRow + 1][col] == board[currentRow][col]) {
                    // Merge tiles
                    board[currentRow + 1][col] *= 2;
                    board[currentRow][col] = 0;
                }
            }
        }
    }
    placeRandomValue();
}

private void moveLeft() {
    for (int row = 0; row < SIZE; row++) {
        for (int col = 1; col < SIZE; col++) {
            if (board[row][col] != 0) {
                int currentCol = col;
                while (currentCol > 0 && board[row][currentCol - 1] == 0) {
                    // Move the tile left
                    board[row][currentCol - 1] = board[row][currentCol];
                    board[row][currentCol] = 0;
                    currentCol--;
                }
                if (currentCol > 0 && board[row][currentCol - 1] == board[row][currentCol]) {
                    // Merge tiles
                    board[row][currentCol - 1] *= 2;
                    board[row][currentCol] = 0;
                }
            }
        }
    }
    placeRandomValue();
}

private void moveRight() {
    for (int row = 0; row < SIZE; row++) {
        for (int col = SIZE - 2; col >= 0; col--) {
            if (board[row][col] != 0) {
                int currentCol = col;
                while (currentCol < SIZE - 1 && board[row][currentCol + 1] == 0) {
                    // Move the tile right
                    board[row][currentCol + 1] = board[row][currentCol];
                    board[row][currentCol] = 0;
                    currentCol++;
                }
                if (currentCol < SIZE - 1 && board[row][currentCol + 1] == board[row][currentCol]) {
                    // Merge tiles
                    board[row][currentCol + 1] *= 2;
                    board[row][currentCol] = 0;
                }
            }
        }
    }
    placeRandomValue();
}

private void checkGameOver() {
    // Check for 2048 or no more moves (game over)
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            if (board[i][j] == TARGET) {
                System.out.println("Congratulations! You reached 2048!");
                // You can handle game over actions here
                return;
            }
        }
    }

    // Check if there are no more moves
    boolean noMoves = true;
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            if (board[i][j] == 0 ||
                (i < SIZE - 1 && board[i][j] == board[i + 1][j]) ||
                (j < SIZE - 1 && board[i][j] == board[i][j + 1])) {
                noMoves = false;
                break;
            }
        }
        if (!noMoves) {
            break;
        }
    }

    if (noMoves) {
        System.out.println("Game over! No more moves.");
        // You can handle game over actions here
    }
}

