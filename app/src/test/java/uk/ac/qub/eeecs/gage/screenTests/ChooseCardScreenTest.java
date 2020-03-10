//
///**
// * Created by Niamh McCartney
// */
//
//package uk.ac.qub.eeecs.gage.screenTests;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import uk.ac.qub.eeecs.gage.Game;
//import uk.ac.qub.eeecs.gage.engine.AssetManager;
//import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card;
//import uk.ac.qub.eeecs.game.cardDemo.Screens.ChooseCardScreen;
//import uk.ac.qub.eeecs.game.cardDemo.Sprites.Deck;
//import uk.ac.qub.eeecs.game.cardDemo.Sprites.Hero;
//
//import static junit.framework.Assert.assertFalse;
//import static junit.framework.TestCase.assertTrue;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ChooseCardScreenTest {
//
//    @Mock
//    Game aGame = Mockito.mock(Game.class);
//
//    @Mock
//    ChooseCardScreen chooseCardScreen = Mockito.mock(ChooseCardScreen.class);
//
//    @Mock
//    Hero aHero = Mockito.mock(Hero.class);
//
//    @Mock
//    private AssetManager assetManager = Mockito.mock(AssetManager.class);;
//
//    private Card card01;
//    private Card card02;
//    private Card card03;
//
//    @Mock
//    Deck aDeck = Mockito.mock(Deck.class);
//
//    @Before
//    public void setup(){
//        when(chooseCardScreen.getGame()).thenReturn(aGame);
//        when(aGame.getHero()).thenReturn(aHero);
//        when(aGame.getAssetManager()).thenReturn(assetManager);
//
//        //Load assets to be used
//        aGame.getAssetManager().loadAssets("txt/assets/CardAssets.JSON");
//
//        //set up Cards
//        card01 = new Card(5, 8, chooseCardScreen, "cardName", "cardType" , null, null, 5, 9, 0.3f);
//        card02 = new Card(5, 8, chooseCardScreen, "cardName", "cardType" , null, null, 5, 9, 0.3f);
//        card03 = new Card(5, 8, chooseCardScreen, "cardName", "cardType" , null, null, 5, 9, 0.3f);
//
//        //add cards to Deck
//        aDeck = new Deck(card01, card02, card03);
//        //assign to deck to Hero
//        chooseCardScreen.getGame().getHero().setPlayerDeck(aDeck);
//    }
//
//    @Test
//    public void ChooseCardScreen_noCardSelected_ReturnTrue_Success(){
//        assertTrue(chooseCardScreen.noCardsSelected());
//    }
//
//
//    @Test
//    public void ChooseCardScreen_CardSelected_ReturnFalse_Success(){
//        aDeck.getCard01(chooseCardScreen).changeHeroCardBackground();
//        assertFalse(chooseCardScreen.noCardsSelected());
//    }
//}
