public class SOSBoard {
    private int n;  // Board size
    public enum Cell { EMPTY, B, R }

    private Cell[][] grid;
    private char turn;

    public SOSBoard(int n) {
        this.n = n;
        grid = new Cell[n][n];
        initGame();
    }

    private void initGame() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                grid[row][col] = Cell.EMPTY;
            }
        }
        turn = 'B';  // Start with the player blue
    }

    public int getBoardSize() {
        return n;
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < n && column >= 0 && column < n) {
            return grid[row][column];
        } else {
            return null;
        }
    }

    public char getTurn() {
        return turn;
    }
    
    public void setTurn(char t) {
    	turn = t;
    }

    public void makeMove(int row, int column) {
        if (row >= 0 && row < n && column >= 0 && column < n && grid[row][column] == Cell.EMPTY) {
            grid[row][column] = (turn == 'B') ? Cell.B : Cell.R;
            turn = (turn == 'B') ? 'R' : 'B';
        }
    }
}