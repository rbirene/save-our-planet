package uk.ac.qub.eeecs.game.cardDemo.Sprites;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.animation.AlphaAnimation;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationManager;
import uk.ac.qub.eeecs.gage.engine.graphics.CanvasGraphics2D;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.miscDemos.AnimationDemoZombie;

public class AttackAnimation extends Sprite {

    private AnimationManager mAnimationManager;
    private int timer = 0;

    public AttackAnimation(float startX, float startY, float width, float height, GameScreen gameScreen) {
        super(startX, startY, width, height,null, gameScreen);

        mAnimationManager = new AnimationManager(this);
        mAnimationManager.addAnimation("txt/animation/attackAnimation.JSON");
        mAnimationManager.setCurrentAnimation("explosion");

    }


    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {
       if(timer!=10){
           mAnimationManager.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
       }
    }

    @Override
    public void update(ElapsedTime elapsedTime) {

        if(timer!=10) {
            mAnimationManager.play(elapsedTime);
            mAnimationManager.update(elapsedTime);
            timer++;
        }else{
            mAnimationManager.stop();
        }
    }
}

