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
    private Random rand = new Random();

    /**
     *
     * Create a new villain.
     *
     */

    public Villain(Bitmap portrait){
        super(0.0f, 0.0f, "Ronald Rump", null, portrait);
    }

    public void playAI(int x) {

        Random rand = new Random();

            containers.addAll(gameBoard.getVillianContainers());
            enemyContainers.addAll(gameBoard.getHeroContainers());

            if (!playerCards.isEmpty()) {
                int n = rand.nextInt(containers.size() - 1);
                if(!containers.get(n).isEmpty()) {
                    n = rand.nextInt(containers.size()-1);
                }


                for (int i = 0; i <= containers.size()-1; i++) {
                    if (containers.get(n).isEmpty()) {
                        containers.get(n).AddCardToHolder(playerCards.get(x));
                        containers.get(n).returnCardHeld().setCardFlipped(false);
                        playerCards.remove(x);

                    }

                }
            }
        }


    public void setPlayerCards(ArrayList<Card> cards) {
        if(playerCards.size() == 0) {
            playerCards.addAll(cards);


        }

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

   public void AI() {
       if(playerCards.size() == 3) {
           AICardSelect();
       }
       else if (playerCards.size() == 0) {
           AICardAttack();
       }
       else {
           int random = rand.nextInt(100) + 1;
           if(random >= 51) {
               AICardAttack();
           }else
               AICardSelect();
       }

   }


   
    public void AICardSelect() {


        DifficultyLevels tempdiff;
        tempdiff = getGameBoard().getGameScreen().getGame().getDifficultyLevel();

        if (tempdiff == DifficultyLevels.EASY) {
            /**
             * For Easy, play the card with the lowest combined attack and health value.
             */


            int cardtochoose = 0;
            int temp = 100;
            for (int i = 0; i < playerCards.size()-1;i++) {
                int total = 0;


                total += playerCards.get(i).getAttackValue();
                total += playerCards.get(i).getHealthValue();
                if (total < temp) {
                    temp = total;
                    cardtochoose = i;
                }
            }

            int choosencard = cardtochoose;
            playAI(cardtochoose);

        }else if (tempdiff == DifficultyLevels.NORMAL) {
            /**
             * For normal, find the two best cards, and random roll on either to play the best card, or second to best card.
             */
            Random rand = new Random();

            int cardtochoose = 0;
            int secondbestcard = 0;
            int temp = 0;
            int secondbestvalue = 0;
            for (int i = 0; i < playerCards.size()-1; i++) {
                int total = 0;


                total += playerCards.get(i).getHealthValue();
                total += playerCards.get(i).getAttackValue();

                if (total > temp) {
                    secondbestvalue = temp;
                    temp = total;
                    secondbestcard = cardtochoose;
                    cardtochoose = i;

                }


            }

            int random = rand.nextInt(100) + 1;
            if (random > 51) {
               playAI(cardtochoose);
            } else
            playAI(secondbestcard);




        } else if (tempdiff == DifficultyLevels.HARD) {
            /**
             * For Hard, play the card with the highest combined attack and health value.
             */


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
           playAI(cardtochoose);
        }
    }

    public  void takeFirstTurn(){

    }


    public void AIattackPhase(int selectedAttackCard, int cardToAttack) {

        if(!containers.get(selectedAttackCard).isEmpty() &&
                !enemyContainers.get(cardToAttack).isEmpty()) {
            Card attackCard = containers.get(selectedAttackCard).returnCardHeld();





            float heroCardX = enemyContainers.get(cardToAttack).getX();
            float heroCardY = enemyContainers.get(cardToAttack).getY();
            attackCard.setPosition(enemyContainers.get(cardToAttack).position.x,enemyContainers.
                    get(cardToAttack).position.y);

            pause();

            if(attackCard.getBound().intersects(enemyContainers.get(cardToAttack).getBound())) {
                gameBoard.playAttackAnimation(enemyContainers.get(cardToAttack));
                enemyContainers.get(cardToAttack).returnCardHeld().setHealthValue(enemyContainers.get(cardToAttack).returnCardHeld().getHealthValue() - containers.get(selectedAttackCard).returnCardHeld().getAttackValue());
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
            for (int i = 0; i <= enemyContainers.size() - 1; i++) {
                if (!enemyContainers.get(i).isEmpty() && (enemyContainers.get(i).returnCardHeld().getHealthValue() >0)) {
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
            for (int i = 0; i <= containers.size() - 1; i++) {
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

            for (int i = 0; i <= enemyContainers.size()-1;i++) {
                if (!enemyContainers.get(i).isEmpty() && (enemyContainers.get(i).returnCardHeld().getHealthValue()> 0)) {
                    int total = 0;
                    total += enemyContainers.get(i).returnCardHeld().getHealthValue();
                    total += enemyContainers.get(i).returnCardHeld().getAttackValue();
                    if (total > temp) {
                        temp = total;
                        cardtochoose = i;
                    }
                }

            }

            int random = rand.nextInt(100) + 1;
            if (random > 51) {
                int n = rand.nextInt(containers.size()-1);
                AIattackPhase(n,cardtochoose);

            }

            else {
                int n = rand.nextInt(containers.size()-1);
                int x = rand.nextInt(enemyContainers.size()-1);
                if(enemyContainers.get(x).isEmpty()) {
                    x = rand.nextInt(enemyContainers.size()-1);
                }
                AIattackPhase(n,x);

            }




        }



        else if(tempdiff == DifficultyLevels.HARD) {

            containers.addAll(gameBoard.getVillianContainers());
            enemyContainers.addAll(gameBoard.getHeroContainers());
            int latestcardsamerange;
            boolean cardwithinrange = false;
            int temp = 0;
            int temp1 = 100;
            int cardtochoose = 0;
            int cardtochoose1 = 0;
            //Choose card to attack with
            for (int i = 0; i <= containers.size()-1;i++) {
                if (!containers.get(i).isEmpty()) {
                    int total = 0;
                    total += containers.get(i).returnCardHeld().getAttackValue();
                    if (total > temp) {
                        temp = total;
                        cardtochoose = i;
                    }
                }

            }

            for (int i = 0; i <= enemyContainers.size() - 1; i++) {
                if (!enemyContainers.get(i).isEmpty() && enemyContainers.get(i).returnCardHeld().getHealthValue() > 0) {
                    int total1 = 0;
                    total1 += enemyContainers.get(i).returnCardHeld().getHealthValue();
                    if (total1 < temp1) {
                        temp1 = total1;
                        cardtochoose1 = i;
                    }
                }

            }


            AIattackPhase(cardtochoose,cardtochoose1);
        }
    }

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
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











