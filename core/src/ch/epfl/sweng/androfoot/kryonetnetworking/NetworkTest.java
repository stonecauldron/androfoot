package ch.epfl.sweng.androfoot.kryonetnetworking;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

public class NetworkTest {

	private static PlayerHost ph;
	private static PlayerClient client;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		ph = new PlayerHost();
		ph.listenToClient();

		client = new PlayerClient();
		client.listenToServer();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
