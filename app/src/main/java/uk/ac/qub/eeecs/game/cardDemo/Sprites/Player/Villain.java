package uk.ac.qub.eeecs.game.cardDemo.Sprites.Player;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.qub.eeecs.game.cardDemo.Boards.GameBoardObjects.CardHolder;
import uk.ac.qub.eeecs.game.cardDemo.DifficultyLevels;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Card.Card;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by [Irene Bhuiyan]
 * This class represents a villain in the game (using traits and behaviours from Player).
 *
 */

public class Villain extends Player {

    // /////////////////////////////////////////////////////////////////////////
    // Constructor
    // /////////////////////////////////////////////////////////////////////////
    private ArrayList<Card> playerCards = new ArrayList<>();
    private ArrayList<CardHolder> containers = new ArrayList<>();
    private ArrayList<CardHolder> enemyContainers = new ArrayList<>();
    private  Card targetCard;
    private Card attackCard;

    /**
     *
     * Create a new villain.
     *
     */

    public Villain(Bitmap portrait){
        super(0.0f, 0.0f, "Ronald Rump", null, portrait);
    }

    public void playAI() {

        Random rand = new Random();

            containers.addAll(gameBoard.getVillianContainers());
            enemyContainers.addAll(gameBoard.getHeroContainers());

            if (!playerCards.isEmpty()) {
                int n = rand.nextInt(containers.size() - 1);
                int x = rand.nextInt(playerCards.size() - 1);

                for (int i = 0; i < containers.size(); i++) {
                    if (containers.get(n).isEmpty()) {
                        containers.get(n).AddCardToHolder(playerCards.get(x));
                        containers.get(n).returnCardHeld().setCardFlipped(false);
                        playerCards.remove(x);

                    }

                }
            }
        }


    public void setPlayerCards(ArrayList<Card> cards) {
        playerCards.addAll(cards);
        Log.d("TAG", String.valueOf(playerCards.size()));
    }


   /** public void AIAttack(){}{

     //   containers.addAll(gameBoard.getVillianContainers());
       // enemyContainers.addAll(gameBoard.getHeroContainers());
        Random rand = new Random();

            for(int i=0;i<enemyContainers.size();i++){
                if(!enemyContainers.get(i).isEmpty()){
                    targetCard = enemyContainers.get(i).returnCardHeld();
                    int n = rand.nextInt(containers.size()-1);
                    attackCard = containers.get(n).returnCardHeld();
                    targetCard.setHealthValue(targetCard.getHealthValue() - attackCard.getAttackValue());
                    gameBoard.playAttackAnimation(enemyContainers.get(i));

                }
            }
        }
    **/


   
    public void AICardSelect() {


        DifficultyLevels tempdiff;
        tempdiff = getGameBoard().getGameScreen().getGame().getDifficultyLevel();

        if (tempdiff == DifficultyLevels.EASY) {
            /**
             * For easy, play a random card (may change to play lowest value card)
             */
            playAI();
        }else if (tempdiff == DifficultyLevels.NORMAL) {
            /**
             * For normal, find the two best cards, and random roll on either to play the best card, or second to best card.
             */
            Random rand = new Random();
            containers.addAll(gameBoard.getVillianContainers());
            enemyContainers.addAll(gameBoard.getHeroContainers());

            int n = rand.nextInt(containers.size()-1);

            int cardtochoose = 0;
            int secondbestcard = 0;
            int temp = 0;
            int secondbestvalue = 0;
            for (int i = 0; i < playerDeck.getSize()-1; i++) {
                int total = 0;


                total += playerCards.get(i).getHealthValue();
                total += playerCards.get(i).getAttackValue();

                if (total > temp) {
                    secondbestvalue = temp;
                    temp = total;
                    secondbestcard = cardtochoose;
                    cardtochoose = i;

                }

                total = 0;
            }

            int random = 5 + rand.nextInt(100 - 1 + 1);
            if (random > 51) {
                if (!playerCards.isEmpty()) {
                    for (int i = 0; i < containers.size(); i++) {
                        if (containers.get(n).isEmpty()) {
                            containers.get(n).AddCardToHolder(playerCards.get(cardtochoose));

                        }
                    }
                    playerCards.remove(cardtochoose);
                }
            } else
            if (!playerCards.isEmpty()) {
                for (int i = 0; i < containers.size(); i++) {
                    if (containers.get(n).isEmpty()) {
                        containers.get(n).AddCardToHolder(playerCards.get(secondbestcard));
                    }
                }
                playerCards.remove(secondbestcard);
            }




        } else if (tempdiff == DifficultyLevels.HARD) {
            /**
             * For Hard, play the card with the highest combined attack and health value.
             */
            playerCards.trimToSize();
            containers.addAll(gameBoard.getVillianContainers());
            enemyContainers.addAll(gameBoard.getHeroContainers());
            Random rand = new Random();

            int n = rand.nextInt(containers.size()-1);

            int cardtochoose = 0;
            int temp = 0;
            for (int i = 0; i < playerCards.size()-1;i++) {
                int total = 0;


                total += playerCards.get(i).getAttackValue();
                total += playerCards.get(i).getHealthValue();
                if (total > temp) {
                    temp = total;
                    cardtochoose = i;
                }
            }

            int choosencard = cardtochoose;
            if (!playerCards.isEmpty()) {
                for (int j = 0; j < containers.size(); j++) {
                    if (containers.get(n).isEmpty()) {
                        containers.get(n).AddCardToHolder(playerCards.get(choosencard));
                    }
                }

                playerCards.remove(choosencard);
            }
        }
    }

    public  void takeFirstTurn(){

    }


    public void AIattackPhase(int selectedAttackCard, int cardToAttack) {

        if(!containers.get(selectedAttackCard).isEmpty() &&
                !enemyContainers.get(cardToAttack).isEmpty()) {
            Card attackCard = containers.get(selectedAttackCard).returnCardHeld();
            // SLEEP METHOD TAKEN FROM https://stackoverflow.com/questions/23283041/how-to-make-java-delay-for-a-few-seconds/48403623




            float heroCardX = enemyContainers.get(cardToAttack).getX();
            float heroCardY = enemyContainers.get(cardToAttack).getY();
            attackCard.setPosition(enemyContainers.get(cardToAttack).position.x,enemyContainers.
                    get(cardToAttack).position.y);

            pause();

            if(attackCard.getBound().intersects(enemyContainers.get(cardToAttack).getBound())) {
                gameBoard.playAttackAnimation(enemyContainers.get(cardToAttack));
                enemyContainers.get(cardToAttack).returnCardHeld().setHealthValue
                        (enemyContainers.get(cardToAttack).
                                returnCardHeld().getHealthValue() - attackCard.getAttackValue());
                containers.get(selectedAttackCard).AddCardToHolder(attackCard);
            }
        }

    }

    public void AICardAttack() {
        DifficultyLevels tempdiff;
        tempdiff = getGameBoard().getGameScreen().getGame().getDifficultyLevel();
        if (tempdiff == DifficultyLevels.EASY) {
            // If easy, attack card with highest health with lowest attack.

            containers.addAll(gameBoard.getVillianContainers());
            enemyContainers.addAll(gameBoard.getHeroContainers());
            Random rand = new Random();


            int cardtochoose = 0;
            int temp = 0;
            for (int i = 0; i < enemyContainers.size() - 1; i++) {
                if (!enemyContainers.get(i).isEmpty()) {
                    int total = 0;
                    total += enemyContainers.get(i).returnCardHeld().getHealthValue();
                    if (total > temp) {
                        temp = total;
                        cardtochoose = i;
                    }
                }

            }

            int cardtochoose1 = 0;
            int temp1 = 100;
            for (int i = 0; i < containers.size() - 1; i++) {
                if (!containers.get(i).isEmpty()) {
                    int total1 = 0;
                    total1 += containers.get(i).returnCardHeld().getAttackValue();
                    if (total1 < temp1) {
                        temp = total1;
                        cardtochoose1 = i;
                    }
                }

            }

            AIattackPhase(cardtochoose1, cardtochoose);
        }


        else if(tempdiff == DifficultyLevels.NORMAL) {

            playerCards.trimToSize();
            containers.addAll(gameBoard.getVillianContainers());
            enemyContainers.addAll(gameBoard.getHeroContainers());
            int temp = 0;
            int cardtochoose= 0;
            Random rand = new Random();

            for (int i = 0; i < enemyContainers.size()-1;i++) {
                if (!enemyContainers.get(i).isEmpty()) {
                    int total = 0;
                    total += enemyContainers.get(i).returnCardHeld().getHealthValue();
                    total += enemyContainers.get(i).returnCardHeld().getAttackValue();
                    if (total > temp) {
                        temp = total;
                        cardtochoose = i;
                    }
                }

            }

            int random = 5 + rand.nextInt(100 - 1 + 1);
            if (random > 51) {
                int n = rand.nextInt(containers.size()-1);
                AIattackPhase(n,cardtochoose);

            }

            else {
                int n = rand.nextInt(containers.size()-1);
                int x = rand.nextInt(playerCards.size()-1);

                AIattackPhase(n,x);

            }




        }



        else if(tempdiff == DifficultyLevels.HARD) {
            playerCards.trimToSize();
            containers.addAll(gameBoard.getVillianContainers());
            enemyContainers.addAll(gameBoard.getHeroContainers());
            int latestcardsamerange;
            boolean cardwithinrange = false;
            int temp = 0;
            int cardtochoose = 0;
            int cardtochoose1 = 0;
            //Choose card to attack with
            for (int i = 0; i < containers.size()-1;i++) {
                if (!containers.get(i).isEmpty()) {
                    int total = 0;
                    total += containers.get(i).returnCardHeld().getAttackValue();
                    if (total > temp) {
                        temp = total;
                        cardtochoose = i;
                    }
                }

            }




            for (int i = 0; i < enemyContainers.size()-1;i++) {
                if (!enemyContainers.get(i).isEmpty()) {
                    if (enemyContainers.get(i).returnCardHeld().getHealthValue() < containers.get(cardtochoose).returnCardHeld().getAttackValue()) {
                        latestcardsamerange = i;
                        cardtochoose1 = latestcardsamerange;
                        cardwithinrange = true;

                    }
                }

            }

            if (cardwithinrange = false) {


                for (int i = 0; i < enemyContainers.size()-1;i++) {
                    if (!enemyContainers.get(i).isEmpty()) {
                        int total = 0;
                        total += enemyContainers.get(i).returnCardHeld().getAttackValue();
                        if (total > temp) {
                            temp = total;
                            cardtochoose = i;
                        }
                    }

                }


            }
            AIattackPhase(cardtochoose,cardtochoose1);
        }
    }




    // METHOD TAKEN FROM https://stackoverflow.com/questions/38005366/how-do-i-have-java-wait-a-second-before-executing-the-next-line-without-try-cat
    static void pause(){
        long Time0 = System.currentTimeMillis();
        long Time1;
        long runTime = 0;
        while (runTime < 1000) { // 1000 milliseconds or 1 second
            Time1 = System.currentTimeMillis();
            runTime = Time1 - Time0;
        }
    }













}










/**
 public DifficultyLevels diff;
 private int easydifficulty = diff.getDifficultyvalue(1);
 private int normaldifficulty = diff.getDifficultyvalue(2);
 private  int harddifficulty = diff.getDifficultyvalue(3);
 private BattleScreen playScreen;
 private int turns = playScreen.getTurnNumber(playScreen.turnnumber);


 // get Villain Deck from the BattleScreen
 public ArrayList<Card> getVillainDeck() {
 return this.playerDeck.getDeck(playScreen);
 }

 /**
 public void update(ElapsedTime elapsedTime) {
 super.update(elapsedTime);
 // If it's the AI's turn, and the game hasn't been paused for a bonus question or paused in general, the AI can calculate and play its turns.
 if (playScreen.getTurnNumber(playScreen.turnnumber) == 2) {
 if(!playScreen.paused) {

 EndTurn();
 }
 }
 }

 /**
 public void AttackCardLowHealth() {
 int selectedherocard;
 // In specific situations (like hard or normal difficulty) We want to find a card to attack that will destroy the card, but will not destroy the AI's card also
 // Replace getPlayerDeck with getActiveCards as AI should not be able to see what cards the player has that they have not used.
 for(int i = 0; i < getPlayerDeck().getSize();i++) {
 int cardtoattack = 0;
 if (!this.hasAttacked) {
 if (getVillainDeck().get(i).getCardAttack() > getPlayerDeck().getDeck(playScreen).get(i).getCardHealth() &&
 getPlayerDeck().getDeck(playScreen).get(i).getCardAttack() > getVillainDeck().get(i).getCardHealth() ;
 cardtoattack = i;
 }
 attackCard(i,selectedherocard);
 }
 }


 public void AttackCardWithHighAttack() {
 // card to attack
 int selectedherocard = 0;

 // for every active card in the villain's board, find the one with the highest attack points and attack
 // replace getVillainDeck with active cards
 for (int i = 0; i < getPlayerDeck().getSize(); i++) {
 if (getVillainDeck().get(i).getCardAttack() > getVillainDeck().get(i + 1).getCardAttack) {


 attackCard(i,selectedherocard);
 //set card to has attacked.

 }
 }
 }



 public void attackCard(int selectedvillaincard, int selectedherocard) {
 // Yet to be determined how to officially attack cards in the Battle Screen.
 //Parameters are selecting card to attack with, and the card to attack.
 }


 public void EndTurn() {
 if (this.hasPlayedCard && this.hasAttacked) ;
 {
 turns--;
 playScreen.setTurnNumber(turns);


 }
 }

 public void PlayCard(int cardindex) {
 if (!this.hasPlayedCard) {
 // If the villain has not played a card, and there is cards in their deck, play the first one.

 if (diff.getDifficultyvalue(easydifficulty) == 1) ;
 {
 for (int i = 0; i < getVillainDeck().size(); i++) {
 if (getVillainDeck().size() != 0) {
 PlayCard(i);
 cardindex = i;

 }
 }
 }
 }
 }

 /**
 private void basicAttackEasy() {
 //if difficulty is easy, there is a 40% chance to attack the card with the lowest health,
 // if not successful, a 30% chance roll will occur to attack a random card or not at all
 if (!this.hasAttacked)

 {
 int selectedherocard;

 if (diff.getDifficultyvalue(easydifficulty) == 1) ;
 {
 int Random = (int)(Math.random()*100);

 if (Random > 60) {
 int cardToAttack;
 for (int i = 0; i < getPlayerDeck().getSize();i++) {
 int card = 0;
 if (getPlayerDeck().getDeck(playScreen).get(card).getCardHealth() > getPlayerDeck().getDeck(playScreen).get(card+1).getCardHealth(); {
 cardToAttack = card+1;
 }else {
 cardToAttack = card;
 attackCard(card,selectedherocard);
 //change has not attacked to attacked.

 }
 }
 }
 else if (Random <= 60) {
 int randomroll2 = (int)(Math.random()*100);
 if (randomroll2 < 30) {
 int randomcard = (int) (Math.random()*getPlayerDeck().getDeck(playScreen).size());
 attackCard(randomcard, selectedherocard);

 //change has not attacked to attacked.
 }else {
 // add code to change to attacked,even though a card was not attacked, change so it misses the turn because of failed chance.
 }
 }

 }

 }
 }

 private void basicAttackNormal() {

 if (diff.getDifficultyvalue(normaldifficulty) == 2); {
 //if difficulty is normal, there is a 65% chance to attack the card with the lowest health, if not successful, a 80% chance roll will occur to attack a random card
 int Random = (int)(Math.random()*100);
 int selectedherocard;
 if (Random > 35) {
 int cardToAttack;
 for (int i = 0; i < getPlayerDeck().getSize();i++) {
 int card = 0;
 if (getPlayerDeck().getDeck(playScreen).get(card).getCardHealth() > getPlayerDeck().getDeck(playScreen).get(card+1).getCardHealth(); {
 cardToAttack = card+1;
 }else {
 cardToAttack = card;
 attackCard(card,selectedherocard);
 //change has not attacked to attacked.

 }
 else if (Random <= 35) {
 int randomroll2 = (int) (Math.random() * 100);
 if (randomroll2 < 30) {
 int randomcard = (int) (Math.random() * getPlayerDeck().getDeck(playScreen).size());
 attackCard(randomcard,selectedherocard);

 //change has not attacked to attacked.
 }
 }
 }

 }


 }
 }
 private void basicAttackHard() {
 if(diff.getDifficultyvalue(harddifficulty) == 3) {
 int cardToAttack;
 // if difficulty is hard, card will always attack the card with the least health
 for (int i = 0; i < getPlayerDeck().getSize();i++) {
 int card = 0;
 if (getPlayerDeck().getDeck(playScreen).get(card).getCardHealth() > getPlayerDeck().getDeck(playScreen).get(card+1).getCardHealth(); {
 cardToAttack = card+1;
 }else {
 cardToAttack = card;
 attackCard(card,cardToAttack);
 //change has not attacked to attacked.

 }
 }
 }
 }





 }


 **/

