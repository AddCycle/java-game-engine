package engine.world.physics;

import engine.entities.Entity;
import engine.world.World2D;

public class TopdownPhysics implements Physics2D {

	@Override
	public void update(Entity e, World2D world, float dt) {
		float nextX = e.x + e.vx * dt;
        float nextY = e.y + e.vy * dt;

        if (!world.isColliding(nextX, e.y, e.width, e.height))
            e.x = nextX;

        if (!world.isColliding(e.x, nextY, e.width, e.height))
            e.y = nextY;
	}
}