package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import minesweeper.core.Field;
import minesweeper.core.Tile;

/**
 * Console user interface.
 */
public class ConsoleUI implements minesweeper.UserInterface {
    /**
     * Playing field.
     */
    private Field field;

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Starts the game.
     *
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();
            //throw new UnsupportedOperationException("Resolve the game state - winning or loosing condition.");
        } while (true);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        System.out.println("Metoda update():");
        //vypis horizontalnu os
        StringBuilder hornaOs= new StringBuilder("   ");
        for(int i=0;i< field.getColumnCount();i++){
            hornaOs.append(String.format("%3s",i));
        }
        System.out.println(hornaOs);

        //vypis riadky so zvislo osou na zaciatku
        for (int i = 0; i < this.field.getRowCount(); i++) {
            System.out.printf("%3s",Character.toString(i+65));
            for (int j = 0; j < this.field.getColumnCount(); j++) {
                if(this.field.getTile(i, j).getState()== Tile.State.OPEN) {System.out.print(this.field.getTile(i, j).toString());}
                if (this.field.getTile(i, j).getState()== Tile.State.MARKED){
                    System.out.printf("%3s","M");
                }
                if (this.field.getTile(i, j).getState()== Tile.State.CLOSED){
                    System.out.printf("%3s","C");
                }

            }
            System.out.println();
        }

readLine();
    }


    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        //throw new UnsupportedOperationException("Method processInput not yet implemented");
    }
}
