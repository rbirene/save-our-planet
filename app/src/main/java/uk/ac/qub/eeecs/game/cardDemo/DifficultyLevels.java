package uk.ac.qub.eeecs.game.cardDemo;

public enum DifficultyLevels {
    // These are difficulty levels that will impact the AI of the villain, this is changeable in the settings screen
    EASY(1),NORMAL(2), HARD(3);

    private int difficultyvalue;


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