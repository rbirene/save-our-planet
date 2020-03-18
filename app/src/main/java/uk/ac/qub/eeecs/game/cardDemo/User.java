package uk.ac.qub.eeecs.game.cardDemo;

public class User {
    private String name;
    private int wins;
    private int losses;
    private int gamesPlayed;
    private double winRateRatio;

    public User(String name, int numOfwins, int numOfLosses){
        this.name = name;
        this.wins = numOfwins;
        this.losses = numOfLosses;
    }

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
        calculateGamesPlayed();
    }
    public void addWin(){
        wins++;
        calculateGamesPlayed();
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
        calculateGamesPlayed();
    }

    public void addLoss(){
        losses++;
        calculateGamesPlayed();
    }

    public double getWinRateRatio() {
        calculateWinRateRatio();
        return winRateRatio;
    }

    public int getGamesPlayed() {
        calculateGamesPlayed();
        return gamesPlayed;
    }


    public void calculateGamesPlayed(){
        gamesPlayed = wins + losses;
    }

    private void calculateWinRateRatio() {
        if(getGamesPlayed() != 0) {
            winRateRatio = (double)wins/getGamesPlayed();
        }else{winRateRatio = wins;}
    }
}
