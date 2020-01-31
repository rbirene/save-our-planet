package uk.ac.qub.eeecs.game.cardDemo;

import uk.ac.qub.eeecs.gage.world.GameScreen;

/**
 * Created by [Irene Bhuiyan]
 * This class represents a villain in the game (using traits and behaviours from Player).
 */

public class Villain extends Player {

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a new villain.
     *
     * @param playerName Name of the player
     * @param gameScreen Gamescreen to which this player belongs
     *
     */

    public Villain(GameScreen gameScreen){
        super("Ronald Rump", null, gameScreen);
    }

}
