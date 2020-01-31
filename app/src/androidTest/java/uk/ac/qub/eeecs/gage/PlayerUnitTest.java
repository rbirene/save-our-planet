package uk.ac.qub.eeecs.gage;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import uk.ac.qub.eeecs.game.cardDemo.Hero;
import uk.ac.qub.eeecs.game.cardDemo.Villain;

public class PlayerUnitTest {

    private TestGameScreen aScreen;
    private TestGame aGame;
    private Hero aHero;
    private Villain aVillain;

    @Before
    public void playerTestSetup(){
        aGame = new TestGame(420, 360);

        aGame.mAssetManager.loadAssets("txt/assets/CardDemoScreenAssets.JSON");

        aScreen = new TestGameScreen(aGame){};

        //set up comparison hero
        aHero = new Hero(aScreen);
        aHero.setPlayerAvatar(aGame.getAssetManager().getBitmap("freta"));

        //set up comparison villain
        aVillain = new Villain("Ronald Rump", aScreen);
        aVillain.setPlayerAvatar(aGame.getAssetManager().getBitmap("ronald"));

    }

    @Test
    public void setUpHeroAvatarSuccess(){
        Hero tHero = new Hero(aScreen);
        tHero.setPlayerAvatar(aGame.getAssetManager().getBitmap("freta"));
        // check correct avatar has been set for hero
        assertEquals(aHero.getPlayerAvatar(), tHero.getPlayerAvatar());
    }

    @Test
    public void setUpHeroAvatarFailure(){
        Hero tHero = new Hero(aScreen);
        tHero.setPlayerAvatar(aGame.getAssetManager().getBitmap("ronald"));
        // check incorrect avatar has been set for hero
        assertEquals(aHero.getPlayerAvatar(), tHero.getPlayerAvatar());
    }

    @Test
    public void displayHeroNameSuccess(){
        Hero tHero = new Hero(aScreen);
        // check correct name is displayed for hero
        assertEquals("Freta Funberg", tHero.getPlayerName());
    }

    @Test
    public void displayHeroNameFailure(){
        Hero tHero = new Hero(aScreen);
        // check correct name is displayed for hero
        assertEquals("Something Something", tHero.getPlayerName());
    }

    @Test
    public void setUpVillainAvatarSuccess(){
        Villain tVillain = new Villain("Ronald Rump", aScreen);
        tVillain.setPlayerAvatar(aGame.getAssetManager().getBitmap("ronald"));
        // check correct avatar has been set for villain
        assertEquals(aVillain.getPlayerAvatar(), tVillain.getPlayerAvatar());
    }

    @Test
    public void setUpVillainAvatarFailure(){
        Villain tVillain = new Villain("Ronald Rump", aScreen);
        tVillain.setPlayerAvatar(aGame.getAssetManager().getBitmap("freta"));
        // check incorrect avatar has been set for villain
        assertEquals(aVillain.getPlayerAvatar(), tVillain.getPlayerAvatar());
    }

    @Test
    public void displayVillainNameSuccess(){
        Villain tVillain = new Villain("Ronald Rump", aScreen);
        // check correct name is displayed for villain
        assertEquals("Ronald Rump", tVillain.getPlayerName());
    }

    @Test
    public void displayVillainNameFailure(){
        Villain tVillain = new Villain("Ronald Rump", aScreen);
        // check correct name is displayed for villain
        assertEquals("Something Something", tVillain.getPlayerName());
    }

}
