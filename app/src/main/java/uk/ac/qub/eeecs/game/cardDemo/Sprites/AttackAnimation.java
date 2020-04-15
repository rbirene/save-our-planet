package uk.ac.qub.eeecs.game.cardDemo.Sprites;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
//Sam Harper
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