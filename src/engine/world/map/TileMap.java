package engine.world.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import engine.entities.Entity;

public class TileMap {
	private int width, height, tileSize;
	private List<Entity> entities = new ArrayList<>();
	
	private List<TileLayer> layers = new ArrayList<>();
	private Map<String, Object> properties; // map-level data
	private List<MapConnection> connections = new ArrayList<>();

	/**
	 * Creates a tilemap object which contains the 2d map infos
	 * @param mapData the data of the map (textureID pos inside the array is the id of the tile)
	 * @param tileSize size
	 * @param texturesID
	 */
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
	}
	
	public void addLayer(TileLayer layer) {
	    if (layers.isEmpty()) {
	        this.width = layer.getWidth();
	        this.height = layer.getHeight();
	    }
	    layers.add(layer);
	}
	
	public void addConnection(MapConnection c) {
        connections.add(c);
    }

    public MapConnection getConnectionAt(int tx, int ty) {
        for (MapConnection c : connections) {
            if (c.fromX == tx && c.fromY == ty)
                return c;
        }
        return null;
    }
	
	public void update(float dt) {
		
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTileSize() {
		return tileSize;
	}

	public boolean isSolid(int x, int y) {
	    if (x < 0 || y < 0 || x >= width || y >= height) return true;
	    
	    for (TileLayer layer : layers) {
	        if (!layer.isCollisionLayer()) continue;

	        int tileId = layer.get(x, y);
	        if (tileId == 0) {
	        		return false;
	        }
	        return true;
	    }
	    return false;
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void addEntities(Collection<? extends Entity> e) {
		entities.addAll(e);
	}

	public List<TileLayer> getLayers() {
		return layers;
	}

	public List<MapConnection> getConnections() {
		return connections;
	}

	public List<Entity> getEntities() {
		return entities;
	}
}