package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;



public class CardHolder extends GameObject {

    private Card card;
    private float x,y;
    private boolean empty;

    public CardHolder(float x, float y,GameScreen gameScreen) {
        super(x, y, 40.0f, 60.0f,
                gameScreen.getGame().getAssetManager().getBitmap("deckContainer"), gameScreen);

        this.empty = true;
    }

    public boolean isEmpty(){
        return empty;
    }


     public void AddCardToHolder(Card card){
        this.card = card;
        this.empty = false;
         card.setPosition(this.getBound().x, this.getBound().y);
     }

     public void removeCard(){
        this.card = null;
        this.empty = true;
     }

    public void setX(float x){ this.x = x;}
    public void setY(float x){ this.y = x;}

    public float getY(){ return y;}
    public float getX(){ return x;}



}
