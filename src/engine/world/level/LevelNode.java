package engine.world.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelNode {
    boolean unlocked;
    List<String> neighbors = new ArrayList<>();

	public LevelNode(boolean unlocked, String... neighbors) {
		this.unlocked = unlocked;
		this.neighbors = Arrays.asList(neighbors);
	}
}