package engine.inputs.controllers;

import engine.entities.Entity;
import engine.inputs.Inputs;
import engine.inputs.action.Action;
import engine.inputs.keybinds.Keybinds;
import engine.world.map.TileMap;

public class PlatformerController implements PlayerController2D {
	private Inputs input;
	private Keybinds keybinds;
    private float speed = 120f;
    private float jumpSpeed = 450f;

    public PlatformerController(Inputs input, Keybinds keybinds) {
        this.input = input;
        this.keybinds = keybinds;
    }

    @Override
    public void update(Entity player, float dt) {
        if (keybinds.isDown(input, Action.MOVE_LEFT)) player.vx = -speed;
        else if (keybinds.isDown(input, Action.MOVE_RIGHT)) player.vx = speed;
        else player.vx = 0;

        if (keybinds.isJustPressed(input, Action.JUMP) && player.onGround) {
            player.vy = -jumpSpeed;
            player.onGround = false;
        }
    }

	/**
	 * Doesn't have tilemap yet
	 */
	public void setTileMap(TileMap tilemap) {
		// doesn't have access to the tilemap
	}
}