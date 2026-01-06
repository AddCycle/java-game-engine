package engine.inputs.controllers;

import org.lwjgl.glfw.GLFW;

import engine.entities.Player2D;
import engine.inputs.Inputs;

public class TopdownController implements PlayerController2D {
	private Inputs input;
	private float speed = 60f;

	public TopdownController(Inputs input) {
		this.input = input;
	}

	@Override
	public void update(Player2D player, float dt) {
		player.vx = 0;
		player.vy = 0;

		if (input.isKeyDown(GLFW.GLFW_KEY_A)) player.vx = -speed;
		if (input.isKeyDown(GLFW.GLFW_KEY_D)) player.vx = speed;
		if (input.isKeyDown(GLFW.GLFW_KEY_W)) player.vy = -speed;
		if (input.isKeyDown(GLFW.GLFW_KEY_S)) player.vy = speed;
	}
}