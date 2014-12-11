package ch.epfl.sweng.androfoot.screens;

import java.io.IOException;

import ch.epfl.sweng.androfoot.board.Board;
import ch.epfl.sweng.androfoot.board.BoardFactory;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.gui.GuiCommand;
import ch.epfl.sweng.androfoot.gui.GuiManager;
import ch.epfl.sweng.androfoot.interfaces.ClientObserver;
import ch.epfl.sweng.androfoot.kryonetnetworking.GameInfo;
import ch.epfl.sweng.androfoot.kryonetnetworking.HostData;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.NoHostFoundException;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerClient;
import ch.epfl.sweng.androfoot.players.PlayerType;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

public class NetworkClientScreen implements Screen, ClientObserver {

	public static PlayerClient pc;
	private boolean gameStarted = false;

	public NetworkClientScreen() {
		pc = new PlayerClient();
	}

	public static PlayerClient getPlayerClient() {
		return pc;
	}

	public void startBoard() {
		gameStarted = true;
		PhysicsWorld.getPhysicsWorld().startWorld();
	}

	@Override
	public void gameClientStart() {
		startBoard();
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.BACK)
				|| Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
			PhysicsWorld.getPhysicsWorld().setSlaveMode(false);
			
			if (gameStarted) {
				pc.loseClient();
				Board.getInstance().resetBoard();
				gameStarted = false;
			}
			
			GuiManager.getInstance().executeCommand(GuiCommand.goToMainMenu);
		}

		PhysicsWorld.getPhysicsWorld().phyStep(delta);
		GraphicEngine.getEngine().render(delta);
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

		if (!gameStarted) {
			try {
				pc.addClientObserver(this);
				BoardFactory.setupNetworkBoard(PlayerType.REMOTE_PLAYER,
						PlayerType.LOCAL_PLAYER, Configuration.getInstance()
								.getScoreLimit());
				pc.addClientObserver(PhysicsWorld.getPhysicsWorld());
				PhysicsWorld.getPhysicsWorld().pauseWorld();
				pc.listenToServer();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoHostFoundException e) {
				Board.getInstance().resetBoard();
				GuiManager.getInstance()
						.executeCommand(GuiCommand.goToMainMenu);
			}
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
	public void updateHostData(HostData data) {
	}

	@Override
	public void updateHostTouchData(InputData data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateHostGameInfoData(GameInfo data) {
		// TODO Auto-generated method stub

	}
}
