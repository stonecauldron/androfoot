package ch.epfl.sweng.androfoot.kryonetnetworking;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class NetworkUtils {
	static public final int TCP_PORT = 40000;
	static public final int UDP_PORT = 40005;
	
	// This registers objects that are going to be sent over the network.
    static public void register (EndPoint endPoint) {
            Kryo kryo = endPoint.getKryo();
            kryo.register(HostData.class);
            kryo.register(InputData.class);
            kryo.register(ShakeData.class);
            kryo.register(ArrayList.class);
            kryo.register(Vector2.class);
    }
    
}