package uk.ac.qub.eeecs.gage;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.game.cardDemo.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player.Hero;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the Player Class written by Niamh McC
 * Created by Niamh McCartney
 */
@RunWith(AndroidJUnit4.class)
public class PlayerTest {

    private TestGame aGame;
    private TestGameScreen aScreen;

    private Hero hero;

    private Card Card01;
    private Card Card02;
    private Card Card03;

    private Deck aDeck;

    @Before
    public void playerTestSetUp(){
        aGame = new TestGame(420, 360);
        aScreen = new TestGameScreen(aGame);

        //Health value to be assigned to each Card
        int healthValue = 100;
        Card01 = new HeroCard(aGame,"card01", null, null, 100, healthValue, 0.3f);
        Card02 = new HeroCard(aGame,"card02", null, null, 100, healthValue, 0.3f);
        Card03 = new HeroCard(aGame,"card03", null, null, 40, healthValue, 0.3f);

        aDeck = new Deck(Card01, Card02, Card03);

        hero = new Hero(null);
    }

    @Test
    public void player_setPlayerDeck_getPlayerDeck_correctDeckReturned(){
        Boolean deckSetCorrectly = false;
        hero.setPlayerDeck(aDeck);

        if(hero.getPlayerDeck() == aDeck){
            deckSetCorrectly = true;
        }
        assertTrue(deckSetCorrectly);
    }

    @Test
    public void player_calculatePlayerHealth_getPlayerHeath_healthEqualToHeathOfAllCardsInDeck(){
        hero.setPlayerDeck(aDeck);
        int health = hero.getPlayerHealth(aScreen);
        int expectedHealth = 100*3;
        assertEquals(expectedHealth, health);
    }
}
