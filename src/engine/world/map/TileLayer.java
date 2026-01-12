package engine.world.map;

public class TileLayer {
	public String name;
    private int width, height;
    private int[][] data;
    public boolean collision;
    public boolean visible;

    public TileLayer(int[][] data, boolean collision) {
        this.data = data;
        this.height = data.length;
        this.width = data[0].length;
        this.collision = collision;
    }

    public int get(int x, int y) {
        return data[y][x];
    }

    public boolean isCollisionLayer() {
        return collision;
    }

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
