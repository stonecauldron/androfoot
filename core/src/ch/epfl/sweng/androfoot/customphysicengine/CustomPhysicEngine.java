package ch.epfl.sweng.androfoot.customphysicengine;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitable;

/**
 * Default implementation of the custom physic engine
 * @author Gilthoniel (Gaylor Bosson)
 */
public final class CustomPhysicEngine extends Thread implements PhysicEngine {

    private static final CustomPhysicEngine PHYSIC_ENGINE = new CustomPhysicEngine();
    private Set<Body> bodies;
    
    private CustomPhysicEngine() {
        bodies = new LinkedHashSet<Body>();
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
    public void addBody(Body body) {
        bodies.add(body);
    }

    @Override
    public void run() {
        while (true) {
            long time = System.currentTimeMillis();
            
            // Calcul the new positions
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
        for (Body body : bodies) {
            body.move(body.getVelocityX(), body.getVelocityY());
            
            Set<Body> othersBody = bodies;
            assert othersBody.remove(body);
            
            OverlapVisitor overlaps = new OverlapVisitor();
            UpdateVelocityVisitor velocityAdaptator = new UpdateVelocityVisitor();
            for (Body other : othersBody) {
                List<ShapeBodyVisitable> overlapBodies = new ArrayList<ShapeBodyVisitable>();
                bodies.add(body);
                bodies.add(other);
                
                overlaps.visit(overlapBodies);
                if (overlaps.isOverlapsed()) {
                    velocityAdaptator.visit(overlapBodies);
                    
                    body.setVelocityX(velocityAdaptator.getNewVelocity()[0]);
                    body.setVelocityY(velocityAdaptator.getNewVelocity()[1]);
                }
            }
        }
    }
}
