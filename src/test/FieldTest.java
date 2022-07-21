package test;

import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;
import org.junit.jupiter.api.Test;


import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private Random randomGenerator = new Random();
    private Field field;
    private int rowCount;
    private int columnCount;
    private int minesCount;

    public FieldTest() {
        rowCount = randomGenerator.nextInt(10) + 5;
        columnCount = rowCount;
        minesCount = Math.max(1, randomGenerator.nextInt(rowCount * columnCount));
        field = new Field(rowCount, columnCount, minesCount);
    }

    @Test
    public void checkMinesCount() {
        int minesCounter = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if (field.getTile(row, column) instanceof Mine) {
                    minesCounter++;
                }
            }
        }

        assertEquals(minesCount, minesCounter, "Field was initialized incorrectly - " +
                "a different amount of mines was counted in the field than amount given in the constructor.");
    }

    @Test
    public void fieldWithTooManyMines() {
        Field fieldWithTooManyMines = null;
        int higherMineCount = rowCount * columnCount + randomGenerator.nextInt(10) + 1;
        try {
            fieldWithTooManyMines = new Field(rowCount, columnCount, higherMineCount);
        } catch (Exception e) {
            // field with more mines than tiles should not be created - it may fail on exception
        }
        assertTrue((fieldWithTooManyMines == null) || (fieldWithTooManyMines.getMineCount() <= (rowCount * columnCount)));
    }

    // ... dalsie testy


    /**
     * //    Ci bola spravne nainicializovana velkost pola (getRowCount, getColumnCount, getMineCount)
     * //    Ci stav pola je playing
     */
    @Test
    public void checkFieldInitialization() {
        int rowCounter = field.getRowCount();
        int columnCounter = field.getColumnCount();
        assertEquals(rowCount, rowCounter, "Field was initialized incorrectly - " +
                "a different amount of rows was counted in the field than amount given in the constructor.");
        assertEquals(columnCount, columnCounter, "Field was initialized incorrectly - " +
                "a different amount of columns was counted in the field than amount given in the constructor.");

    }

    /**
     * Marknut nahodnu dlazdicu a checknut, ci potom je marknuta
     * To iste pre unmark
     * Bonus: testnut, aby sa nedala marknut otvorena dlazdica
     */
    @Test
    public void checkMarkTile() {
        int row = randomGenerator.nextInt(rowCount);
        int col = randomGenerator.nextInt(columnCount);
        Tile randomTile = field.getTile(row, col);
        Tile.State state1 = randomTile.getState();
        field.markTile(row, col);
        field.markTile(row, col);
        Tile.State state2 = randomTile.getState();
        assertEquals(state1, state2, "chyba, metoda mark nefunguje spravne");
    }

    /**
     * Ako otestovat mark mine?
     * Ci je open a ci game state je failed
     */
    public void checkOpenMine() {
    }


    /**
     *
     */
    @Test
    public void checkOpenClue() {
        //***** Najst si v poli dlazdicu s hodnotou inou ako 0, otvorit a testnut, ci otvorilo len jednu dlazdicu a ci stav pola zostava nadalej PLAYING
        Tile t;
        do {
            t = field.getTile(randomGenerator.nextInt(rowCount), randomGenerator.nextInt(columnCount));

        } while (t.toString().equalsIgnoreCase("0") || t.toString().equalsIgnoreCase("*"));
        t.setState(Tile.State.OPEN);
        assertEquals(GameState.PLAYING, field.getState(), "chyba, po otvoreni CLUE sa zmenil GameState");

        //  ******    Najst si v poli dlazdicu s hodnotou 0, otvorit a checknut, ci su otvorene len dlazdice typu Clue a ci stav pola zostava nadalej PLAYING
        Tile t2;
        int rRow;
        int rCol;

        do {
            rRow = randomGenerator.nextInt(rowCount);
            rCol = randomGenerator.nextInt(columnCount);
            t2 = field.getTile(rRow, rCol);

        } while (!t2.toString().equalsIgnoreCase("0"));
        field.openTile(rRow, rCol);
        // tu ma byt test ci su otvorene len dlazdice tyou clue - nemam dorobene openAdjescentTiles
        assertEquals(GameState.PLAYING, field.getState(), "chyba, po otvoreni CLUE sa zmenil GameState");


        //**** Marknut si akukolvek neotvorenu dlazdicu a skusit si na nej open, ci ostava marknuta.
        rRow = randomGenerator.nextInt(rowCount);
        rCol = randomGenerator.nextInt(columnCount);
        if (field.getTile(rRow, rCol).getState() == Tile.State.CLOSED) {
            field.markTile(rRow, rCol);
            field.openTile(rRow, rCol);
        }
        assertEquals(Tile.State.MARKED, field.getTile(rRow, rCol).getState(), "chyba: po marknuti sa tile otvorila");


    }




}