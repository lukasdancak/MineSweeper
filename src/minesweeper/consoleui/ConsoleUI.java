package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

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
    Pattern pattern = Pattern.compile("([OA]{1})([A-Z])([0-9]{1,2})");

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
        StringBuilder hornaOs = new StringBuilder("   ");
        for (int i = 0; i < field.getColumnCount(); i++) {
            hornaOs.append(String.format("%3s", i));
        }
        System.out.println(hornaOs);

        //vypis riadky so zvislo osou na zaciatku
        for (int i = 0; i < this.field.getRowCount(); i++) {
            System.out.printf("%3s", Character.toString(i + 65));
            for (int j = 0; j < this.field.getColumnCount(); j++) {
                if (this.field.getTile(i, j).getState() == Tile.State.OPEN) {
                    System.out.print(this.field.getTile(i, j).toString());
                }
                if (this.field.getTile(i, j).getState() == Tile.State.MARKED) {
                    System.out.printf("%3s", "M");
                }
                if (this.field.getTile(i, j).getState() == Tile.State.CLOSED) {
                    System.out.printf("%3s", "-");
                }

            }
            System.out.println();
        }


    }


    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        System.out.println("Zadaj svoj vstup.");
        System.out.println("Ocakavany vstup:  X – ukončenie hry, MA1 – označenie dlaždice v riadku A a stĺpci 1, OB4 – odkrytie dlaždice v riadku B a stĺpci 4. ");
        String playerInput = readLine();
        if(!isInputCorrect(playerInput)){ processInput();}
        if(!isInputInBorderOfField(playerInput)){ processInput();}
        

        doOperation(playerInput);


    }

    private void doOperation(String playerInput) {
        System.out.println("Vykonal som operaciu");
    }

    private boolean isInputInBorderOfField(String playerInput) {
        boolean result = true;
        String suradnicaZvislaPismeno = pattern.matcher(playerInput).group(2);
        String suradnicaHorizontalnaCislo = pattern.matcher(playerInput).group(3);

        if( (int)suradnicaZvislaPismeno.charAt(0) >= (65+ field.getRowCount())) {
            result=false;
            System.out.println("Zadane pismeno prekracuje pocet riadkov hracieho pola. Opakuj vstup");
        }
        if( Integer.parseInt(suradnicaHorizontalnaCislo) >= field.getColumnCount()) {
            result=false;
            System.out.println("Zadane cislo prekracuje pocet stlpcov hracieho pola. Opakuj vstup");

        }

        return result;
    }

    private boolean isInputCorrect(String playerInput) {
        return pattern.matcher(playerInput).find();

    }
    
    


}
