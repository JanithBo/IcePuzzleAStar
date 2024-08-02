import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PuzzleSolver {

    public static void main(String[] args) {

        String directoryPath = "/home/bk/Desktop/IcePuzzleAStar/benchmark_series/";


        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input the file name (e.g., puzzle_x.txt): ");


        String file = scanner.nextLine();


        System.out.println("\nSelected File: " + file);


        PuzzleGame game = new PuzzleGame();


        String[][] grid = game.loadPuzzle(directoryPath + file);


        long startTime = System.currentTimeMillis();


        int[] start = game.findStart(grid);
        int[] finish = game.findFinish(grid);


        PuzzleGame.Tile[][] board = game.createBoard(grid);


        PuzzleGame.Tile startingTile = board[start[0]][start[1]];
        PuzzleGame.Tile finishingTile = board[finish[0]][finish[1]];


        game.solve(startingTile, finishingTile);


        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;


        System.out.println("-------------------------------------------------");
        System.out.println("       Execution Time (milliseconds)             " + elapsedTime);
        System.out.println("-------------------------------------------------");
        System.out.println(String.format("%20s %20s \n", "File name", file));
    }
}
