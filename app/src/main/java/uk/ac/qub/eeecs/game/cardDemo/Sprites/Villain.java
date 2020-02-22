package uk.ac.qub.eeecs.game.cardDemo.Sprites;
import android.graphics.Bitmap;

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

    /**
     *
     * Create a new villain.
     *
     */

    public Villain(Bitmap portrait){
        super(0.0f, 0.0f, "Ronald Rump", null, portrait);
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

