package minesweeper.core;

/**
 * Mine tile.
 */
public class Mine extends Tile {
    @Override
    public String toString() {

        return String.format("%3s", "*");
    }
}
