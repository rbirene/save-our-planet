package uk.ac.qub.eeecs.gage.ScreenTests;


import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.TestGame;
import uk.ac.qub.eeecs.game.cardDemo.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Screens.MenuScreen;
import uk.ac.qub.eeecs.game.cardDemo.Boards.GameBoardObjects.CardHolder;
import uk.ac.qub.eeecs.game.cardDemo.Screens.BattleScreen;
import uk.ac.qub.eeecs.game.cardDemo.Screens.ChooseCardScreen;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import uk.ac.qub.eeecs.game.cardDemo.Boards.GameBoard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.VillainCard;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player.Hero;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player.Villain;
import uk.ac.qub.eeecs.game.cardDemo.Enums.DifficultyLevels;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import static junit.framework.Assert.assertTrue;



@RunWith(AndroidJUnit4.class)
public class AIBattleScreenTests {
    private TestGame Game;
    private BattleScreen battleDemo;
    private ChooseCardScreen chooseCardScreenDemo;
    private MenuScreen menuScreenDemo;
    private GameBoard gameBoard;
    private Bitmap bitmap;
    private Hero hero;
    private GameScreen gameScreen;
    private Villain villain;
    private ArrayList<Card> playerCards = new ArrayList<>();
    private ArrayList<CardHolder> containers = new ArrayList<>();
    private ArrayList<CardHolder> enemyContainers = new ArrayList<>();
    private Deck villainDeck;

    String cardType;
    String cardName;
    Card aCard;
    Card aCard2;
    Card aCard3;
    Card aCard4;
    Card aCard5;
    Card aCard6;
    private DifficultyLevels tempDiff;



    @Before
    public void setUp() {

        villain = new Villain(bitmap);


        Game = new TestGame(1280,720);
        Game.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        menuScreenDemo = new MenuScreen(Game);
        chooseCardScreenDemo = new ChooseCardScreen(Game);
        battleDemo = new BattleScreen(Game);
        Game.getScreenManager().addScreen(battleDemo);
        gameScreen = Game.getScreenManager().getScreen("battleDemo");


        gameBoard = new GameBoard(1280,720,1280,720,bitmap,battleDemo);
        containers = gameBoard.getVillianContainers();
        enemyContainers = gameBoard.getHeroContainers();

        cardType = "cardType";
        cardName = "cardName";
        aCard = new VillainCard(Game, cardName,null, null, 20, 30, 0.3f);
        aCard2 = new VillainCard(Game, cardName,null, null, 5, 9, 0.3f);
        aCard3 = new VillainCard(Game, cardName,null, null, 15, 20, 0.3f);
        aCard4 = new HeroCard(Game, cardName,null, null, 5, 20, 0.3f);
        aCard5 = new HeroCard(Game, cardName,null, null, 5, 15, 0.3f);
        aCard6 = new HeroCard(Game, cardName,null, null, 5, 9, 0.3f);


        villain.setGameBoard(gameBoard);
        villain.setGameScreen(battleDemo);
        playerCards.add(aCard);
        playerCards.add(aCard2);
        playerCards.add(aCard3);
        villain.setPlayerCards(playerCards);
    }


    @Test
    public void addCardToHolder() {
        villain.playAI(1);
        Assert.assertTrue("Passed",villain.getPlayerCards().size() < 3);

    }

    @Test
    public void cardAddEasy() {
        //Play lowest card health+attack on easy
        tempDiff = DifficultyLevels.EASY;
        Game.setDifficultyLevel(tempDiff);
        villain.AICardSelect();
        Assert.assertTrue(!villain.getPlayerCards().contains(aCard2));
    }

    @Test
    public void cardAddHard() {
        //Play highest card health+attack on Hard
        tempDiff = DifficultyLevels.HARD;
        Game.setDifficultyLevel(tempDiff);
        villain.AICardSelect();
        Assert.assertTrue(!villain.getPlayerCards().contains(aCard));
    }

    @Test
    public void attackCard() {
        //make sure AI attacks card, card has 20 attack, enemy card has 20 health, test if
        //other card has 0 health
        enemyContainers.get(0).AddCardToHolder(aCard4);
        villain.playAI(0);
        villain.AICardAttack();
        Assert.assertTrue("Passed",enemyContainers.get(0).returnCardHeld().getHealthValue() == 0);

    }


    @Test
    public void attackCardEasy() {
        // Attack Highest enemy health card on EASY, test other cards have same health and the highest
        //card has less health.
        tempDiff = DifficultyLevels.EASY;
        Game.setDifficultyLevel(tempDiff);
        enemyContainers.get(0).AddCardToHolder(aCard4);
        enemyContainers.get(1).AddCardToHolder(aCard5);
        enemyContainers.get(2).AddCardToHolder(aCard6);
        containers.get(0).AddCardToHolder(aCard);
        containers.get(1).AddCardToHolder(aCard2);
        containers.get(2).AddCardToHolder(aCard3);
        villain.AICardAttack();
        Assert.assertTrue("Passed",enemyContainers.get(0).returnCardHeld().getHealthValue() < 20);
        Assert.assertTrue("Passed",enemyContainers.get(1).returnCardHeld().getHealthValue() == 15);
        Assert.assertTrue("Passed",enemyContainers.get(2).returnCardHeld().getHealthValue() == 9);


    }

    @Test
    public void attackCardHard() {
        // Attack lowest enemy health card on HARD with the card that has the HIGHEST attack, test other cards have same health and the highest
        //card has less health (aCard6 had lowest health with 9,aCard has highest attack with 20
        // 9-20 == -11.
        tempDiff = DifficultyLevels.HARD;
        Game.setDifficultyLevel(tempDiff);
        enemyContainers.get(0).AddCardToHolder(aCard4);
        enemyContainers.get(1).AddCardToHolder(aCard5);
        enemyContainers.get(2).AddCardToHolder(aCard6);
        containers.get(0).AddCardToHolder(aCard);
        containers.get(1).AddCardToHolder(aCard2);
        containers.get(2).AddCardToHolder(aCard3);
        villain.AICardAttack();
        Assert.assertTrue("Passed",enemyContainers.get(0).returnCardHeld().getHealthValue() == 20);
        Assert.assertTrue("Passed",enemyContainers.get(1).returnCardHeld().getHealthValue() == 15);
        Assert.assertTrue("Passed",enemyContainers.get(2).returnCardHeld().getHealthValue() == -11);

    }

}