package uk.ac.qub.eeecs.gage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.qub.eeecs.game.cardDemo.Enums.Colour;

import static org.junit.Assert.assertEquals;

/**
 * Tests for Colour Enum Class
 * Created by Niamh McCartney
 */
@RunWith(MockitoJUnitRunner.class)
public class ColourUnitTest {

    private Colour colour;

    @Test
    public void colour_getColourCode_white_correctCodeReturned(){
        colour = Colour.WHITE;
        String colourCode = colour.getColourCode();
        assertEquals("#FFFFFF", colourCode);
    }

    @Test
    public void colour_getColourCode_black_correctCodeReturned(){
        colour = Colour.BLACK;
        String colourCode = colour.getColourCode();
        assertEquals("#000000", colourCode);
    }

    @Test
    public void colour_getColourCode_green_correctCodeReturned(){
        colour = Colour.GREEN;
        String colourCode = colour.getColourCode();
        assertEquals("#a4c639", colourCode);
    }

    @Test
    public void colour_getColourCode_blue_correctCodeReturned(){
        colour = Colour.BLUE;
        String colourCode = colour.getColourCode();
        assertEquals("#0000FF", colourCode);
    }

    @Test
    public void colour_getColourCode_red_correctCodeReturned(){
        colour = Colour.RED;
        String colourCode = colour.getColourCode();
        assertEquals("#FF0000", colourCode);
    }

    @Test
    public void colour_getColourCode_yellow_correctCodeReturned(){
        colour = Colour.YELLOW;
        String colourCode = colour.getColourCode();
        assertEquals("#FFFF00", colourCode);
    }

    @Test
    public void colour_getColourCode_orange_correctCodeReturned(){
        colour = Colour.ORANGE;
        String colourCode = colour.getColourCode();
        assertEquals("#FFA500", colourCode);
    }
}