package uk.ac.qub.eeecs.game.cardDemo.Screens;

import android.graphics.Color;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Sprites.Player;

public class EndGame extends GameScreen {

    private Player winner;

    public EndGame(Game game, Player winner) {
        super("EndGame", game);

      this.winner = winner;

    }



    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);
    }

    @Override
    public void update(ElapsedTime elapsedTime) {

    }
}
