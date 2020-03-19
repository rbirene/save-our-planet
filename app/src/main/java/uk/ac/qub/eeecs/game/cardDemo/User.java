package uk.ac.qub.eeecs.game.cardDemo;

public class User implements Comparable<User>{
    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////
    private String name;
    private int wins;
    private int losses;
    private int gamesPlayed;
    private double winRateRatio;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    public User(String name, int numOfwins, int numOfLosses){
        this.name = name;
        this.wins = numOfwins;
        this.losses = numOfLosses;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @Override
    public int compareTo(User user) {
        if(this.getWinRateRatio() < user.getWinRateRatio()){
            return -1;
        }else if(this.getWinRateRatio() > user.getWinRateRatio()){
            return 1;
        }else {
            return 0;
        }
    }

    public void calculateGamesPlayed(){
        gamesPlayed = wins + losses;
    }

    private void calculateWinRateRatio() {
        if(getGamesPlayed() != 0) {
            winRateRatio = (double)wins/getGamesPlayed();
        }else{winRateRatio = wins;}
    }

    public void addWin(){
        wins++;
        calculateGamesPlayed();
    }

    public void addLoss(){
        losses++;
        calculateGamesPlayed();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    public int getGamesPlayed() {
        calculateGamesPlayed();
        return gamesPlayed;
    }

    public double getWinRateRatio() {
        calculateWinRateRatio();
        return winRateRatio;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }


    public String getName() {
        return name;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////

    public void setWins(int wins) {
        this.wins = wins;
        calculateGamesPlayed();
    }

    public void setLosses(int losses) {
        this.losses = losses;
        calculateGamesPlayed();
    }

    public void setName(String name) {
        this.name = name;
    }
}
