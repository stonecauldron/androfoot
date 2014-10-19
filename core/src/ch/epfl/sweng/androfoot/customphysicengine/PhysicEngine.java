package ch.epfl.sweng.androfoot.customphysicengine;

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
     * Add a new body in the world
     * @param polygon
     */
    void addBody(Body body);
}
