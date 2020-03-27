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

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.cardDemo.User.User;
import uk.ac.qub.eeecs.game.cardDemo.User.UserStore;


public class EndGame extends GameScreen {

    private PushButton menuButton;

    private GameObject background, result;

    private ScreenViewport ScreenViewport;

    private LayerViewport LayerViewport;

    private int gameHeight, gameWidth;

    private Boolean winner;

    private UserStore userStore;

    private User currentUser;


    public EndGame(Game game, boolean winner) {

        super("EndGame", game);

        gameHeight = mGame.getScreenHeight();
        gameWidth = mGame.getScreenWidth();
        ScreenViewport = new ScreenViewport(0, 0, gameWidth, gameHeight);
        LayerViewport = mDefaultLayerViewport;

        this.winner = winner;

        //set screen properties [Niamh McCartney]
        userStore = game.getUserStore();
        currentUser = game.getCurrentUser();

        loadScreenAssets();

        String resultString;
        if (winner)
            resultString = "win";
        else
            resultString = "lose";


        menuButton = new PushButton(420.0f, 30.0f, 80.0f, 28.0f, "menuBtn", "menuBtn", this);
        background = new GameObject(mDefaultLayerViewport.halfWidth, mDefaultLayerViewport.halfHeight,
                mDefaultLayerViewport.getWidth(), mDefaultLayerViewport.getHeight(), game.getAssetManager().getBitmap("Background"), this);
        result = new GameObject(mDefaultLayerViewport.halfWidth, mDefaultLayerViewport.halfHeight,
                mDefaultLayerViewport.getHeight(), mDefaultLayerViewport.getHeight(), game.getAssetManager().getBitmap(resultString), this);


        updateUserStore();
    }

    @Override

    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        background.draw(elapsedTime, graphics2D, LayerViewport, ScreenViewport);
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

    private void updateUserStore(){
        int userPos;
        userPos = userStore.checkUserStore(currentUser.getName());
        if(winner){
            userStore.getUserList().get(userPos).addWin();
            userStore.saveUsers();
        }else{
            userStore.getUserList().get(userPos).addLoss();
            userStore.saveUsers();
        }
    }

}
