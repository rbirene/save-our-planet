package uk.ac.qub.eeecs.game.cardDemo.Enums;

public enum DifficultyLevels {
    /** These are difficulty levels that will impact the AI of the villain, this is changeable in the settings screen
     *  Difficulty Values are Easy,Normal and Hard
     *  Keith Tennyson.
     */
    EASY(1),NORMAL(2), HARD(3);

    private int difficultyvalue;
    /** DifficultyLevels,getDifficultyvalue and setDifficultyvalue were from the idea of using an
     *  int for enumeration,this  was taken from http://dan.clarke.name/2011/07/enum-in-java-with-int-conversion/ but was ultimately not used
     *  The EASY, NORMAL and HARD values were instead used, so an int difficulty enum is not used
     *  and is left over code from another source.
     *  Keith Tennyson
      */




    private DifficultyLevels(int difficultyvalue) {
        this.difficultyvalue = difficultyvalue;
    }

    public int getDifficultyvalue(int difficultyvalue) {
        return difficultyvalue;
    }

    public void setDifficultyvalue(int difficultyvalue) {
        this.difficultyvalue = difficultyvalue;
    }
}