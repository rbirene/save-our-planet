package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameScreen;


public class BattleScreen extends GameScreen {

    private GameBoard board;
    private ArrayList<Card> cards = new ArrayList<>();
    public BattleScreen(Game game) {
        super("Battle", game);
        mGame.getAssetManager().loadAssets("txt/assets/CardDemoScreenAssets.JSON");
        board = new GameBoard(game.getScreenWidth()/2,game.getScreenHeight()/2,
                1700.0f,1000.0f,game.getAssetManager().getBitmap("tempBack"),this);

        Card sam = new Card(25,25,this,"Sam", "Hero",
                game.getAssetManager().getBitmap("CardBackground"), 9,9);
        Card sam2 = new Card(50,50,this,"Sam2", "Hero2",
                game.getAssetManager().getBitmap("CardBackground"), 4,9);
        cards.add(sam);
        cards.add(sam2);
    }


    @Override
    public void update(ElapsedTime elapsedTime) {

        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        for(Card c:cards) {
            c.moveCardIfTouched(touchEvents, cards);
        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        board.draw(elapsedTime, graphics2D);
    }
}
