package uk.ac.qub.eeecs.game.cardDemo.cardDemo;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.TestGame;
import uk.ac.qub.eeecs.game.MenuScreen;
import uk.ac.qub.eeecs.game.cardDemo.Screens.SplashScreen;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SplashScreenTest {

    private TestGame Game;
    private SplashScreen splashDemo;
    private MenuScreen menuScreen;
    @Before
    public void setUp() {

        Game = new TestGame(1280,720);
        Game.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
    }


    @Test
    public void timerTest() {
        splashDemo = new SplashScreen(Game);
        Game.getScreenManager().addScreen(splashDemo);
        splashDemo.setTimer(40);
        assertTrue(splashDemo.getTimer() == 40);
    }

    @Test
    public void timerTest2() {
        splashDemo = new SplashScreen(Game);
        Game.getScreenManager().addScreen(splashDemo);
        splashDemo.setTimer(40);
        assertTrue(Game.getScreenManager().getCurrentScreen().getName() == "Splash");
    }

    @Test
    public void timerTest3() {
        splashDemo = new SplashScreen(Game);
        menuScreen = new MenuScreen(Game);

        Game.getScreenManager().addScreen(splashDemo);
        splashDemo.setTimer(100);
        assertTrue(Game.getScreenManager().getCurrentScreen().getName() != "MenuScreen");
    }
}
