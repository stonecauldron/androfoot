package ch.epfl.sweng.androfoot.kryonetnetworking.test;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerClient;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerHost;

public class NetworkTest {

	private static PlayerHost ph;
	private static PlayerClient client;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		ph = new PlayerHost();
		ph.listenToClient();
		client = new PlayerClient();
		client.listenToServer();
		//Not yet usefull
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
