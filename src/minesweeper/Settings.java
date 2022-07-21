package minesweeper;

import java.io.Serializable;

public class Settings implements Serializable {
    private final int rowCount, columnCount, mineCount;



//    Hracie pole pre začiatočníkov - veľkosť 9x9, počet mín 10 -
    public static final Settings BEGINNER = new Settings(9, 9, 10);

//    Hracie pole pre mierne pokročilých - veľkosť 16x16, počet mín 40 -
    public static final Settings INTERMEDIATE = new Settings(16, 16, 40);

//    Hracie pole pre pokročilých - veľkosť 16x30, počet mín 99 -
    public static final Settings EXPERT = new Settings(16, 30, 99);

// informáciu o súbore, kde bude nastavenie hry uložené, resp. z ktorého bude nastavenie hry získané pri jej spustení.
    private static final String SETTING_FILE = System.getProperty("user.home") + System.getProperty("file.separator") + "minesweeper.settings";



    public Settings(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void save(){}
    public static Settings load(){
        // default
         return BEGINNER;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Settings)) {
            return false;
        }

        Settings c = (Settings) obj;

        return (this.rowCount==c.getRowCount()&& this.columnCount==c.getColumnCount() && this.mineCount==c.getMineCount());
    }

    @Override
    public int hashCode() {
        return this.rowCount*this.columnCount*this.mineCount;
    }
}
