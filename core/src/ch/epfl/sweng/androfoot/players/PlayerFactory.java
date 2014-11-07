package ch.epfl.sweng.androfoot.players;

import ch.epfl.sweng.androfoot.interfaces.Controllable;

/**
 * The factory for the players.
 * Uses the enum PlayerType to decide which type of player to instantiate.
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class PlayerFactory {

	public static Controllable createPlayer(PlayerType playerType) {
		switch(playerType) {
		
		case AI_PLAYER:
			return new AIPlayer();
			
		case LOCAL_PLAYER:
			return new LocalPlayer();
			
		case REMOTE_PLAYER:
			return new RemotePlayer();
			
		default:
			throw new IllegalArgumentException();
		}
	}
}
