package minesweeper.core;

import java.util.Random;

/**
 * Tile of a field.
 */
public abstract class Tile {

    public void opMark() {
        this.setState(State.MARKED);
    }

    /**
     * Tile states.
     */
    public enum State {
        /**
         * Open tile.
         */
        OPEN,
        /**
         * Closed tile.
         */
        CLOSED,
        /**
         * Marked tile.
         */
        MARKED

    }

    /**
     * Tile state.
     */
    private State state = State.CLOSED;

    /**
     * Returns current state of this tile.
     *
     * @return current state of this tile
     */
    public State getState() {
        return state;
    }

    /**
     * Sets current current state of this tile.
     *
     * @param state current state of this tile
     */
    public void setState(State state) {
        this.state = state;
    }


    /**
     * Metoda vrati nahodnu hodnotu z Enum State
     *
     * @return vrati State
     */
    public static State randomGameState() {
        int pick = new Random().nextInt(GameState.values().length);
        return State.values()[pick];
    }
}
