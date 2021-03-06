package ch.epfl.sweng.androfoot.players;

import ch.epfl.sweng.androfoot.players.ai.EasyAI;
import ch.epfl.sweng.androfoot.players.ai.HardAI;
import ch.epfl.sweng.androfoot.players.ai.MediumAI;
import ch.epfl.sweng.androfoot.players.ai.RandomAI;

/**
 * The factory for the players.
 * Uses the enum PlayerType to decide which type of player to instantiate.
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class PlayerFactory {
	
	// enum create the correct player each time.
	private static PlayerNumber playerNumber = PlayerNumber.ONE;

	public static AbstractPlayer createPlayer(PlayerType playerType) {
		
		AbstractPlayer player;
		
		switch(playerType) {
		
			case RANDOM_AI_PLAYER:
				player = new RandomAI(playerNumber);
				break;
				
			case EASY_AI_PLAYER:
				player = new EasyAI(playerNumber);
				break;
				
			case MEDIUM_AI_PLAYER:
				player = new MediumAI(playerNumber);
				break;
				
			case HARD_AI_PLAYER:
				player = new HardAI(playerNumber);
				break;
			
			case LOCAL_PLAYER:
				player = new LocalPlayer(playerNumber);
				break;
			
			case REMOTE_PLAYER:
				player = new RemotePlayer(playerNumber);
				break;
			
			default:
				throw new IllegalArgumentException();
		}
		
		if (playerNumber == PlayerNumber.ONE) {
			// get next player number
			playerNumber = PlayerNumber.TWO;
		}
		return player;
	}
	
	public static void resetPlayerNumber() {
		playerNumber = PlayerNumber.ONE;
	}
}
