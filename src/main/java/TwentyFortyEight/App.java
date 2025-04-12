package TwentyFortyEight;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static int GRID_SIZE = 4; // 4x4 grid
    public static final int CELLSIZE = 100; // Cell size in pixels
    public static final int CELL_BUFFER = 8; // Space between cells
    public static int WIDTH;
    public static int HEIGHT;
    public static final int FPS = 30;

    private Cell[][] board;
    public static Random random = new Random();

    private PFont font;
    public PImage eight;

    private long startTime;
    private boolean gameOver = false;

    public App() {
        this.board = new Cell[GRID_SIZE][GRID_SIZE];
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    @Override
    public void setup() {
        frameRate(FPS);
        this.eight = loadImage(this.getClass().getResource("8.png").getPath().replace("%20", ""));

        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board[i].length; i2++) {
                board[i][i2] = new Cell(i2, i);
            }
        }
        Movements.spawnRandomTile(board);
        Movements.spawnRandomTile(board);
        startTime = millis();
        gameOver = false;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        boolean moved = false;

        if (gameOver && (key == 'r' || key == 'R')) {
            restartGame();
            return;
        }

        if (!gameOver) {
            if (keyCode == LEFT) {
                moved = Movements.moveLeft(board);
            } else if (keyCode == RIGHT) {
                moved = Movements.moveRight(board);
            } else if (keyCode == UP) {
                moved = Movements.moveUp(board);
            } else if (keyCode == DOWN) {
                moved = Movements.moveDown(board);
            }
            if (moved) {
                Movements.spawnRandomTile(board);
                if (isGameOver()) {
                    gameOver = true;
                }
            }
        }
    }

    @Override
    public void keyReleased() {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver && e.getButton() == PConstants.LEFT) {
            int row = e.getY() / App.CELLSIZE;
            int col = e.getX() / App.CELLSIZE;
            if (row < GRID_SIZE && col < GRID_SIZE && board[row][col].getValue() == 0) {
                board[row][col].place();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void draw() {
        background(150, 75, 0);
        this.textSize(40);
        this.strokeWeight(15);

        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board[i].length; i2++) {
                board[i][i2].draw(this);
            }
        }

        // Timer in top-right corner
        if (!gameOver) {
            int elapsedTime = (int) ((millis() - startTime) / 1000);
            fill(255);
            textSize(24);
            textAlign(RIGHT, TOP);
            text("Time: " + elapsedTime + "s", WIDTH - 10, 10);
        }

        // Game over message
        if (gameOver) {
            textAlign(CENTER, CENTER);
            textSize(50);
            fill(255, 0, 0);
            text("GAME OVER", WIDTH / 2, HEIGHT / 2);
        }
    }

    private boolean isGameOver() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                int current = board[y][x].getValue();
                if (current == 0) return false;

                // Check right
                if (x + 1 < GRID_SIZE && board[y][x + 1].getValue() == current) return false;
                // Check down
                if (y + 1 < GRID_SIZE && board[y + 1][x].getValue() == current) return false;
            }
        }
        return true;
    }

    private void restartGame() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                board[y][x] = new Cell(x, y);
            }
        }
        Movements.spawnRandomTile(board);
        Movements.spawnRandomTile(board);
        startTime = millis();
        gameOver = false;
    }

    public static void main(String[] args) {
        if(args.length > 0){
            GRID_SIZE = Integer.parseInt(args[0]);
        } else {
            GRID_SIZE = 4;
        }
        WIDTH = GRID_SIZE * CELLSIZE;
        HEIGHT = GRID_SIZE * CELLSIZE;
        PApplet.main("TwentyFortyEight.App");
    }
}