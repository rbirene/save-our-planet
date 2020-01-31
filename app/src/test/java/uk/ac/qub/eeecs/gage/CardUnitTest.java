package uk.ac.qub.eeecs.gage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Card;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CardUnitTest {

    private  String cardType;
    private String cardName;

    @Mock
    GameScreen aScreen = Mockito.mock(GameScreen.class);

    @Mock
    Card aCard = Mockito.mock(Card.class);

    @Before
    public void setUp() {
        // card instance
        cardType =  "cardType";
        cardName = "cardName";
        aCard = new Card(5, 8, aScreen, cardName, cardType , null, null, 5, 9);
    }

    @Test
    public void getCardType_Success(){ assertEquals(cardType, aCard.getCardType());}

    @Test
    public void getCardType_Failure(){ assertNotEquals("Type", aCard.getCardType());}

    @Test
    public void getCardName_Success(){ assertEquals(cardName, aCard.getCardName());}

    @Test
    public void getCardName_Failure(){ assertNotEquals("name", aCard.getCardName());}


}
