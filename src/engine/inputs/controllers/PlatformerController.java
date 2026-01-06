package engine.inputs.controllers;

import org.lwjgl.glfw.GLFW;

import engine.entities.Player2D;
import engine.inputs.Inputs;

public class PlatformerController implements PlayerController2D {
	private Inputs input;
    private float speed = 120f;
    private float jumpSpeed = 450f;

    public PlatformerController(Inputs input) {
        this.input = input;
    }

    @Override
    public void update(Player2D player, float dt) {
        if (input.isKeyDown(GLFW.GLFW_KEY_A)) player.vx = -speed;
        else if (input.isKeyDown(GLFW.GLFW_KEY_D)) player.vx = speed;
        else player.vx = 0;

        if (input.isKeyJustPressed(GLFW.GLFW_KEY_SPACE) && player.onGround) {
            player.vy = -jumpSpeed;
            player.onGround = false;
        }
    }

}
