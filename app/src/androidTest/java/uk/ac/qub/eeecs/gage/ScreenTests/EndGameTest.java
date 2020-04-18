package uk.ac.qub.eeecs.gage.ScreenTests;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.TestGame;
import uk.ac.qub.eeecs.game.cardDemo.Screens.EndGame;

//EndGameTest by Gareth Scott (40152832)
@RunWith(AndroidJUnit4.class)
public class EndGameTest {
    private TestGame Game;
    private EndGame endGameDemo;


    @Before
    public void setUp() {
        Game = new TestGame(1280, 720);
        Game.getAssetManager().loadAssets("EndGameScreenAssets.JSON");
    }

    @Test
    public void testWinScreen(){
        endGameDemo = new EndGame(Game, true);
        Game.getScreenManager().addScreen(endGameDemo);
        endGameDemo.getGame().getAssetManager().getBitmap("win");
    }

    @Test
    public void testLoseScreen(){
        endGameDemo = new EndGame(Game, false);
        Game.getScreenManager().addScreen(endGameDemo);
        endGameDemo.getGame().getAssetManager().getBitmap("lose");
    }

}