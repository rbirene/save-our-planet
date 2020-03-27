package uk.ac.qub.eeecs.game.cardDemo.User;

public class User implements Comparable<User>{
    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Defines the User's name
    private String name;

    //Defines the number of games the User has won
    private int wins;
    //Defines the number of games the User has lost
    private int losses;
    //Defines the number of games the User has played
    private int gamesPlayed;

    //Defines the User's WinRateRatio (wins/gamePlayed)
    private double winRateRatio;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    public User(String name, int numOfWins, int numOfLosses){
        this.name = name;
        this.wins = numOfWins;
        this.losses = numOfLosses;

        winRateRatio = 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Overrides the compareTo method
     * from the Comparator interface
     * to enable the winRateRatio of
     * Users to be compared
     *
     * Created By Niamh McCartney
     */
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

    /**
     * Calculates the number of
     * games played by the User
     *
     * Created By Niamh McCartney
     */
    public void calculateGamesPlayed(){
        gamesPlayed = wins + losses;
    }

    private void calculateWinRateRatio() {
        if(getGamesPlayed() == 0 && wins == 0){
            winRateRatio = 0.0;
        }
        else if(getGamesPlayed() == 0) {
            winRateRatio = 0.0;
        }else{
            winRateRatio = (double)wins/getGamesPlayed();
        }
    }

    /**
     * Increases the number of games
     * the play has won by one
     *
     * Created By Niamh McCartney
     */
    public void addWin(){
        wins++;
        calculateGamesPlayed();
    }

    /**
     * Increases the number of games
     * the play has lost by one
     *
     * Created By Niamh McCartney
     */
    public void addLoss(){
        losses++;
        calculateGamesPlayed();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Returns the number of games
     * the User has played
     *
     * Created By Niamh McCartney
     */
    public int getGamesPlayed() {
        calculateGamesPlayed();
        return gamesPlayed;
    }

    /**
     * Returns the Users winRateRatio
     *
     * Created By Niamh McCartney
     */
    public double getWinRateRatio() {
        calculateWinRateRatio();
        return winRateRatio;
    }

    /**
     * Returns the number of games
     * the User has won
     *
     * Created By Niamh McCartney
     */
    public int getWins() {
        return wins;
    }


    /**
     * Returns the number of games
     * the User has lost
     *
     * Created By Niamh McCartney
     */
    public int getLosses() {
        return losses;
    }


    /**
     * Returns the name of the User
     *
     * Created By Niamh McCartney
     */
    public String getName() {
        return name;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Setters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Setter to set the Users name
     *
     * Created By Niamh McCartney
     */
    public void setUser(User user) {
        this.name = user.getName();
        this.wins = user.getWins();
        this.losses = user.getLosses();
    }

    /**
     * Setter to set the number of games
     * the User has won
     *
     * Created By Niamh McCartney
     */
    public void setWins(int wins) {
        this.wins = wins;
        calculateGamesPlayed();
    }

    /**
     * Setter to set the number of games
     * the User has lost
     *
     * Created By Niamh McCartney
     */
    public void setLosses(int losses) {
        this.losses = losses;
        calculateGamesPlayed();
    }

    /**
     * Setter to set the Users name
     *
     * Created By Niamh McCartney
     */
    public void setName(String name) {
        this.name = name;
    }
}