package engine.entities.interaction;

import java.awt.Rectangle;
import java.util.List;

import engine.entities.Entity;

/**
 * This class is helping the detection of the interaction between the entities
 */
public class InteractionSystem {

	public static InteractionResult tryInteract(
            Entity interactor,
            Rectangle.Float box,
            List<Entity> entities) {

        for (Entity e : entities) {
            if (e instanceof Interactible i && e != interactor) {
                if (box.intersects(e.getHitbox())) {
                		InteractionResult result = i.interact(interactor);
                		return result;
                }
            }
        }

        return null;
    }
}
