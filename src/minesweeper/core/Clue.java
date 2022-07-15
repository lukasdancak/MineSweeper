package minesweeper.core;

/**
 * Clue tile.
 */
public class Clue extends Tile {
    private final int value;

    /**
     * Constructor.
     *
     * @param value value of the clue
     */
    public Clue(int value) {
        this.value = value;
    }

    /**
     * Value of the clue.
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%3s", Integer.toString(value));
    }

    @Override
    public void opOpen(Field field) {
        this.setState(State.OPEN);
        field.searchForOpenCluesWithZero(); // takto ?
        if (field.isSolved()){field.setState(GameState.SOLVED);}

    }
}
