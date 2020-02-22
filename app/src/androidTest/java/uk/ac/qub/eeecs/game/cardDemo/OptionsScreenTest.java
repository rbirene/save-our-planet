package uk.ac.qub.eeecs.game.cardDemo;


import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.TestGame;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.game.cardDemo.Screens.OptionsScreen;

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
