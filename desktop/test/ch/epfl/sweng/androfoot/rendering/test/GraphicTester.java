package ch.epfl.sweng.androfoot.rendering.test;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;

public class GraphicTester {
	
	private int testCount = 0;
	
	private Queue<TestRequest> requests = new ConcurrentLinkedQueue<GraphicTester.TestRequest>();
	
	public GraphicTester() {
		LwjglApplicationConfiguration.disableAudio=true;
		new LwjglApplication(new ApplicationListener() {
			
			@Override
			public void resume() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void resize(int width, int height) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void render() {
				if (! requests.isEmpty()) {
					TestRequest request = requests.poll();
					request.getCode().run();
					Pixmap screenshot = ScreenShotTaker.getScreenshot(0, 0, 
							Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
					request.setResult(screenshot);
					//FileHandle handle = Gdx.files.absolute("C:\\Users\\Guillame Leclerc\\workspacegdx\\androfoot\\" + (testCount++) + "test.png");
					//ScreenShotTaker.saveScreenshot(handle);
				}
			}
			
			@Override
			public void pause() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void create() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void scheduleTest(TestRequest test){
		requests.add(test);
	}
	
	public void dispose() {
		Gdx.app.exit();
	}
	
	public static class TestRequest{
		private final Runnable code;
		private final Object lock = new Object();
		private Pixmap result = null;

		public TestRequest(Runnable codeArg) {
			code = codeArg;
		}
		
		public void setResult(Pixmap res) {
			result = res;
			synchronized (lock) {
				lock.notify();
			}
		}
		
		public Runnable getCode() {
			return code;
		}
		
		public Pixmap getResult() {
			if(result == null) {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						return null;
					}
				}
			}
			return result;
		}
		
		
	}
	
	
}
