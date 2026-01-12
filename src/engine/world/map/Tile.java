package engine.world.map;

public class Tile {
	public boolean solid;
	public int textureId;
	
	public Tile(int textureId, boolean solid) {
        this.textureId = textureId;
        this.solid = solid;
    }
}