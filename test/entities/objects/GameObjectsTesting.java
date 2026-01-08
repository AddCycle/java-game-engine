package entities.objects;

import engine.core.Logger;
import engine.entities.AnimatedPlayer2D;
import engine.entities.Entity;
import engine.entities.EntityManager;
import engine.entities.Player2D;
import engine.graphics.Renderer;
import engine.world.Camera;

public class GameObjectsTesting {
	
	public static Entity createTestCoin(Player2D player, EntityManager entityManager) {
	    Entity coin = new Entity(100, 100, 8, 8, 0) {
            @Override
            public void update(float dt) {}
            @Override
            public void render(Renderer r, Camera c) {
                Renderer.drawRect(x - c.x, y - c.y, width, height, 1f, 1f, 0f);
            }
        };

        coin.addCollisionListener(other -> {
            if (other == player) {
            		Logger.info("Coin gathered !");
            		coin.markedForRemoval = true;
            }
        });
        
        return coin;
	}

	public static Entity createTestCoin(AnimatedPlayer2D player, EntityManager entityManager) {
	    Entity coin = new Entity(100, 100, 8, 8, 0) {
            @Override
            public void update(float dt) {}
            @Override
            public void render(Renderer r, Camera c) {
                Renderer.drawRect(x - c.x, y - c.y, width, height, 1f, 1f, 0f);
            }
        };

        coin.addCollisionListener(other -> {
            if (other == player) {
            		Logger.info("Coin gathered !");
            		coin.markedForRemoval = true;
            }
        });
        
        return coin;
	}

}
