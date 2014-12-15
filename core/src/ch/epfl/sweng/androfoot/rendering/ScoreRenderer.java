package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.gamelogic.PlayerCharacteristicsManager;
import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.AngleType;
import ch.epfl.sweng.androfoot.polygongenerator.AppendPointPolygonGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.CircleGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.ImmutablePoint;
import ch.epfl.sweng.androfoot.polygongenerator.NonCyclicPolygon;
import ch.epfl.sweng.androfoot.polygongenerator.PolygonReflector;
import ch.epfl.sweng.androfoot.polygongenerator.PolygonRotator;
import ch.epfl.sweng.androfoot.polygongenerator.PolygonUtils;
import ch.epfl.sweng.androfoot.polygongenerator.PrependPointPolygonGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.PolygonReflector.Axis2D;
import ch.epfl.sweng.androfoot.polygongenerator.RawPolygonGenerator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Display the score of the game <b> the code does not take the size of the
 * world in parameter !! be carefull </b>
 * 
 * @author Guillame Leclerc
 *
 */
public class ScoreRenderer implements DrawableRenderer {

	private static final float SCORE_OPACITY = 0.1f;

	private static final int NB_SEGMENT_SCORE = 15;

	private PolygonRenderer scoreADisplayer;
	private PolygonRenderer scoreBDisplayer;

	/**
	 * Set the score
	 * 
	 * @param player1
	 *            the score of the player 1
	 * @param player2
	 *            the score of the player 2
	 */
	public void setScore(int player1, int player2) {
		scoreADisplayer = generateRendererForScore(player1, true);
		scoreBDisplayer = generateRendererForScore(player2, false);
	}

	private static PolygonRenderer generateRendererForScore(int score,
					boolean team1) {
		int maxScore = Configuration.getInstance().getScoreLimit();
		float progression = score / (float) maxScore;
		PolygonGenerator generator;
		if (score != 0) {
			generator = new CircleGenerator(NB_SEGMENT_SCORE, 0,
							PolygonUtils.MAX_ANGLE_RADIAN / 2 * progression,
							AngleType.RADIAN);
		} else {
			generator = new RawPolygonGenerator(new float[0]);
		}
		Color c;
		if (team1) {
			c = new Color(PlayerCharacteristicsManager.getColorTeam1());
			generator = new PolygonReflector(generator, Axis2D.y);
		} else {
			c = new Color(PlayerCharacteristicsManager.getColorTeam2());
		}
		generator = new PolygonRotator(generator,
						-PolygonUtils.MAX_ANGLE_RADIAN / 2 / 2);
		generator = new NonCyclicPolygon(generator);
		generator = new PrependPointPolygonGenerator(generator,
						new ImmutablePoint<Float>(0f, 0f));
		generator = new AppendPointPolygonGenerator(generator,
						new ImmutablePoint<Float>(0f, 0f));
		PolygonRenderer renderer = new PolygonRenderer(generator);
		renderer.setPosition(Constants.WORLD_ORIGIN_X + Constants.WORLD_SIZE_X
						/ 2, Constants.WORLD_ORIGIN_Y + Constants.WORLD_SIZE_Y
						/ 2);
		c.a = SCORE_OPACITY;
		renderer.setColor(c);
		return renderer;
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		batch.begin();
		GraphicEngine.getEngine().enableBlending();
		if (scoreADisplayer != null) {
			scoreADisplayer.render(batch, shapes);
		}
		if (scoreADisplayer != null) {
			scoreBDisplayer.render(batch, shapes);
		}
		batch.end();
	}

}
