package uk.ac.qub.eeecs.gage.GameBoardObjectTests;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.TestGame;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.game.cardDemo.Screens.MenuScreen;
import uk.ac.qub.eeecs.game.cardDemo.Boards.GameBoardObjects.CardHolder;
import uk.ac.qub.eeecs.game.cardDemo.Screens.BattleScreen;
import uk.ac.qub.eeecs.game.cardDemo.Screens.ChooseCardScreen;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.HeroCard;

@RunWith(AndroidJUnit4.class)
public class CardHolderTests {

    private TestGame Game;
    private BattleScreen battleDemo;
    private ElapsedTime elapsedTime;
    private ChooseCardScreen chooseCardScreenDemo;
    private MenuScreen menuScreenDemo;



    @Before
    public void setUp() {

        Game = new TestGame(1280,720);
        Game.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
         menuScreenDemo = new MenuScreen(Game);
        chooseCardScreenDemo = new ChooseCardScreen(Game);
        battleDemo = new BattleScreen(Game);

    }



    @Test
    public void testHolderCreation(){
        CardHolder testHolder = new CardHolder(140.0f, 130.0f,battleDemo);
        Assert.assertTrue(testHolder.isEmpty());
    }

    @Test
    public void addCardToHolder(){
        CardHolder holder = new CardHolder(140.0f, 130.0f,battleDemo);
        HeroCard testCard = new HeroCard(Game, "test", null, null ,
                2, 2, 2.0f);
        holder.AddCardToHolder(testCard);
        Assert.assertFalse(holder.isEmpty());
    }

    @Test
    public void removeCardFromHolder(){
        CardHolder holder = new CardHolder(140.0f, 130.0f,battleDemo);
        HeroCard testCard = new HeroCard(Game, "test", null, null ,
                2, 2, 2.0f);
        holder.AddCardToHolder(testCard);
        holder.removeCard();
        Assert.assertTrue(holder.isEmpty());
    }

    @Test
    public void removeCardFromHolderThenReadd(){
        CardHolder holder = new CardHolder(140.0f, 130.0f,battleDemo);
        HeroCard testCard = new HeroCard(Game, "test", null, null ,
                2, 2, 2.0f);
        holder.AddCardToHolder(testCard);
        holder.removeCard();
        holder.AddCardToHolder(testCard);
        Assert.assertFalse(holder.isEmpty());
    }
}