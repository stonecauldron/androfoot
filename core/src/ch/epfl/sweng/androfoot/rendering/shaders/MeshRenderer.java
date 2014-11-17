package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector2;

public interface MeshRenderer {
	
	public void setPosition(Vector2 pos);
	public void setRotation(float angle);
	public void setZIndex(int ZIndex);
	public void setMesh(Mesh mesh);
	public void render();

}
