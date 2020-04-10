package uk.ac.qub.eeecs.gage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.qub.eeecs.game.cardDemo.User.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for User Class
 * Created by Niamh McCartney
 */
@RunWith(MockitoJUnitRunner.class)
public class UserUnitTest {

    private User aUser;
    private int numOfWins = 4;
    private int numOfLosses = 6;

    @Before
    public void SetUp() {
        aUser = new User("David", numOfWins, numOfLosses);
    }

    @Test
    public void getName_CorrectName_Success(){
        assertEquals("David", aUser.getName());
    }

    @Test
    public void getWins_CorrectNumber_Success(){
        assertEquals(4, aUser.getWins());
    }

    @Test
    public void getLosses_CorrectNumber_Success(){
        assertEquals(6, aUser.getLosses());
    }

    @Test
    public void calculateGamesPlayed_ReturnsWinsPlusLosses_Success(){
        assertEquals(10, aUser.getGamesPlayed());
    }

    @Test
    public void setName_CorrectName_Success(){
        String newName = "Sean";
        aUser.setName(newName);
        assertEquals(newName, aUser.getName());
    }

    @Test
    public void setWins_CorrectNumber_Success(){
        int wins = 8;
        aUser.setWins(wins);
        assertEquals(wins, aUser.getWins());
    }

    @Test
    public void setLosses_CorrectNumber_Success(){
        int losses = 7;
        aUser.setLosses(losses);
        assertEquals(losses, aUser.getLosses());
    }

    @Test
    public void setUser_CorrectProperties_Success(){
        String name = "Claire";
        int wins = 2;
        int losses = 3;
        User newUser = new User(name, wins , losses);
        aUser.setUser(newUser);
        Boolean correctProperties = aUser.getName() == name &&
                aUser.getWins() == 2 && aUser.getLosses() == 3;
        assertTrue(correctProperties);
    }

    @Test
    public void getWinRateRatio_CorrectNumberCalculated_Success(){
        assertEquals(0.4, aUser.getWinRateRatio(), 0.05);
    }

    @Test
    public void getWinRateRatio_zeroGamesPlayed_Success(){
        aUser.setWins(0);
        aUser.setLosses(0);
        assertEquals(0.0, aUser.getWinRateRatio(), 0.05);
    }

    @Test
    public void getWinRateRatio_zeroWins_moreThanZeroLosses_Success(){
        int losses = 4;
        aUser.setWins(0);
        aUser.setLosses(losses);
        assertEquals(losses, aUser.getWinRateRatio(), 0.05);
    }

    @Test
    public void addWin_WinsIncrease_Success(){
        aUser.addWin();
        int newNumOfWins = numOfWins + 1;
        assertEquals(newNumOfWins, aUser.getWins());
    }

    @Test
    public void addLosses_LossesIncrease_Success(){
        aUser.addLoss();
        int newNumOfLosses = numOfLosses + 1;
        assertEquals(newNumOfLosses, aUser.getLosses());
    }

    @Test
    public void compareTo_UserGreaterThanComparison(){
        User comparisonUser = new User("Kate", 3, 6);
        int result = aUser.compareTo(comparisonUser);
        assertEquals(1, result);
    }

    @Test
    public void compareTo_UserLessThanComparison(){
        User comparisonUser = new User("Kate", 6, 6);
        int result = aUser.compareTo(comparisonUser);
        assertEquals(-1, result);
    }

    @Test
    public void compareTo_UserEqualToComparison(){
        User comparisonUser = new User("Kate", 4, 6);
        int result = aUser.compareTo(comparisonUser);
        assertEquals(0, result);
    }

}