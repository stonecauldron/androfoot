package ch.epfl.sweng.androfoot.players;

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
			
		case LOCAL_PLAYER:
			player = new LocalPlayer(playerNumber);
			break;
			
		case REMOTE_PLAYER:
			player = new RemotePlayer(playerNumber);
			break;
			
		default:
			throw new IllegalArgumentException();
		}
		
		if (playerNumber == playerNumber.ONE) {
			// get next player number
			playerNumber = playerNumber.TWO;
		}
		return player;
	}
}
