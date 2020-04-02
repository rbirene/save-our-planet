package uk.ac.qub.eeecs.game.cardDemo.User;

/**
 * Defines User object to contain information
 * on Game's Users and provide methods to
 * access and modify this information. Implements
 * Comparable interface to provide ability to
 * compare and rank Users
 *
 * Created By Niamh McCartney
 */
public class User implements Comparable<User>{

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the User's name
    private String name;

    //Define the number of games the User has won
    private int wins;
    //Define the number of games the User has lost
    private int losses;
    //Define the number of games the User has played
    private int gamesPlayed;

    //Define the User's WinRateRatio (wins/gamePlayed)
    private double winRateRatio;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the User object
     *
     * @param name User's Name
     * @param numOfWins Number of games User has won
     * @param numOfLosses Number of games User has lost
     *
     * Created by Niamh McCartney
     */
    public User(String name, int numOfWins, int numOfLosses){
        //Define the parameters
        this.name = name;
        this.wins = numOfWins;
        this.losses = numOfLosses;

        //Initialise the LeaderBoard properties
        this.winRateRatio = 0;
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
    private void calculateGamesPlayed(){
        gamesPlayed = wins + losses;
    }

    /**
     * Calculates the User's
     * WinRateRatio
     *
     * Created By Niamh McCartney
     */
    private void calculateWinRateRatio() {
        if(getGamesPlayed() == 0){
            winRateRatio = 0.0;
        }
        else if(getWins() == 0) {
            winRateRatio = getLosses();
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