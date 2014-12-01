package ch.epfl.sweng.androfoot.screens;

import java.io.IOException;

import ch.epfl.sweng.androfoot.board.BoardFactory;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.HostObserver;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerHost;
import ch.epfl.sweng.androfoot.players.PlayerType;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class NetworkHostScreen implements Screen, HostObserver {

	public static PlayerHost ph;

	public NetworkHostScreen() {
		ph = new PlayerHost();
		try {
			
			ph.addHostObserver(this);
			ph.addHostObserver(PhysicsWorld.getPhysicsWorld());
			BoardFactory.setupBoard(PlayerType.LOCAL_PLAYER,
					PlayerType.REMOTE_PLAYER);
			
			ph.listenToClient();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PlayerHost getPlayerHost() {
		return ph;
	}

	public void startBoard() {
		// TODO START MOVING THE BALL
	}

	@Override
	public void gameHostStart() {
		startBoard();
	}

	@Override
	public void render(float delta) {
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

}
