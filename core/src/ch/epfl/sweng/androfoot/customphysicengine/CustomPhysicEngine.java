package ch.epfl.sweng.androfoot.customphysicengine;

import java.util.LinkedHashSet;
import java.util.Set;

import ch.epfl.sweng.androfoot.customphysicengine.DynamicBody.Velocity;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

/**
 * Default implementation of the custom physic engine
 * @author Gilthoniel (Gaylor Bosson)
 */
public final class CustomPhysicEngine extends Thread implements PhysicEngine {

    private static final CustomPhysicEngine PHYSIC_ENGINE = new CustomPhysicEngine();
    private Set<StaticBody> staticBodies;
    private Set<DynamicBody> dynamicBodies;
    
    private CustomPhysicEngine() {
        staticBodies = new LinkedHashSet<StaticBody>();
        dynamicBodies = new LinkedHashSet<DynamicBody>();
    }

    /**
     * Singleton design pattern
     * @see PhysicEngine.getInstance
     */
    public CustomPhysicEngine getInstance() {
        return PHYSIC_ENGINE;
    }
    
    /**
     * @see PhysicEngine.addStaticBody
     */
    public void addStaticBody(Polygon polygon) {
        staticBodies.add(new StaticBody(polygon));
    }
    
    /**
     * @see PhysicEngine.addDynamicBody
     */
    public void addDynamicBody(Polygon polygon) {
        dynamicBodies.add(new DynamicBody(polygon));
    }

    @Override
    public void run() {
        while (true) {
            long time = System.currentTimeMillis();
            calculPositions();

            try {
                long endTime = System.currentTimeMillis();
                if (endTime - time < CALCUL_TIME) {
                    sleep(CALCUL_TIME - (endTime - time));
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Calcul the new positions of all the objects for a step
     */
    private void calculPositions() {
        for (DynamicBody body : dynamicBodies) {
            Velocity bodyVelocity = body.getVelocity();
            body.move(bodyVelocity.getVelocityX(), bodyVelocity.getVelocityY());
            
            for (StaticBody staticBody : staticBodies) {
                if (Intersector.overlapConvexPolygons(body.getPolygon(), staticBody.getPolygon())) {
                    
                }
            }
        }
    }
}
