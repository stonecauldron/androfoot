package ch.epfl.sweng.androfoot.players;

import ch.epfl.sweng.androfoot.interfaces.Controllable;

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
