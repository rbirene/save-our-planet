package uk.ac.qub.eeecs.game.cardDemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class CardStore {

    private FileIO mFileIO;

    private Game mGame;

    //Define the Card Name [Niamh McCartney]
    private String name;

    //Define the Card's Health and Attack values [Niamh McCartney]
    private int attackValue;
    private int healthValue;

    //Define the Card's Type [Niamh McCartney]
    private String cardType;

    //Define the Card's portrait image [Niamh McCartney]
    private Bitmap cardPortrait;

    //Define the Default float value [Niamh McCartney]
    private final float nullFloatValue = 0.0f;

    //Define the HashMap to contain the Cards [Niamh McCartney]
    private HashMap<String, Card> cardPool = new HashMap<>();;

    //CardStore Constructor [Niamh McCartney]
    public CardStore(Game game){

        mGame = game;
        mFileIO = mGame.getFileIO();
        loadCardAssets("txt/assets/Card.JSON");
    }

    /**
     * [Niamh McCartney]
     * The JSON file assumes the following format:
     *
     {
     "assets": [
     {
     "id": string,
     "name": string,
     "attackValue": int,
     "healthValue": int
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
                name = assets.getJSONObject(idx).getString("name");
                cardType = assets.getJSONObject(idx).getString("type");
                attackValue = assets.getJSONObject(idx).getInt("attackValue");
                healthValue = assets.getJSONObject(idx).getInt("healthValue");
                Card cardName = new Card(nullFloatValue, nullFloatValue, null, name, cardType, null, attackValue, healthValue);
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
     * @param LayerViewPortWidth  width/2 of Screen
     * @param LayerViewPortHeight height/2 of Screen
     *
     *  Created By Niamh McCartney
     */
    public HashMap<String, Card> getAllHeroCards(GameScreen gameScreen, float LayerViewPortWidth, float LayerViewPortHeight){
        HashMap<String, Card> heroCardPool = new HashMap<>();
        for (Map.Entry<String, Card> entry : cardPool.entrySet()) {
            String key = entry.getKey();
            Card value = entry.getValue();
            setGameScreenVariables(value, gameScreen, key, LayerViewPortWidth, LayerViewPortHeight);
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
     * @param LayerViewPortWidth  width/2 of Screen
     * @param LayerViewPortHeight height/2 of Screen
     *
     *  Created By Niamh McCartney
     */
    public HashMap<String, Card> getAllVillainCards(GameScreen gameScreen, float LayerViewPortWidth, float LayerViewPortHeight){
        HashMap<String, Card> heroCardPool = new HashMap<>();
        for (Map.Entry<String, Card> entry : cardPool.entrySet()) {
            String key = entry.getKey();
            Card value = entry.getValue();
            setGameScreenVariables(value, gameScreen, key, LayerViewPortWidth, LayerViewPortHeight);
            if(value.getCardType().equals("villainCard")){
                heroCardPool.put(key, value);
            }
        }
        return heroCardPool;
    }


    /**
     * set game screen variables equal to the properties of the game screen that accessed the CardStore
     *
     * @param card card instance
     * @param gameScreen game Screen the cards have been called by
     * @param key  of the chosen HashMap
     * @param LayerViewPortWidth  width/2 of Screen
     * @param LayerViewPortHeight height/2 of Screen
     *
     *  Created By Niamh McCartney
     */
    private Card setGameScreenVariables(Card card, GameScreen gameScreen, String key, float LayerViewPortWidth, float LayerViewPortHeight){
        Card.setGameScreen(gameScreen);
        card.setLayerViewPortWidth(gameScreen.getDefaultLayerViewport().x);
        card.setLayerViewPortHeight(gameScreen.getDefaultLayerViewport().y);
        card.createCardImages();
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
    //Get the Bitmap that has the same key as the inputted Card `card` [Niamh McCartney]
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

}
