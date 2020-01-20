package uk.ac.qub.eeecs.game.cardDemo;

import uk.ac.qub.eeecs.gage.Game;

public class Hero {

    private Deck heroDeck;

    public Hero(){
    }

    public void setHeroDeck(Deck aDeck){heroDeck = aDeck;}

    public Deck getHeroDeck(){return heroDeck;}
}
