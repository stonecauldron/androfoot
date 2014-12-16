package ch.epfl.sweng.androfoot.gui;

/**
 * These commands are supposed to be passed to the GuiManager using the method
 * executeCommand and will be interpreted.
 * 
 * @author Sidney Barthe
 * 
 */
public enum GuiCommand {
	addPlayerOneFormation,
	subPlayerOneFormation,
	addPlayerTwoFormation,
	subPlayerTwoFormation,
	addPlayerOneTeam,
	subPlayerOneTeam,
	addPlayerTwoTeam,
	subPlayerTwoTeam,
	goToMainMenu,
	goToLocalPlay,
	goToSettings,
	goToCredits,
	goToGame,
	goToGameOver,
	goToNetworkPlay,
	goToClientNetwork,
	goToHostNetwork,
	exit,
	addScoreLimit,
	subScoreLimit,
	addPlayerOneType,
	subPlayerOneType,
	addPlayerTwoType,
	subPlayerTwoType,
	toggleSound,
	toggleTilting,
	togglePowerups,
	updateSensitivity,
	nothing
};
