package uk.ac.qub.eeecs.game.cardDemo.Colour;

/**
 * Provides information on
 * a particular Colour
 *
 * Created By Niamh McCartney
 */
public class Colour {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Name of Colour
    private ColourEnum colour;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    public Colour(ColourEnum colourName){
        colour = colourName;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Returns Colour Code of Colour
     * used during design of Game objects
     *
     * Created By Niamh McCartney
     */
    public String getColourCode(){
            String colourCode;

            switch(colour)
            {
                // case statements
                case WHITE : colourCode = "#FFFFFF";
                    break;

                case BLACK : colourCode = "#000000";
                    break;

                case GREEN : colourCode = "#a4c639";
                    break;

                case BLUE : colourCode = "#0000FF";
                    break;

                case RED : colourCode = "#FF0000";
                    break;

                case YELLOW : colourCode = "#FFFF00";
                    break;

                case ORANGE : colourCode = "#FFA500";
                    break;
                // below is default statement, used when none of the cases are true.
                default : colourCode = "#FFFFFF";

            }

            return colourCode;
    }
}