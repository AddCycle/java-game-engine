package engine.world.physics;

import engine.entities.Entity;
import engine.world.World2D;

public interface Physics2D {
	public void update(Entity entity, World2D world, float dt);
}