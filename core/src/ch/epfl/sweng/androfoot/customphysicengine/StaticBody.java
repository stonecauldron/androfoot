package ch.epfl.sweng.androfoot.customphysicengine;

import com.badlogic.gdx.math.Polygon;

/**
 * Those objects are static in the world, so they can't move because of collisions or anything else
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class StaticBody extends AbstractBody {
    
    public StaticBody(Polygon polygon) {
        super(polygon);
    }
}
