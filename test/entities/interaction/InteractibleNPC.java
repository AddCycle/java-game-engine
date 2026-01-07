package entities.interaction;

import engine.core.Logger;
import engine.entities.Entity;
import engine.entities.interaction.Interactible;
import engine.entities.interaction.InteractionResult;
import engine.entities.interaction.InteractionType;
import engine.graphics.Renderer;
import engine.world.Camera;

public class InteractibleNPC extends Entity implements Interactible {

	public InteractibleNPC(float x, float y, float width, float height, int texture) {
		super(x, y, width, height, texture);
	}

	@Override
	public InteractionResult interact(Entity interactor) {
		Logger.debug("Interacted with npc");
		return new InteractionResult(
		        InteractionType.DIALOG,
		        "Hello my friend, how are you?"
		    );
	}

	@Override
	public void update(float dt) {}

	@Override
	public void render(Renderer renderer, Camera camera) {
		Renderer.drawRect(x - camera.x, y - camera.y, width, height, 0f, 0f, 1f);
	}
}