package ch.epfl.sweng.androfoot.screens;

import java.io.IOException;

import ch.epfl.sweng.androfoot.board.Board;
import ch.epfl.sweng.androfoot.board.BoardFactory;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.gui.GuiCommand;
import ch.epfl.sweng.androfoot.gui.GuiManager;
import ch.epfl.sweng.androfoot.interfaces.HostObserver;
import ch.epfl.sweng.androfoot.kryonetnetworking.GameInfo;
import ch.epfl.sweng.androfoot.kryonetnetworking.HostServer;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerHost;
import ch.epfl.sweng.androfoot.kryonetnetworking.ShakeData;
import ch.epfl.sweng.androfoot.players.PlayerType;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

public class NetworkHostScreen implements Screen, HostObserver {

	public static PlayerHost ph;

	public NetworkHostScreen() {
	}

	public static PlayerHost getPlayerHost() {
		return ph;
	}

	public void startBoard() {
		PhysicsWorld.getPhysicsWorld().startWorld();
	}

	@Override
	public void gameHostStart() {
		startBoard();
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.BACK)
				|| Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {

			resetBoard();

			GuiManager.getInstance().executeCommand(GuiCommand.goToMainMenu);
		}
		PhysicsWorld.getPhysicsWorld().phyStep(delta);
		GraphicEngine.getEngine().render(delta);
	}

	private void resetBoard() {
		// reset board only if game wasn't reset already
		Board.getInstance().resetBoard();
		PhysicsWorld.getPhysicsWorld().setHostMode(false);
		ph.closeServers();
	}

	@Override
	public void resize(int width, int height) {
		GraphicEngine.getEngine().setScreenSize(width, height);
		PlayerTouchTracker.getInstance().setNewScreenWidth(width, height);
	}

	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true);
		GraphicEngine.getEngine().bindToWorld(PhysicsWorld.getPhysicsWorld());

		ph = HostServer.getHostServer();
		Gdx.input.setInputProcessor(PlayerTouchTracker.getInstance());

		if (!ph.gameStarted) {
			ph.addHostObserver(this);
			try {
				ph.listenToClient();
			} catch (IOException e) {
				// TODO Handle failed server initialization
			}

			ph.addHostObserver(PhysicsWorld.getPhysicsWorld());

			BoardFactory.setupNetworkBoard(PlayerType.LOCAL_PLAYER,
					PlayerType.REMOTE_PLAYER, Configuration.getInstance()
							.getScoreLimit());

			PhysicsWorld.getPhysicsWorld().pauseWorld();
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClientData(InputData data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClientShakeData(ShakeData data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClientGameInfoData(GameInfo data) {
		// TODO Auto-generated method stub

	}

}