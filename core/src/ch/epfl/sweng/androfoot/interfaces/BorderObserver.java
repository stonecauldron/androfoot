package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;

public interface BorderObserver {
    public void borderContact(BorderType type);
}
