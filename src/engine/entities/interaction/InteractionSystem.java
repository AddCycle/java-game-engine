package engine.entities.interaction;

import java.awt.Rectangle;
import java.util.List;

import engine.entities.Entity;

/**
 * This class is handling the entities interactions (tmp I think)
 */
public class InteractionSystem {

	public static void tryInteract(
            Entity interactor,
            Rectangle.Float box,
            List<Entity> entities) {

        for (Entity e : entities) {
            if (e instanceof Interactible i && e != interactor) {
                if (box.intersects(e.getHitbox())) {
                    i.interact(interactor);
                    return;
                }
            }
        }
    }
}
