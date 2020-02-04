package uk.ac.qub.eeecs.game.cardDemo;
import uk.ac.qub.eeecs.gage.Game;

import uk.ac.qub.eeecs.gage.world.GameScreen;

/**
 * Created by [Irene Bhuiyan]
 * This class represents a hero in the game (using traits and behaviours from Player).
 */

public class Hero extends Player {

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a new Hero.
     *
     * @param gameScreen Gamescreen to which this player belongs
     *
     */

    public Hero(GameScreen gameScreen){
        super("Freta Funberg", null, gameScreen);

    }

}
