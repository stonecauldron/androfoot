package ch.epfl.sweng.androfoot.interfaces;

import com.badlogic.gdx.math.Vector2;

import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;

public interface BorderInterface {
    BorderType getType();
    
    Vector2 getPosition();
    
    BorderInterface clone();
}
