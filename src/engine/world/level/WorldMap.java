package engine.world.level;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.glfw.GLFW;

import engine.core.Engine;
import engine.entities.movement.Direction;
import engine.graphics.Renderer;
import engine.inputs.Inputs;
import engine.world.level.view.LevelView;

/**
 * Level selection/creation is handled by WorldMap TODO : make this class
 * working as soon as possible to move on...
 */
public class WorldMap {
	private Engine engine;
	Map<String, LevelNode> nodes = new HashMap<>();
	Map<String, LevelView> views = new HashMap<>();
	private int x = 100;
	private int y = 0;
	private int width = 100;
	private int height = 100;
	private int nodeW = 16;
	private int nodeH = 16;
	private int spacing = nodeW * 4;

	private String currentNodeId;

	// FIXME : temp test
	public void create(Engine engine) {
		this.engine = engine;

		nodes.put("level1", new LevelNode(true, "level2", "level3"));
		nodes.put("level2", new LevelNode(true, "level1"));

		nodes.put("level3", new LevelNode(true, "level1", "level4", "level5"));
		nodes.put("level4", new LevelNode(true, "level3"));
		nodes.put("level5", new LevelNode(true, "level3"));

		currentNodeId = "level1";

		views.put("level1", new LevelView(0, 0));
		views.put("level2", new LevelView(1, 0));
		views.put("level3", new LevelView(0, 1));
		views.put("level4", new LevelView(0, 2));
		views.put("level5", new LevelView(3, 1));
	}

	public void update() {
		Inputs input = engine.getInput();

		if (input.isKeyJustPressed(GLFW.GLFW_KEY_RIGHT))
			move(Direction.RIGHT);
		else if (input.isKeyJustPressed(GLFW.GLFW_KEY_LEFT))
			move(Direction.LEFT);
		else if (input.isKeyJustPressed(GLFW.GLFW_KEY_UP))
			move(Direction.UP);
		else if (input.isKeyJustPressed(GLFW.GLFW_KEY_DOWN))
			move(Direction.DOWN);
	}

	public void render(Renderer r) {
		for (Entry<String, LevelView> e : views.entrySet()) {
			String nodeId = e.getKey();
			LevelView v = e.getValue();

			int x1 = x + v.x * (nodeW + spacing);
			int y1 = y + v.y * (nodeH + spacing);

			// draw connections
			for (String neighborId : nodes.get(nodeId).neighbors) {
				LevelView nv = views.get(neighborId);
				if (nv == null)
					continue;

				int x2 = x + nv.x * (nodeW + spacing);
				int y2 = y + nv.y * (nodeH + spacing);

				Renderer.drawLine(x1 + nodeW / 2, y1 + nodeH / 2, x2 + nodeW / 2, y2 + nodeH / 2, Color.green);
			}
		}

		for (Entry<String, LevelView> e : views.entrySet()) {
			String nodeId = e.getKey();
			LevelView v = e.getValue();

			int x1 = x + v.x * (nodeW + spacing);
			int y1 = y + v.y * (nodeH + spacing);

			LevelNode current = nodes.get(currentNodeId);

			Color color;
			if (nodeId.equalsIgnoreCase(currentNodeId)) {
				color = Color.green;
			} else if (nodes.get(nodeId).unlocked) {
				color = Color.blue;
			} else {
				color = Color.pink;
			}

			// draw nodes
			Renderer.drawRect(x1, y1, nodeW, nodeH, color);
			r.drawTextCentered(nodeId, x1 + nodeW / 2, y1 + nodeH / 2, 8, 8);
		}
	}

	public void moveTo(String targetId) {
		LevelNode current = nodes.get(currentNodeId);

		if (!current.neighbors.contains(targetId))
			return;
		if (!nodes.get(targetId).unlocked)
			return;

		currentNodeId = targetId;
	}

	public void move(Direction dir) {
		LevelNode current = nodes.get(currentNodeId);
		LevelView currentView = views.get(currentNodeId);

		if (current.neighbors.isEmpty())
			return;

		for (String nodeId : current.neighbors) {
			LevelNode next = nodes.get(nodeId);
			LevelView nextView = views.get(nodeId);

			int dx = currentView.x - nextView.x;
			int dy = currentView.y - nextView.y;

			boolean isCorrectMove = switch (dir) {
			case LEFT -> dx > 0;
			case RIGHT -> dx < 0;
			case UP -> dy > 0;
			case DOWN -> dy < 0;
			};

			if (isCorrectMove && next.unlocked) {
				currentNodeId = nodeId;
				return;
			}
		}
	}
}