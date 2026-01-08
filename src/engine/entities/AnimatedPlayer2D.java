package engine.entities;

import engine.core.Logger;
import engine.graphics.Renderer;
import engine.inputs.controllers.PlayerController2D;
import engine.world.Camera;

public class AnimatedPlayer2D extends AnimatedEntity {
	private PlayerController2D controller;
	public int tileX, tileY;
	public int targetTileX, targetTileY;

	public AnimatedPlayer2D(PlayerController2D controller) {
		this(controller, 0, 0, 16, 16);
	}

	public AnimatedPlayer2D(PlayerController2D controller, float x, float y, float width, float height) {
		super(x, y, width, height);
		this.controller = controller;
	}

	@Override
	public void update(float dt) {
		if (controller != null)
			controller.update(this, dt);
		super.update(dt);
		Logger.debug("facing: %s, state: %s", facing, state);
	}

	@Override
	public void render(Renderer renderer, Camera camera) {
		float camX = camera.x;
		float camY = camera.y;
		renderer.drawTexturedRect(texture, x - camX, y - camY, width, height);
	}

	public void setController(PlayerController2D newController) {
		controller = newController;
	}

	public void reset() {
		x = 0;
		y = 0;
		vx = 0;
		vy = 0;
	}
}