package engine.inputs.controllers;

import org.lwjgl.glfw.GLFW;

import engine.entities.Player2D;
import engine.inputs.Inputs;
import engine.inputs.Keybinds;
import engine.inputs.action.Action;

public class PlatformerController implements PlayerController2D {
	private Inputs input;
	private Keybinds keybinds;
    private float speed = 120f;
    private float jumpSpeed = 450f;

    public PlatformerController(Inputs input) {
        this.input = input;
        this.keybinds = new Keybinds();
    }

    @Override
    public void update(Player2D player, float dt) {
        if (keybinds.isDown(input, Action.MOVE_LEFT)) player.vx = -speed;
        else if (keybinds.isDown(input, Action.MOVE_RIGHT)) player.vx = speed;
        else player.vx = 0;

        if (keybinds.isJustPressed(input, Action.JUMP) && player.onGround) {
            player.vy = -jumpSpeed;
            player.onGround = false;
        }
    }

}
