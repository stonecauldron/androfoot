/**
 * 
 */
package ch.epfl.sweng.androfoot.gui;

import ch.epfl.sweng.androfoot.configuration.Configuration;

/**
 * @author Sidney Barthe
 * These commands are supposed to be passed to the GuiManager using the method
 * executeCommand and will be interpreted.
 */
public enum GuiCommand {
	addPlayerOneFormation,
	subPlayerOneFormation,
	addPlayerTwoFormation,
	subPlayerTwoFormation,
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
	refreshDisplay,
	subScoreLimit,
	togglePlayerOneType,
	togglePlayerTwoType,
	toggleSound,
	toggleTilting,
	togglePowerups,
	updateSensitivity,
	nothing
};
