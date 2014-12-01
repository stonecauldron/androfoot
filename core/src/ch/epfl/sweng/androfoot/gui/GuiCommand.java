/**
 * 
 */
package ch.epfl.sweng.androfoot.gui;

/**
 * @author Sidney Barthe
 * These commands are supposed to be passed to the GuiManager using the method
 * executeCommand and will be interpreted.
 */
public enum GuiCommand {
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
	startAI,
	startHuman,
	updateSensibility,
	nothing
};
