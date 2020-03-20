package uk.ac.qub.eeecs.gage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player.Hero;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PlayerUnitTest {

    private Hero hero;

    private Card Card01;
    private Card Card02;
    private Card Card03;

    private Deck aDeck;
    private ArrayList<Card> newDeck;

    @Mock
    GameScreen aScreen = Mockito.mock(GameScreen.class);

    @Before
    public void SetUp() {
        Card01 = new Card(5, 8, aScreen, "card01", "hero" , null, null, 100, 100, 0.3f);
        Card02 = new Card(5, 8, aScreen, "card02", "hero" , null, null, 100, 150, 0.3f);
        Card03 = new Card(5, 8, aScreen, "card03", "hero" , null, null, 40, 40, 0.3f);

        aDeck = new Deck(Card01, Card02, Card03);

        hero = new Hero(null);
        hero.setPlayerDeck(aDeck);
    }

    @Test
    public void setPlayerHealth_CorrectValueReturned_Success(){
        int health = hero.getPlayerHealth(aScreen);
        assertEquals(290, health);
    }
}
