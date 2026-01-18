package engine.entities.movement;

public enum Direction {
	LEFT("left"),
	RIGHT("right"),
	UP("up"),
	DOWN("down");
	
	private String name;
	
	Direction(String face) {
		name = face;
	}
	
	public String getName() {
		return this.name;
	}
}