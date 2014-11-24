package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 *Display the score of the game
 * <b> the code does not take the size of the world in parameter !! be carefull </b>
 * @author Guillame Leclerc
 *
 */
public class ScoreRenderer implements DrawableRenderer {
	
	private int scoreA = 0;
	private int scoreB = 0;
	
	private final BitmapFont font;
	
	/**
	 * Init the {@link ScoreRenderer} with a color
	 * @param textColor the color to draw the score with
	 */
	public ScoreRenderer(Color textColor) {
		font = new BitmapFont();
		font.setColor(textColor);
		//font.setFixedWidthGlyphs("1234567890");
		font.setScale(0.1f, 0.2f);
	}
	
	/**
	 * Set the score
	 * @param player1 the score of the player 1
	 * @param player2 the score of the player 2
	 */
	public void setScore(int player1, int player2) {
		scoreA = player1;
		scoreB = player2;
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		batch.begin();
		batch.enableBlending();
		font.draw(batch, String.valueOf(scoreA), 2, 4.5f);	
		font.draw(batch, String.valueOf(scoreB), 7, 4.5f);	
		batch.end();
	}

}
