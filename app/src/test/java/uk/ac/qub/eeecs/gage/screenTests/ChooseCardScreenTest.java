package uk.ac.qub.eeecs.gage.screenTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.game.cardDemo.Card;
import uk.ac.qub.eeecs.game.cardDemo.ChooseCardScreen;
import uk.ac.qub.eeecs.game.cardDemo.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Hero;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChooseCardScreenTest {

    //private Deck aDeck;

    @Mock
    Game aGame = Mockito.mock(Game.class);

    @Mock
    ChooseCardScreen chooseCardScreen = Mockito.mock(ChooseCardScreen.class);

    @Mock
    Hero aHero = Mockito.mock(Hero.class);

//    @Mock
//    Card card01 = Mockito.mock(Card.class);
//
//    @Mock
//    Card card02 = Mockito.mock(Card.class);
//
//    @Mock
//    Card card03 = Mockito.mock(Card.class);
      @Mock
      private AssetManager assetManager = Mockito.mock(AssetManager.class);;

    Card card01;
    Card card02;
    Card card03;

    @Mock
    Deck aDeck = Mockito.mock(Deck.class);

    @Before
    public void setup(){
        when(chooseCardScreen.getGame()).thenReturn(aGame);
        when(aGame.getHero()).thenReturn(aHero);
        when(aGame.getAssetManager()).thenReturn(assetManager);
        aGame.getAssetManager().loadAssets("txt/assets/CardAssets.JSON");
        card01 = new Card(5, 8, chooseCardScreen, "cardName", "cardType" , null, null, 5, 9, 0.3f);
        Card card02 = new Card(5, 8, chooseCardScreen, "cardName", "cardType" , null, null, 5, 9, 0.3f);
        Card card03 = new Card(5, 8, chooseCardScreen, "cardName", "cardType" , null, null, 5, 9, 0.3f);
        aDeck = new Deck(card01, card02, card03);
        chooseCardScreen.getGame().getHero().setPlayerDeck(aDeck);
    }

    @Test
    public void ChooseCardScreen_noCardSelected_ReturnFalse_Success(){
        aDeck.getCard01(chooseCardScreen).changeHeroCardBackground();
        assertFalse(chooseCardScreen.noCardsSelected());
    }
}
