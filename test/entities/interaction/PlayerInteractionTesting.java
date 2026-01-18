package entities.interaction;

import java.awt.Color;

public class PlayerInteractionTesting {

	public static InteractibleNPC createTestNPC(float x, float y, float w, float h, Color color) {
		return new InteractibleNPC(x, y, w, h, color);
	}
	
}
