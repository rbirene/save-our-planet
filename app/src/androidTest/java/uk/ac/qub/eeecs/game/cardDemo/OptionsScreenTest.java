package uk.ac.qub.eeecs.game.cardDemo;


import android.graphics.Bitmap;
import android.graphics.Path;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.TestGame;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.game.cardDemo.Screens.OptionsScreen;
import uk.ac.qub.eeecs.gage.ui.PushButton;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class OptionsScreenTest {

    private TestGame Game;
    private OptionsScreen optionDemo;
    private ElapsedTime elapsedTime;

    @Before
    public void setUp() {

        Game = new TestGame(1280,720);
        Game.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
    }

    @Test
    public void DifficultyInteractionWithEnum() {
        optionDemo = new OptionsScreen(Game);
        Game.getScreenManager().addScreen(optionDemo);
        Game.mDifficultyLevel = DifficultyLevels.EASY;
        System.out.println("Difficulty is " + Game.mDifficultyLevel);

        assertTrue(Game.mDifficultyLevel == DifficultyLevels.EASY);
    }





    @Test
    public void DifficultyBetweenScreens() {
        optionDemo = new OptionsScreen(Game);
        Game.getScreenManager().addScreen(optionDemo);
        Game.mDifficultyLevel = DifficultyLevels.NORMAL;
        System.out.println("Screen is " + Game.getScreenManager().getCurrentScreen());
        System.out.println("Difficulty is" + Game.mDifficultyLevel);
        OptionsScreen optionDemo1;
        optionDemo1 = new OptionsScreen(Game);
        Game.getScreenManager().addScreen(optionDemo1);
        Game.getScreenManager().getScreen("optionDemo1");
        System.out.println("Screen is " + Game.getScreenManager().getCurrentScreen() + "Difficulty is" + Game.mDifficultyLevel);
        String test = "Difficulty is " + Game.mDifficultyLevel;
        Assert.assertEquals(test,test);
        assertTrue(Game.mDifficultyLevel == DifficultyLevels.NORMAL);





    }

    @Test
    public void DifficultyCheckerOptions() {
        optionDemo = new OptionsScreen(Game);
        PushButton difficulty;
        Game.mDifficultyLevel = DifficultyLevels.EASY;
        difficulty = new PushButton(420.0f, 50.0f, 75.0f, 60.0f,
                "diffEasy","diffNormal",optionDemo );
        DifficultyLevels diff = Game.mDifficultyLevel;
        Game.getScreenManager().addScreen(optionDemo);
        Game.getScreenManager().getScreen("optionDemo");
        optionDemo.diffChecker(diff,difficulty);
        Bitmap diffEasy = difficulty.getBitmap();
        assertTrue(Game.getAssetManager().getBitmap("diffEasy") == diffEasy);
    }




    @Test
    public void VolumeGetSetTest() {
        optionDemo = new OptionsScreen(Game);
        Game.getScreenManager().addScreen(optionDemo);
        optionDemo.setVolume(0.5f);
        float volume = optionDemo.getVolume();
        assertTrue(volume == 0.5f);
    }

    @Test
    public void correctVolumeBarBitmap() {
        optionDemo = new OptionsScreen(Game);
        Game.getScreenManager().addScreen(optionDemo);
        optionDemo.setVolume(0.5f);
        optionDemo.updateVolumeBar(elapsedTime);
        Bitmap volBar = optionDemo.getVolumeBar();
        assertTrue(Game.getAssetManager().getBitmap("soundBar50") == volBar);
    }

    @Test
    public void correctVolumeBarBitmap2() {
        optionDemo = new OptionsScreen(Game);
        Game.getScreenManager().addScreen(optionDemo);
        optionDemo.setVolume(0.95f);
        optionDemo.updateVolumeBar(elapsedTime);
        Bitmap volBar = optionDemo.getVolumeBar();
        assertTrue(Game.getAssetManager().getBitmap("soundBar100") == volBar);
    }

    @Test
    public void correctVolumeBarBitmap3() {
        optionDemo = new OptionsScreen(Game);
        Game.getScreenManager().addScreen(optionDemo);
        optionDemo.setVolume(0.3f);
        optionDemo.updateVolumeBar(elapsedTime);
        Bitmap volBar = optionDemo.getVolumeBar();
        assertTrue(Game.getAssetManager().getBitmap("soundBar25") == volBar);
    }
}
