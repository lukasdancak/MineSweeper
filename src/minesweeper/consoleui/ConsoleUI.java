package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Tile;

/**
 * Console user interface.
 */
public class ConsoleUI implements minesweeper.UserInterface {
    /**
     * Playing field. MA1 OB99
     */
    private Field field;
    Pattern pattern1 = Pattern.compile("([OMU]{1})([A-Z]{1})([0-9]{1,2})");
    // pattern pre jednopismenkove prikazy - zatial X - exit
    Pattern pattern2 = Pattern.compile("([X]{1})");


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

            if (field.getState() == GameState.FAILED) {
                System.out.println("Odkryl si minu. Prehral si");
                break;
            }
            if (field.getState() == GameState.SOLVED) {
                System.out.println("Vyhral si");
                break;
            }
        } while (true);
        System.exit(0);

    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        //System.out.println("Metoda update():");
        System.out.printf("Pocet poli neoznacenych ako mina je %s (pocet min: %s)%n", field.getRemainingMineCount(), field.getMineCount());

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
        System.out.println("Ocakavany vstup:  X – ukončenie hry, M - mark, O - open, U - unmark. Napr.: MA1 – označenie dlaždice v riadku A a stĺpci 1");
        String playerInput = readLine();
        Matcher matcher1 = pattern1.matcher(playerInput);
        Matcher matcher2 = pattern2.matcher(playerInput);

        // overi format vstupu - exception handling
        try {
            handleInput(playerInput);
        } catch (WrongFormatException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            processInput();
            return;

        }
//        //overi vstup s patternom
//        if (!pattern.matcher(playerInput).matches()) {
//            System.out.println("!!! Zadal si nespravny format vstupu, opakuj vstup.");
//            processInput();
//            return;
//        }
        System.out.println("// Format vstupu spravny");

        matcher1.find();

//        //pomocny vypis - vypise hodnoty v group-ach
//        if (matcher1.find()) {
//            System.out.print("// PlayerInput: " + matcher1.group(0));
//            System.out.print(" | group1: " + matcher1.group(1));
//            System.out.print(" | group2: " + matcher1.group(2));
//            System.out.println(" | group3: " + matcher1.group(3));
//        } else {
//            System.out.println("NO MATCH");
//        }

        //overi ci suradnica nie je mimo hracie pole
        if (!isInputInBorderOfField(matcher1.group(2), matcher1.group(3))) {
            System.out.println("");
            processInput();
            return;
        }
        //vykona operaciu
        if(pattern1.matcher(playerInput).matches()) {
            doOperation(matcher1.group(1).charAt(0), matcher1.group(2).charAt(0), Integer.parseInt(matcher1.group(3)));
        }

        if(pattern2.matcher(playerInput).matches()) {
            doOperation(playerInput);
        }


    }

    private void doOperation(String playerInput) {
        // X - ukoncenie hry // nefunguje, lebo X vkladam do metody iba X bez suradnic - prerobit
        if (playerInput.equalsIgnoreCase("X")) {
            System.out.println("Ukoncujem hru");
            System.exit(0);
        }

    }

    private void doOperation(char operation, char osYRow, int osXCol) {

        int osYRowInt = osYRow - 65;

        // M - oznacenie dlzadice
        if (operation == 'M') {
            field.getTile(osYRowInt, osXCol).opMark();

        }

        // U - odznacenie dlazdice
        if (operation == 'U') {
            field.getTile(osYRowInt, osXCol).opUnMark();

        }

        // O - Odkrytie dlazdice
        if (operation == 'O') {
            if (field.getTile(osYRowInt, osXCol).getState() == Tile.State.MARKED) {
                System.out.println("!!! Nie je mozne odkryt dlazdicu v stave MARKED");
                return;
            } else {
                field.getTile(osYRowInt, osXCol).opOpen(field);
            }

        }

        System.out.println("Vykonal som pozadovanu operaciu");
    }

    private boolean isInputInBorderOfField(String suradnicaZvislaPismeno, String suradnicaHorizontalnaCislo) {
        boolean result = true;


        if ((int) suradnicaZvislaPismeno.charAt(0) >= (65 + field.getRowCount())) {
            result = false;
            System.out.print("!!! Pismeno prekracuje pocet riadkov.");
        }
        if (Integer.parseInt(suradnicaHorizontalnaCislo) >= field.getColumnCount()) {
            result = false;
            System.out.print(" !!! Cislo prekracuje pocet stlpcov.");

        }
        if (!result) {
            System.out.println(" Opakuj vstup.");
        }

        return result;
    }

    void handleInput(String playerInput) throws WrongFormatException {

        //overi vstup s patternom
        if (!pattern1.matcher(playerInput).matches() && !pattern2.matcher(playerInput).matches()) {
//            System.out.println("!!! Zadal si nespravny format vstupu, opakuj vstup.");
            throw new WrongFormatException("!!! Zadal si nespravny format vstupu, opakuj vstup.");
//            processInput();
//            return;
        }
    }

}
