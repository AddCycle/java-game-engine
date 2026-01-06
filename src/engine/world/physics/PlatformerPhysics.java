package engine.world.physics;

import engine.entities.Entity;
import engine.world.World2D;

public class PlatformerPhysics implements Physics2D {
	public static final float GRAVITY = 1200f;

	@Override
	public void update(Entity entity, World2D world, float dt) {
		entity.vy += GRAVITY * dt; // apply gravity

		// horizontal move
        float nextX = entity.x + entity.vx * dt;
        if (!world.isColliding(nextX, entity.y, entity.width, entity.height)) {
            entity.x = nextX;
        }

        // vertical move
        float nextY = entity.y + entity.vy * dt;
        if (!world.isColliding(entity.x, nextY, entity.width, entity.height)) {
            entity.y = nextY;
            entity.onGround = false;
        } else {
            if (entity.vy > 0) entity.onGround = true;
            entity.vy = 0;
        }
	}
}
