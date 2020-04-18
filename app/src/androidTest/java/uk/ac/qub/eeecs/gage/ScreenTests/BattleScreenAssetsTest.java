package uk.ac.qub.eeecs.gage.ScreenTests;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;

import uk.ac.qub.eeecs.gage.TestGame;
import uk.ac.qub.eeecs.gage.TestGameScreen;
import uk.ac.qub.eeecs.gage.world.GameObject;

/**
 * Tests for players in Battle Screen.
 *
 * Created by [Irene Bhuiyan]
 *
 */

public class BattleScreenAssetsTest {

    private GameObject heroAvatarImg, villainAvatarImg;
    private TestGameScreen aScreen;
    private TestGame aGame;

    @Before
    public void BattleScreenAssetsTestSetup(){

        aGame = new TestGame(420, 360);
        aGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        aScreen = new TestGameScreen(aGame){};

        //set up hero
        Bitmap heroAvatar = aGame.getAssetManager().getBitmap("freta");
        heroAvatarImg = new GameObject(100.0f, 50.0f, 100.0f, 100.0f, heroAvatar, aScreen);

        //set up villain
        Bitmap villainAvatar = aGame.getAssetManager().getBitmap("ronald");
        villainAvatarImg = new GameObject(50.0f, 270.0f, 100.0f, 100.0f, villainAvatar, aScreen);

    }

    // check avatar for hero has been successfully set
    @Test
    public void getHeroAvatar(){
        System.out.println("Hero avatar: "+heroAvatarImg.getBitmap());
    }

    // check dimensions for hero has been successfully defined
    @Test
    public void getHeroDimensions(){
        System.out.println("Hero height: "+heroAvatarImg.getHeight());
        System.out.println("Hero width: "+heroAvatarImg.getWidth());
    }

    // check game screen for hero has been successfully set
    @Test
    public void getHeroGameScreen(){
        System.out.println("Hero game screen: "+heroAvatarImg.getGameScreen());
    }

    // check avatar for villain has been successfully set
    @Test
    public void getVillainAvatar(){
        System.out.println("Villain avatar: "+villainAvatarImg.getBitmap());
    }

    // check dimensions for villain has been successfully defined
    @Test
    public void getVillainDimensions(){
        System.out.println("Villain height: "+villainAvatarImg.getHeight());
        System.out.println("Villain width: "+villainAvatarImg.getWidth());
    }

    // check game screen for villain has been successfully set
    @Test
    public void getVillainGameScreen(){
        System.out.println("Villain game screen: "+villainAvatarImg.getGameScreen());
    }

}
