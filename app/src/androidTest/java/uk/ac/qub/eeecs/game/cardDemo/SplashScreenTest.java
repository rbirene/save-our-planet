package uk.ac.qub.eeecs.game.cardDemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.TestGame;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.MenuScreen;
import uk.ac.qub.eeecs.game.cardDemo.SplashScreen;


import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SplashScreenTest {

    private TestGame Game;
    private SplashScreen splashDemo;

    @Before
    public void setUp() {

        Game = new TestGame(1280,720);
        Game.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
    }

    @Test
    public void timerTest1() {
        splashDemo = new SplashScreen(Game);
        Game.getScreenManager().addScreen(splashDemo);
        splashDemo.setTimer(40);
        assertTrue(Game.getScreenManager().getCurrentScreen().getName() == "Splash");
    }

    @Test
    public void timerTest2() {
        splashDemo = new SplashScreen(Game);
        Game.getScreenManager().addScreen(splashDemo);
        splashDemo.setTimer(100);
        assertTrue(Game.getScreenManager().getCurrentScreen().getName() != "Splash");
    }
}

