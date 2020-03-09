package uk.ac.qub.eeecs.game.cardDemo;

public class Colour {

    private ColourEnum colour;

    public Colour(ColourEnum colourName){
        colour = colourName;
    }

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
                // below is default statement, used when none of the cases is true.
                default : colourCode = "#FFFFFF";

            }

            return colourCode;
    }
}
