package uk.ac.qub.eeecs.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.game.cardDemo.BattleScreen;
import uk.ac.qub.eeecs.game.cardDemo.Card;
import uk.ac.qub.eeecs.game.cardDemo.ChooseCardScreen;
import uk.ac.qub.eeecs.game.cardDemo.Deck;
import uk.ac.qub.eeecs.game.cardDemo.SplashScreen;
import uk.ac.qub.eeecs.gage.Game;

/**
 * Sample demo game that is create within the MainActivity class
 *
 * @version 1.0
 */
public class DemoGame extends Game {

    private HashMap<String, Card> heroCardPool;
    private HashMap<String, Card> screenCardPool = new HashMap<>();

    private Card randCard;

    private Card Card01;
    private Card Card02;
    private Card Card03;

    private Deck heroDeck;

    private AssetManager assetManager = getAssetManager();

    /**
     * Create a new demo game
     */
    public DemoGame() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see uk.ac.qub.eeecs.gage.Game#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Go with a default 20 UPS/FPS
        setTargetFramesPerSecond(20);

//        assetManager.loadAssets("txt/assets/CardAssets.JSON");
//
//        // get all the cards of type hero
//        heroCardPool = getCardStore().getAllHeroCards(null);
//
//        //Create Hero Deck
//        createHeroDeck();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Call the Game's onCreateView to get the view to be returned.
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Create and add a stub game screen to the screen manager. We don't
        // want to do this within the onCreate method as the menu screen
        // will layout the buttons based on the size of the view.

        SplashScreen SplashScreen = new SplashScreen(this);
        mScreenManager.addScreen(SplashScreen);

       //BattleScreen battleScreen = new BattleScreen(this);
       //mScreenManager.addScreen(battleScreen);


        return view;
    }

    @Override
    public boolean onBackPressed() {
        // If we are already at the menu screen then exit
        if (mScreenManager.getCurrentScreen().getName().equals("MenuScreen"))
            return false;

        // Stop any playing music
        if(mAudioManager.isMusicPlaying())
            mAudioManager.stopMusic();

        // Go back to the menu screen
        getScreenManager().removeAllScreens();
        MenuScreen menuScreen = new MenuScreen(this);
        getScreenManager().addScreen(menuScreen);
        return true;
    }

//    /**
//     * generates the cards to present to the player
//     *
//     * @param numOfCards the number of cards to be generated
//     *
//     *  Created By Niamh McCartney
//     */
//    private void generateRandCards(int numOfCards){
//        int num = 0;
//        while(num<numOfCards) {
//            randCard = getCardStore().getRandCard(heroCardPool);
//            String name = randCard.getCardName();
//            //If Card is not already chosen then add to the HashMap
//            if(!screenCardPool.containsKey(name)) {
//                screenCardPool.put(name, randCard);
//                num++;
//                if (num == 1) {
//                    Card01 = randCard;
//                    Log.d("ifStatement" ,randCard.getCardName());
//                }
//                if (num == 2) {
//                    Card02 = randCard;
//                    Log.d("ifStatement" ,randCard.getCardName());
//                }
//                if (num == 3) {
//                    Card03 = randCard;
//                    Log.d("ifStatement" ,randCard.getCardName());
//                }
//            }
//
//        }
//    }
//
//    private void createHeroDeck(){
//        generateRandCards(3);
//        heroDeck = new Deck(Card01, Card02, Card03);
//        //heroDeck.getCard01();
////        Log.d("deck" ,heroDeck.getCard01(this).getCardName());
////        Log.d("deck" ,heroDeck.getCard02(this).getCardName());
////        Log.d("deck" ,heroDeck.getCard02(this).getCardName());
//        getHero().setHeroDeck(heroDeck);
//    }
}