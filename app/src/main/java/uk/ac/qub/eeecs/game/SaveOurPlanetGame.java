package uk.ac.qub.eeecs.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.qub.eeecs.game.cardDemo.Screens.MenuScreen;
import uk.ac.qub.eeecs.game.cardDemo.Screens.SplashScreen;
import uk.ac.qub.eeecs.gage.Game;

/**
 * Game that is create within the MainActivity class
 * This class is based off the 'DemoGame' class in
 * the original Gage code
 *
 */
public class SaveOurPlanetGame extends Game {

    /**
     * Create a new game
     */
    public SaveOurPlanetGame() {
        super();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Go with a default 20 UPS/FPS
        setTargetFramesPerSecond(20);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Call the Game's onCreateView to get the view to be returned.
        View view = super.onCreateView(inflater, container, savedInstanceState);

        SplashScreen SplashScreen = new SplashScreen(this);
        mScreenManager.addScreen(SplashScreen);
        return view;
    }

    @Override
    public boolean onBackPressed() {
        // If we are already at the menu screen then exit
        if (mScreenManager.getCurrentScreen().getName().equals("MenuScreen"))
            return false;

        // Stop any playing music
        if(mAudioManager.isMusicPlaying())
            mAudioManager.stopMusic();

        // Go back to the menu screen
        getScreenManager().removeAllScreens();
        MenuScreen menuScreen = new MenuScreen(this);
        getScreenManager().addScreen(menuScreen);
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}