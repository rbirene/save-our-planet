package uk.ac.qub.eeecs.game.cardDemo.Enums;

/**
 * Define list of Colours
 *
 * Created By Niamh McCartney
 */
public enum Colour {
    WHITE(1),
    BLACK(2),
    GREEN(3),
    BLUE(4),
    RED(5),
    YELLOW(6),
    ORANGE(7);

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the ID that represents a certain Colour
    private int colourID;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    Colour(int colourID){
        this.colourID = colourID;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Returns Colour Code of Colour Enum that calls the method.
     * Used during design of Game objects
     *
     * Created By Niamh McCartney
     */
    public String getColourCode(){
        String colourCode;

        switch(colourID)
        {
            // case statements
            case 1 : colourCode = "#FFFFFF";
                break;

            case 2 : colourCode = "#000000";
                break;

            case 3 : colourCode = "#a4c639";
                break;

            case 4 : colourCode = "#0000FF";
                break;

            case 5 : colourCode = "#FF0000";
                break;

            case 6 : colourCode = "#FFFF00";
                break;

            case 7 : colourCode = "#FFA500";
                break;
            // below is default statement, used when none of the cases are true.
            default : colourCode = "#FFFFFF";

        }

        return colourCode;
    }
}