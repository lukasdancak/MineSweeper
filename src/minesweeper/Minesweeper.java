package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
    /**
     * User interface.
     */
    private ConsoleUI userInterface;

    /**
     * Constructor.
     */
    private Minesweeper() {
        userInterface = new ConsoleUI();

        Field field = new Field(9, 9, 10);
        userInterface.newGameStarted(field);
        System.err.println("Vytvoreny novy objekt MineSweeper");
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello " + System.getProperty("user.name")+" !");
        new Minesweeper();
    }
}
