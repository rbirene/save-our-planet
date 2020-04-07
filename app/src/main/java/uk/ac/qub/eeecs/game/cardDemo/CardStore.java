package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.game.CardType;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.VillainCard;

/**
 * Loads and Creates the Game Cards and
 * provides way for user to access them
 *
 * Created By Niamh McCartney
 */

public class CardStore {

    // /////////////////////////////////////////////////////////////////////////
    // Properties:
    // /////////////////////////////////////////////////////////////////////////

    //Define the FileIO used by the Game the CardStore is in
    private FileIO mFileIO;

    //Define the AssetManager used by the Game the CardStore is in
    private AssetManager assetManager;

    //Define the Game the CardStore is in
    private Game mGame;

    //Define the Card Name
    private String name;

    //Define the Card Health and Attack values
    private int attackValue;
    private int healthValue;

    //Define the Card Portrait scale
    private String portraitScaleValueX;
    private String portraitScaleValueY;
    private Vector2 portraitScaleValue;

    //Define the card portrait Y co-ordinate
    private float portraitYPos;

    //Define the Card Type
    private CardType cardType;

    //Define the Card portrait image
    private Bitmap cardPortrait;

    //Define the HashMap to contain all the Cards
    private HashMap<String, Card> cardPool = new HashMap<>();

    //Define the HashMap to contain all the collections
    //of Cards divided by CardType
    private HashMap<CardType, HashMap<String, Card>> cardPoolCollection;

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create the CardStore object
     * @param game Game the CardStore object belongs to
     *
     * Created by Niamh McCartney
     */
    public CardStore(Game game) {
        //Define the parameters
        this.mGame = game;

        //Initialise the UserStore properties
        this.mFileIO = mGame.getFileIO();
        this.assetManager = mGame.getAssetManager();
        this.cardPoolCollection = new HashMap<>();

        //Load the assets used by the Cards
        loadCardAssets();

        //Load and create the Card objects to be used in the game
        loadCardObjects("txt/assets/Card.JSON");

        //Separate the Cards by CardType and add them to the relevant HashMaps
        separateByCardType();
    }
   /**
     * The JSON file is in the following format:
     *
     {
     "assets": [
     {
     "name": string,
     "type" string
     "scaleValueX": float,
     "scaleValueY": float,
     "portraitYPos": float
     },
     ]
     }
     *
     * @param assetsToLoadJSONFile JSON file to load and process
     *
     * code refactored from 'loadAssets(String assetsToLoadJSONFile)'
     * method from 'AssetManager' class - MODIFIED TO SUIT OBJECTS NEEDS
     *
     * Created By Niamh McCartney
     */

    public void loadCardObjects(String assetsToLoadJSONFile) {
        // Attempt to load in the JSON asset details
        String loadedJSON;
        try {
            loadedJSON = mFileIO.loadJSON(assetsToLoadJSONFile);
        } catch (IOException e) {
            throw new RuntimeException(
                    "CardStore.loadCardObjects: Cannot load JSON [" +
                            assetsToLoadJSONFile + "]");
        }

        // Attempt to extract the JSON information
        try {
            JSONObject settings = new JSONObject(loadedJSON);
            JSONArray assets = settings.getJSONArray("assets");

            // Load in each asset
            for (int idx = 0; idx < assets.length(); idx++){

                //get values from JSON file
                name = assets.getJSONObject(idx).getString("name");
                cardType = CardType.valueOf(assets.getJSONObject(idx).getString("type"));
                portraitScaleValueX = assets.getJSONObject(idx)
                        .getString("scaleValuex");
                portraitScaleValueY = assets.getJSONObject(idx)
                        .getString("scaleValuey");
                portraitScaleValue = new Vector2(Float.parseFloat(portraitScaleValueX),
                        Float.parseFloat(portraitScaleValueY));
                portraitYPos = Float.parseFloat(assets.getJSONObject(idx)
                        .getString("portraitYPos"));

                //randomise health and attack values
                attackValue = getRandNum(5, 30);
                healthValue = getRandNum(40, 99);

                //Get the Card's portrait
                cardPortrait = assetManager.getBitmap(name);

                //create card object
                if(!cardPool.containsKey(name)) {
                    createCard();
                }else{ throw new RuntimeException(
                        "CardStore.constructor: Card appears multiple times in store " +
                                "["+"Multiple Cards in text file with same name"+"]");}
            }

        } catch (JSONException | IllegalArgumentException e) {
            throw new RuntimeException(
                    "CardStore.loadCardObjects: JSON parsing error [" + e.getMessage() + "]");
        }
    }

    /**
     * Creates a Card object depending on the cardType
     *  Created By Niamh McCartney
     */
    private void createCard(){
        //Create the new Card object
        Card card = cardType.getCardObjectType(mGame, name, cardPortrait, portraitScaleValue,
                attackValue, healthValue, portraitYPos);

        //Add the Card to the cardPool
        cardPool.put(name, card);
    }

    /**
     * Separate all Cards in the store into HashMaps
     * depending on their type. Then add all the HashMaps
     * to another HashMap where they can be stored to be
     * accessed when needed
     *
     *  Created By Niamh McCartney
     */
    private void separateByCardType(){
       for(CardType type : CardType.values()) {
           HashMap<String, Card> cardTypePool = new HashMap<>();

           for (Map.Entry<String, Card> entry : this.cardPool.entrySet()) {
               //Define the Card and its key in the cardPool
               String key = entry.getKey();
               Card card = entry.getValue();

               if (card.getCardType() == type) {
                   cardTypePool.put(key, card);
               }
           }
           cardPoolCollection.put(type, cardTypePool);
       }
    }

    /**
     * Load Assets used by screen
     * Created By Niamh McCartney
     */
    private void loadCardAssets() {
        assetManager.loadAssets("txt/assets/CardAssets.JSON");
    }

    /**
     * Renew the health of all cards of a specific type
     *  Created By Niamh McCartney
     */
    public void renewHealthOfCards(CardType cardTypeEnum){
        HashMap<String, Card> aCardPool = getAllCardsOfType(cardTypeEnum);

        for (Map.Entry<String, Card> entry : aCardPool.entrySet()) {
            //Define the Card and its key in the cardPool
            Card card = entry.getValue();
            card.setCardToOriginalHealth();
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Return a random number between two given values
     *
     * @param min minimum number value can be
     * @param max maximum number value can be
     *
     *  Created By Niamh McCartney
     */
    private int getRandNum(int min, int max){

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /**
     * Returns a HashMap containing all Cards of a specified type
     * @param cardTypeEnum Type of Cards to be returned in HashMap
     *
     * Created By Niamh McCartney
     */
    public HashMap<String, Card> getAllCardsOfType(CardType cardTypeEnum){
        try{
            return cardPoolCollection.get(cardTypeEnum);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(
                "CardStore.getAllCardsOfType: No Cards of type inputted exist ["
                        + e.getMessage() + "]");}
    }

    /**
     * Return a random Card `value` from a given HashMap
     * @param cardTypeEnum Type of random Card returned
     *
     *  Created By Niamh McCartney
     */
    public Card getRandCard(CardType cardTypeEnum) {
        HashMap<String, Card> aCardPool = getAllCardsOfType(cardTypeEnum);

        //Number of Cards in the cardPool
        int numOfCards = aCardPool.size();

        //generate a random number that equals the position of a Card in the cardPool
        int randNum = getRandNum(0, numOfCards-1);

        int num = 0;
        Card randCard = null;

        //Find and return the Card in the CardPool whose position
        //corresponds to the random number generated above
        for (Map.Entry<String, Card> entry : aCardPool.entrySet()) {
            if(num == randNum){
                randCard = entry.getValue();
                return randCard;
            }
            num++;
        }
        return randCard;
    }
}