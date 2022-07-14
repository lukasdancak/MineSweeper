package minesweeper.core;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;

    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    private final int rowCount;

    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    private final int columnCount;

    /**
     * Mine count.
     */
    private final int mineCount;

    /**
     * Game state.
     */
    private GameState state = GameState.PLAYING;

    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     * @param mineCount   mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        tiles = new Tile[rowCount][columnCount];

        //generate the field content
        generate();

       // printField(); // vypise cisto iba field bez osi
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void openTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.OPEN);
            if (tile instanceof Mine) {
                state = GameState.FAILED;
                return;
            }

            if (isSolved()) {
                state = GameState.SOLVED;
                return;
            }
        }
    }

    /**
     * Marks tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void markTile(int row, int column) {
        //toto dat prec : // throw new UnsupportedOperationException("Method markTile not yet implemented");

        if (getTile(row, column).getState() == Tile.State.OPEN) {
            return;
        }
        if (getTile(row, column).getState() == Tile.State.MARKED) {
            getTile(row, column).setState(Tile.State.CLOSED);
        } else {
            {
                getTile(row, column).setState(Tile.State.MARKED);
            }
        }
    }


    /**
     * Generates playing field.
     */
    private void generate() {
        System.out.println("Metoda generate():");

        // ulozenie min
        int pocetMinNaUlozenie = this.getMineCount();
        Random r = new Random();
        //Random c = new Random();
        int randomRow = 0;
        int randomColumn = 0;

        while (pocetMinNaUlozenie > 0) {
            randomRow = r.nextInt(this.getRowCount());
            randomColumn = r.nextInt(this.getColumnCount());
            if (this.getTile(randomRow, randomColumn) == null) {
                tiles[randomRow][randomColumn] = new Mine();
               // tiles[randomRow][randomColumn].setState(Tile.randomGameState()); // nastavi nahodny State
                pocetMinNaUlozenie--;
            }


        }
        //ulozenie Clues
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                if (this.getTile(i, j) == null) {
                    tiles[i][j] = new Clue(this.countAdjacentMines(i, j));
                    //tiles[i][j].setState(Tile.randomGameState()); // nastavi nahodny State
                }
            }
        }


    }

    public void printField() {
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                System.out.print(this.getTile(i, j).toString());
            }
            System.out.println();
        }
    }

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    private boolean  isSolved() {

        int solvedTiles = 0;
        for (Tile[] tArray : this.tiles) {
            for (Tile t : tArray) {
                if (t.getState() == Tile.State.OPEN) {
                    solvedTiles++;
                }
            }
        }
        if ((this.getColumnCount() * this.getRowCount() - this.getMineCount()) == solvedTiles) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < rowCount) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < columnCount) {
                        if (tiles[actRow][actColumn] instanceof Mine) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }


    /***
     *@return specific Tile by row and column
     */

    public Tile getTile(int row, int column) {
        return this.tiles[row][column];
    }

    /**
     * @return whole array of tiles
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * @return rowCount
     **/
    public int getRowCount() {
        return rowCount;
    }


    /**
     * @return columnCount
     */
    public int getColumnCount() {
        return columnCount;
    }


    /**
     * @return variable mineCount
     */
    public int getMineCount() {
        return mineCount;
    }

    /**
     * @return variable state
     */
    public GameState getState() {
        return state;
    }
}
