//package uk.ac.qub.eeecs.game.cardDemo.Screens;
//
//import android.graphics.Color;
//
//import uk.ac.qub.eeecs.gage.Game;
//import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
//import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
//import uk.ac.qub.eeecs.gage.world.GameScreen;
//import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player.Player;
//
//public class EndGame extends GameScreen {
//
//    private Player winner;
//
//    public EndGame(Game game, Player winner) {
//        super("EndGame", game);
//
//      this.winner = winner;
//
//    }
//
//
//
//    @Override
//    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
//        graphics2D.clear(Color.WHITE);
//    }
//
//    @Override
//    public void update(ElapsedTime elapsedTime) {
//
//    }
//}
package uk.ac.qub.eeecs.game.cardDemo.Screens;


import android.graphics.Color;


import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;


public class EndGame extends GameScreen {

    private PushButton menuButton;

    private GameObject background, result;

    private ScreenViewport ScreenViewport;

    private LayerViewport LayerViewport;

    private int gameHeight, gameWidth;


    public EndGame(Game game, boolean winner) {

        super("EndGame", game);

        gameHeight = mGame.getScreenHeight();
        gameWidth = mGame.getScreenWidth();
        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        loadScreenAssets();

        String resultString;
        if (winner)
            resultString = "win";
        else
            resultString = "lose";


        menuButton = new PushButton(420.0f, 30.0f, 80.0f, 28.0f, "menuBtn", "menuBtn", this);
        //background = new GameObject(240.0f, 160.0f, 490.0f, 325.0f, game.getAssetManager().getBitmap("menuBackground"), this);
        result = new GameObject(mDefaultLayerViewport.halfWidth, mDefaultLayerViewport.halfHeight,
                mDefaultLayerViewport.getHeight(), mDefaultLayerViewport.getHeight(), game.getAssetManager().getBitmap(resultString), this);

    }


    @Override

    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);
        //background.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        result.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
        menuButton.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);

    }


    @Override

    public void update(ElapsedTime elapsedTime) {
        menuButton.update(elapsedTime);

        if (menuButton.isPushTriggered()) { mGame.getScreenManager().addScreen(new MenuScreen(mGame));
        }
    }

    private void loadScreenAssets(){
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAssets("txt/assets/EndGameScreenAssets.JSON");
    }

}
