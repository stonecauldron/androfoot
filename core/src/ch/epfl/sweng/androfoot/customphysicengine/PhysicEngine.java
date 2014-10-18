package ch.epfl.sweng.androfoot.customphysicengine;

import com.badlogic.gdx.math.Polygon;

/**
 * Interface of the physic engine
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public interface PhysicEngine {
    
    int CALCUL_TIME = 20;
    
    /**
     * Get the instance of the physic engine, because we need only one
     * @return the instance
     */
    PhysicEngine getInstance();
    
    /**
     * Add a new static object in the world
     * @param polygon
     */
    void addStaticBody(Polygon polygon);
    
    /**
     * Add a new dynamic object in the world
     * @param polygon
     */
    void addDynamicBody(Polygon polygon);
}
