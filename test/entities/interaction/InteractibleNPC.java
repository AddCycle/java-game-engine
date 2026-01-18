package entities.interaction;

import java.awt.Color;

import engine.core.Logger;
import engine.entities.Entity;
import engine.entities.body.BodyType;
import engine.entities.interaction.Interactible;
import engine.entities.interaction.InteractionResult;
import engine.entities.interaction.InteractionType;
import engine.graphics.Renderer;
import engine.world.Camera;

public class InteractibleNPC extends Entity implements Interactible {
	private Color color;
	public String dialog;

	public InteractibleNPC(float x, float y, float width, float height, Color color) {
		super(x, y, width, height, -1);
		this.solid = true;
		this.bodyType = BodyType.STATIC;
		this.color = color;
	}

	public InteractibleNPC(float x, float y, float width, float height, int texture) {
		super(x, y, width, height, texture);
		this.solid = true;
		this.bodyType = BodyType.STATIC;
	}

	@Override
	public InteractionResult interact(Entity interactor) {
		Logger.debug("Interacted with npc");
		return new InteractionResult(
		        InteractionType.DIALOG,
		        dialog != null ? dialog : "default"
		    );
	}

	@Override
	public void update(float dt) {}

	@Override
	public void render(Renderer renderer, Camera camera) {
		if (texture == -1 && color != null) {
			Renderer.drawRect(x - camera.x, y - camera.y, width, height, (float)((float)color.getRed() / 255f), (float)((float)color.getGreen() / 255f), (float)((float)color.getBlue() / 255f));
		} else {
			Renderer.drawRect(x - camera.x, y - camera.y, width, height, 0f, 0f, 0f);
		}
	}
}