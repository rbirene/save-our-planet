package uk.ac.qub.eeecs.game.cardDemo.Sprites;

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
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card;

public class CardStore {

    private FileIO mFileIO;

    private Game mGame;

    //Define the Card Name [Niamh McCartney]
    private String name;

    //Define the Card's Health and Attack values [Niamh McCartney]
    private int attackValue;
    private int healthValue;

    //Define the card Portraits scale
    private String scaleValuex;
    private String scaleValuey;
    private Vector2 scaleValue;

    //Define the card portraits Y-position
    private float portraitYPos;

    //Define the Card's Type [Niamh McCartney]
    private String cardType;

    //Define the Card's portrait image [Niamh McCartney]
    private Bitmap cardPortrait;

    //Define the Default float value [Niamh McCartney]
    private final float nullFloatValue = 0.0f;

    //Define the HashMap to contain the Cards [Niamh McCartney]
    private HashMap<String, Card> cardPool = new HashMap<>();

    //CardStore Constructor [Niamh McCartney]
    public CardStore(Game game) {
        mGame = game;
        mFileIO = mGame.getFileIO();
        loadCardAssets("txt/assets/Card.JSON");

    }
/**
 * * [Niamh McCartney]
     * The JSON file is in the following format:
     *
     {
     "assets": [
     {
     "id": string,
     "name": string,
     "scaleValuex": float,
     "scaleValuey": float,
     "portraitYPos": float
     },

     ]
     }     *
     *
     * @param assetsToLoadJSONFile JSON file to load and process
     */

    public void loadCardAssets(String assetsToLoadJSONFile) {
        // Attempt to load in the JSON asset details
        String loadedJSON;
        try {
            loadedJSON = mFileIO.loadJSON(assetsToLoadJSONFile);
        } catch (IOException e) {
            throw new RuntimeException(
                    "CardStore.constructor: Cannot load JSON [" + assetsToLoadJSONFile + "]");
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
                scaleValuex = assets.getJSONObject(idx).getString("scaleValuex");
                scaleValuey = assets.getJSONObject(idx).getString("scaleValuey");
                scaleValue = new Vector2(Float.parseFloat(scaleValuex), Float.parseFloat(scaleValuey));
                portraitYPos = Float.parseFloat(assets.getJSONObject(idx).getString("portraitYPos"));

                //randomise health and attack values
                attackValue = getRandNum(1, 99);
                healthValue = getRandNum(30,99);

                //create card
                Card cardName = new Card(nullFloatValue, nullFloatValue, null, name, cardType, null, scaleValue, attackValue, healthValue, portraitYPos);
                if(!cardPool.containsKey(name)){
                    cardPool.put(name, cardName);
                }
            }

        } catch (JSONException | IllegalArgumentException e) {
            throw new RuntimeException(
                    "CardStore.constructor: JSON parsing error [" + e.getMessage() + "]");
        }
    }

    /**
     * return all the hero cards in a HashMap
     *
     * @param gameScreen game Screen the cards have been called by
     *
     *  Created By Niamh McCartney
     */
    public HashMap<String, Card> getAllHeroCards(GameScreen gameScreen){
        HashMap<String, Card> heroCardPool = new HashMap<>();
        for (Map.Entry<String, Card> entry : cardPool.entrySet()) {
            String key = entry.getKey();
            Card value = entry.getValue();
            setGameScreenVariables(value, gameScreen, key, "HeroCardBackground");
            if(value.getCardType().equals("heroCard")){
                heroCardPool.put(key, value);
            }
        }
        return heroCardPool;
    }

    /**
     * return all the villain cards in a HashMap
     *
     * @param gameScreen game Screen the cards have been called by
     *
     *  Created By Niamh McCartney
     */
    public HashMap<String, Card> getAllVillainCards(GameScreen gameScreen){
        HashMap<String, Card> villainCardPool = new HashMap<>();
        for (Map.Entry<String, Card> entry : cardPool.entrySet()) {
            String key = entry.getKey();
            Card value = entry.getValue();
            setGameScreenVariables(value, gameScreen, key, "VillainCardBackground");
            if(value.getCardType().equals("villainCard")){
                villainCardPool.put(key, value);
            }
        }
        return villainCardPool;
    }


    /**
     * set game screen variables equal to the properties of the game screen that accessed the CardStore
     *
     * @param card card instance
     * @param gameScreen game Screen the cards have been called by
     * @param key  of the chosen HashMap
     * @param cardBackgroundName name of the cardBackground image from the JSON file
     *
     *  Created By Niamh McCartney
     */
    private Card setGameScreenVariables(Card card, GameScreen gameScreen, String key, String cardBackgroundName){
        Card.setGameScreen(gameScreen);
        card.setLayerViewPortWidth(gameScreen.getDefaultLayerViewport().x);
        card.setLayerViewPortHeight(gameScreen.getDefaultLayerViewport().y);
        card.createCardImages(cardBackgroundName);
        getCardBitmap(gameScreen, key, card);
        return card;
    }

    /**
     * Get the Bitmap that has the same key as the inputted Card `card`
     *
     * @param gameScreen game Screen the cards have been called by
     * @param key  of the chosen HashMap
     * @param card card instance
     *
     *  Created By Niamh McCartney
     */
    private void getCardBitmap(GameScreen gameScreen, String key, Card card){
        try {
            AssetManager assetManager = gameScreen.getGame().getAssetManager();
            cardPortrait = assetManager.getBitmap(key);
            card.setCardPortrait(cardPortrait);
        }catch(Exception e){
            throw new RuntimeException(
                    "CardStore.getCardBitmap: Bitmap search error [" + e.getMessage() + "]");
        }
    }

    /**
     * Return a random Card `value` from a given HashMap
     *
     * @param cardPool HashMap containing a number of Cards
     *
     *  Created By Niamh McCartney
     */
    public Card getRandCard(HashMap<String, Card> cardPool) {
        int numOfCards = cardPool.size();

        Random rand = new Random();
        int randNum = rand.nextInt((numOfCards-1) + 1);

        int num = 0;
        Card value = null;

        for (Map.Entry<String, Card> entry : cardPool.entrySet()) {
            if(num == randNum){
                value = entry.getValue();
                return value;
            }
            num++;
        }
        return null;
    }

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
}