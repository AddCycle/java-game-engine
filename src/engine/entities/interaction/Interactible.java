package engine.entities.interaction;

import engine.entities.Entity;

public interface Interactible {
	/**
	 * @param interactor : the initiator of the interaction
	 */
	InteractionResult interact(Entity interactor);
}
