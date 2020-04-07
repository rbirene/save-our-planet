package uk.ac.qub.eeecs.game;

import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.VillainCard;

/**
 * Define list of Card Types and
 * associated methods
 *
 * Created By Niamh McCartney
 */
public enum CardType {
    //CardTypes
    HERO_CARD(1),
    VILLAIN_CARD(2);

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Define the ID that represents a certain CardType
    private int cardTypeID;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    CardType(int cardTypeID){ this.cardTypeID = cardTypeID; }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Creates a Card object depending on the cardType
     * Enum that calls the method and returns the created
     * Card object
     *
     *  Created By Niamh McCartney
     */
    public Card getCardObjectType(Game aGame, String mName, Bitmap mCardPortrait, Vector2 scaleValue,
                                   int mAttack, int mHealth, float portraitYPos) {
        Card card;

        switch(cardTypeID)
        {
            //Case statements
            case 1 :  card = new HeroCard(aGame, mName, mCardPortrait, scaleValue, mAttack,
                    mHealth, portraitYPos);
                break;

            case 2 : card = new VillainCard(aGame, mName, mCardPortrait, scaleValue, mAttack,
                    mHealth, portraitYPos);
                break;

            //Default statement, used when none of the cases are true.
            default : throw new RuntimeException("CardType.getCardObjectType: " +
                    "CardTpe not included in switch case");
        }
        return card;
    }

}