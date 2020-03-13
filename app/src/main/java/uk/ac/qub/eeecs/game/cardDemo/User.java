package uk.ac.qub.eeecs.game.cardDemo;

public class User {
    private String name;
    private int wins;
    private int losses;
    private int gamesPlayed;
    private double winLossRatio;

    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public double getWinLossRatio() {
        return winLossRatio;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void calculateWinLossRatio() {
        winLossRatio = wins/gamesPlayed;
    }
}
