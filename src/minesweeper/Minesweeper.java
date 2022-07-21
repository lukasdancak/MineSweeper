package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.BestTimes;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
    /**
     * User interface.
     */
    private UserInterface userInterface;
    private long startMillis;
    private static Minesweeper instance;
    BestTimes bestTimes;

    /**
     * Constructor.
     */
    private Minesweeper() {
        instance=this;
        userInterface = new ConsoleUI();
        Field field = new Field(9, 9, 10);
        startMillis=System.currentTimeMillis();
        bestTimes = new BestTimes();
        userInterface.newGameStarted(field);


    }
    public static Minesweeper getInstance(){
        if(instance==null){ instance=new Minesweeper();}
        return instance;

    }

    public int getPlayingSeconds (){
        return (int) (System.currentTimeMillis()-startMillis)/1000;
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello " + System.getProperty("user.name") + " !");
        new Minesweeper();

    }

    public BestTimes getBestTimes() {
        return bestTimes;
    }
}
