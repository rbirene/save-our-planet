package uk.ac.qub.eeecs.gage;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player.Hero;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player.Villain;

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
        aHero = new Hero(null);
        //aHero.setPlayerAvatar(aGame.getAssetManager().getBitmap("freta"));

        //set up comparison villain
        aVillain = new Villain(null);
        //aVillain.setPlayerAvatar(aGame.getAssetManager().getBitmap("ronald"));

    }

    // check player avatar is successfully set for hero
    @Test
    public void setUpHeroAvatar(){
        Hero tHero = new Hero(null);
        //tHero.setPlayerAvatar(aGame.getAssetManager().getBitmap("freta"));
        //System.out.println(tHero.getPlayerAvatar());
    }

    // check player avatar is the correct one for hero
    @Test
    public void setUpHeroCorrectAvatar(){
        Hero tHero = new Hero(null);
        //tHero.setPlayerAvatar(aGame.getAssetManager().getBitmap("freta"));
        //assertEquals(aHero.getPlayerAvatar(), tHero.getPlayerAvatar());
    }

    // check if player name has been added for hero
    @Test
    public void hasHeroPlayerName(){
        Hero tHero = new Hero(null);
        System.out.println(tHero.getPlayerName());
    }

    // check correct player name has been added for hero
    @Test
    public void correctHeroPlayerName(){
        Hero tHero = new Hero(null);
        assertEquals("Freta Funberg", tHero.getPlayerName());
    }

    // check player avatar is successfully set for villain
    @Test
    public void setUpVillainAvatar(){
        Villain tVillain = new Villain(null);
        //tVillain.setPlayerAvatar(aGame.getAssetManager().getBitmap("ronald"));
        //System.out.println(tVillain.getPlayerAvatar());
    }

    // check player avatar is the correct one for villain
    @Test
    public void setUpVillainCorrectAvatar(){
        Villain tVillain = new Villain(null);
        //tVillain.setPlayerAvatar(aGame.getAssetManager().getBitmap("ronald"));
        //assertEquals(tVillain.getPlayerAvatar(), tVillain.getPlayerAvatar());
    }

    // check if player name has been added for villain
    @Test
    public void hasVillainPlayerName(){
        Villain tVillain = new Villain(null);
        System.out.println(tVillain.getPlayerName());
    }

    //check correct player name has been added for villain
    @Test
    public void correctVillainPlayerName(){
        Villain tVillain = new Villain(null);
        assertEquals("Ronald Rump", tVillain.getPlayerName());
    }

}