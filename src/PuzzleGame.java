import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class PuzzleGame {


    static class Tile implements Comparable<Tile> {
        int row;
        int col;
        int g;
        int h;
        int f;
        Tile parent;
        Tile left, right, up, down;


        Tile(int row, int col) {
            this.row = row;
            this.col = col;
            this.g = 0;
            this.h = 0;
            this.f = 0;
        }


        @Override
        public int compareTo(Tile other) {
            return Integer.compare(this.f, other.f);
        }
    }


    private int countLines(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        int lines = 0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            lines++;
        }
        return lines;
    }


    public String[][] loadPuzzle(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            int lines = countLines(filePath);
            String[][] puzzle = new String[lines][];
            int index = 0;
            while (scanner.hasNextLine()) {
                puzzle[index++] = scanner.nextLine().split("");
            }
            return puzzle;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int[] findStart(String[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].equals("S")) {
                    grid[row][col] = ".";
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }


    public int[] findFinish(String[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].equals("F")) {
                    grid[row][col] = ".";
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }


    public Tile[][] createBoard(String[][] grid) {
        Tile[][] board = new Tile[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].equals(".")) {
                    Tile tile = new Tile(row, col);
                    if (col > 0 && grid[row][col - 1].equals(".")) {
                        tile.left = board[row][col - 1];
                        board[row][col - 1].right = tile;
                    }
                    if (row > 0 && grid[row - 1][col].equals(".")) {
                        tile.up = board[row - 1][col];
                        board[row - 1][col].down = tile;
                    }
                    board[row][col] = tile;
                }
            }
        }
        return board;
    }


    private int heuristic(Tile a, Tile b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }


    public void solve(Tile start, Tile end) {

        PriorityQueue<Tile> openList = new PriorityQueue<>();

        Set<Tile> closedList = new HashSet<>();


        start.g = 0;
        start.h = heuristic(start, end);
        start.f = start.g + start.h;
        openList.add(start);


        while (!openList.isEmpty()) {

            Tile current = openList.poll();


            if (current.equals(end)) {
                printPath(current);
                return;
            }


            closedList.add(current);


            for (Tile neighbor : Arrays.asList(current.left, current.right, current.up, current.down)) {
                if (neighbor == null || closedList.contains(neighbor)) continue;


                int tentative_g = current.g + 1;


                if (!openList.contains(neighbor) || tentative_g < neighbor.g) {

                    neighbor.g = tentative_g;
                    neighbor.h = heuristic(neighbor, end);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;


                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
    }

    
    private void printPath(Tile end) {
        List<Tile> steps = new ArrayList<>();
        for (Tile at = end; at != null; at = at.parent) {
            steps.add(at);
        }
        Collections.reverse(steps);

        System.out.println("Path from start to finish:");
        int stepNumber = 1;
        for (Tile step : steps) {
            System.out.printf("%d. Move to (%d, %d)\n", stepNumber, step.col + 1, step.row + 1);
            stepNumber++;
        }
    }
}
