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
import uk.ac.qub.eeecs.gage.world.GameScreen;
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
    private String cardType;

    //Define the Card portrait image
    private Bitmap cardPortrait;

    //Define the HashMap to contain all the Cards
    private HashMap<String, Card> cardPool = new HashMap<>();

    //Define the HashMap to contain the Hero Cards
    private HashMap<String, Card> heroCardPool;

    //Define the HashMap to contain the Villain Cards
    private HashMap<String, Card> villainCardPool;

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
        this.heroCardPool = new HashMap<>();
        this.villainCardPool = new HashMap<>();

        //Load the assets used by the Cards
        loadCardAssets();

        //Load and create the Card objects to be used in the game
        loadCardObjects("txt/assets/Card.JSON");

        //Separate the Cards by CardType and add them to the relevant HashMaps
        separateByCardType("heroCard", heroCardPool);
        separateByCardType("villainCard", villainCardPool);
    }
   /**
     * The JSON file is in the following format:
     *
     {
     "assets": [
     {
     "id": string,
     "name": string,
     "portraitScaleValueX": float,
     "portraitScaleValueY": float,
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
                cardType = assets.getJSONObject(idx).getString("type");
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
                getCardPortraitBitmap(name);

                //create card object
                if(!cardPool.containsKey(name)) {
                    createCard();
                }else{ throw new RuntimeException(
                        "CardStore.constructor: JSON parsing error " +
                                "["+"Multiple Cards in text file with same name"+"]");}
            }

        } catch (JSONException | IllegalArgumentException e) {
            throw new RuntimeException(
                    "CardStore.loadCardObjects: JSON parsing error [" + e.getMessage() + "]");
        }
    }

    /**
     * Creates a HeroCard or VillainCard
     * object depending on the cardType
     *
     *  Created By Niamh McCartney
     */
    private void createCard(){
        Card card = null;
        if (cardType.equals("villainCard")) {
            card = new VillainCard(mGame, name, cardPortrait, portraitScaleValue,
                    attackValue, healthValue, portraitYPos);

        }else if(cardType.equals("heroCard")){
            card = new HeroCard(mGame, name, cardPortrait, portraitScaleValue,
                    attackValue, healthValue, portraitYPos);
        }
        //add the Card to the cardPool
        cardPool.put(name, card);
    }

    /**
     * Get all Cards of a given type in the
     * CardStore and store them in the
     * provided HashMap
     *
     * @param cardType Type of Card to store
     * @param cardTypePool HashMap to store Cards in
     *
     *  Created By Niamh McCartney
     */
    private void separateByCardType(String cardType, HashMap<String, Card> cardTypePool){
        for (Map.Entry<String, Card> entry : this.cardPool.entrySet()) {
            //Define the Card and its key in the cardPool
            String key = entry.getKey();
            Card card = entry.getValue();

            if(card.getCardType().equals(cardType)){
                cardTypePool.put(key, card);
            }
        }
    }

    /**
     * Get the Bitmap whose name matches
     * the name of the Card inputted
     *
     * @param name Name of the Card
     *
     *  Created By Niamh McCartney
     */
    private void getCardPortraitBitmap(String name){
        try {
            cardPortrait = assetManager.getBitmap(name);
        }catch(Exception e){
            throw new RuntimeException(
                    "CardStore.getCardPortraitBitmap: Bitmap search error ["
                            + e.getMessage() + "]");
        }
    }

    /**
     * Renew the health of all the Hero cards
     *
     *  Created By Niamh McCartney
     */
    public void renewHealthOfHeroCards(){
        for (Map.Entry<String, Card> entry : this.heroCardPool.entrySet()) {
            //Define the Card and its key in the cardPool
            Card card = entry.getValue();
            card.setCardToOriginalHealth();
        }
    }

    /**
     * Renew the health of all the Villain cards
     *
     *  Created By Niamh McCartney
     */
    public void renewHealthOfVillainCards(){
        for (Map.Entry<String, Card> entry : this.villainCardPool.entrySet()) {
            //Define the Card and its key in the cardPool
            Card card = entry.getValue();
            card.setCardToOriginalHealth();
        }
    }

    /**
     * Load Assets used by screen
     *
     * Created By Niamh McCartney
     */
    private void loadCardAssets() {
        assetManager.loadAssets("txt/assets/CardAssets.JSON");
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
     * Return all the hero cards in the CardStore
     * @param gameScreen game Screen the cards have been called by
     *
     *  Created By Niamh McCartney
     */
    public HashMap<String, Card> getAllHeroCards(GameScreen gameScreen){
        for (Map.Entry<String, Card> entry : heroCardPool.entrySet()) {
            //Define the Card from the cardPool
            Card card = entry.getValue();
            card.setGameScreen(gameScreen);
        }
        return heroCardPool;
    }

    /**
     * Return all the villain cards in the CardStore
     * @param gameScreen game Screen the cards have been called by
     *
     *  Created By Niamh McCartney
     */
    public HashMap<String, Card> getAllVillainCards(GameScreen gameScreen){

        for (Map.Entry<String, Card> entry : villainCardPool.entrySet()) {
            //Define the Card from the cardPool
            Card card = entry.getValue();
            card.setGameScreen(gameScreen);
        }
        return villainCardPool;
    }

    /**
     * Return a random Card `value` from a given HashMap
     * @param cardPool HashMap containing Cards that the
     *                 random Card will be chosen from
     *
     *  Created By Niamh McCartney
     */
    public Card getRandCard(HashMap<String, Card> cardPool) {
        //Number of Cards in the cardPool
        int numOfCards = cardPool.size();

        //generate a random number that equals the position of a Card in the cardPool
        int randNum = getRandNum(0, numOfCards-1);

        int num = 0;
        Card randCard = null;

        //Find and return the Card in the CardPool whose position
        //corresponds to the random number generated above
        for (Map.Entry<String, Card> entry : cardPool.entrySet()) {
            if(num == randNum){
                randCard = entry.getValue();
                return randCard;
            }
            num++;
        }
        return null;
    }
}