package minesweeper.core;

/**
 * Mine tile.
 */
public class Mine extends Tile {
    @Override
    public String toString() {

        return String.format("%3s", "*");
    }

    @Override
    public void opOpen(Field field) {
        this.setState(State.OPEN);
        field.setState(GameState.FAILED);
    }
}
