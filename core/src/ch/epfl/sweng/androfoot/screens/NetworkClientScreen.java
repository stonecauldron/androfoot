package ch.epfl.sweng.androfoot.screens;

import java.io.IOException;

import ch.epfl.sweng.androfoot.board.BoardFactory;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.interfaces.ClientObserver;
import ch.epfl.sweng.androfoot.kryonetnetworking.HostData;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerClient;
import ch.epfl.sweng.androfoot.players.PlayerType;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class NetworkClientScreen implements Screen, ClientObserver {

	public static PlayerClient pc;
	
	public NetworkClientScreen() {
		pc  = new PlayerClient();
		try {
			pc.addClientObserver(this);
			BoardFactory.setupBoard(PlayerType.REMOTE_PLAYER,
					PlayerType.LOCAL_PLAYER, Configuration.getInstance()
							.getScoreLimit());
			pc.addClientObserver(PhysicsWorld.getPhysicsWorld());

			pc.listenToServer();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PlayerClient getPlayerClient() {
		return pc;
	}

	public void startBoard() {
		//TODO START MOVING THE BALL
	}

	@Override
	public void gameClientStart() {
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
	public void updateHostData(HostData data) {
	}

	@Override
	public void updateHostTouchData(InputData data) {
		// TODO Auto-generated method stub
		
	}
}
