package engine.world.level;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.glfw.GLFW;

import engine.core.Engine;
import engine.core.Logger;
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
	private int startX = 50;
	private int startY = 50;
	private int width = 100;
	private int height = 100;
	private int nodeWidth = 16;
	private int nodeHeight = 16;
	private int nodeSpacing = nodeWidth * 2;

	Map<String, LevelNode> nodes = new HashMap<>();
	Map<String, LevelView> views = new HashMap<>();
	private String currentNodeId;

	public void create(Engine engine) {
		this.engine = engine;

		addNode("1", new Point(0, 0), true, "2", "3");
		addNode("2", new Point(1, 0), true, "1");
		addNode("3", new Point(0, 1), true, "1", "4", "5");
		addNode("4", new Point(0, 2), true, "3");
		addNode("5", new Point(3, 1), true, "3");

		currentNodeId = "1";
	}

	/**
	 * Add an unlocked level node to the worldMap
	 * 
	 * @param id
	 * @param position
	 * @param neighborsIds
	 */
	protected void addNode(String id, Point position, String... neighborsIds) {
		nodes.put(id, new LevelNode(false, neighborsIds));
		views.put(id, new LevelView(position.x, position.y));
	}

	/**
	 * Add a level node to the worldMap with parameters
	 * 
	 * @param id
	 * @param position
	 * @param unlocked
	 * @param neighborsIds
	 */
	protected void addNode(String id, Point position, boolean unlocked, String... neighborsIds) {
		nodes.put(id, new LevelNode(unlocked, neighborsIds));
		views.put(id, new LevelView(position.x, position.y));
	}

	public void update() {
		views.values().forEach(v -> v.hovered = false);

		if (isActionJustPressed(GLFW.GLFW_KEY_RIGHT, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_RIGHT)
				|| isAxisJustPressed(Direction.RIGHT))
			move(Direction.RIGHT);
		else if (isActionJustPressed(GLFW.GLFW_KEY_LEFT, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_LEFT)
				|| isAxisJustPressed(Direction.LEFT))
			move(Direction.LEFT);
		else if (isActionJustPressed(GLFW.GLFW_KEY_UP, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_UP)
				|| isAxisJustPressed(Direction.UP))
			move(Direction.UP);
		else if (isActionJustPressed(GLFW.GLFW_KEY_DOWN, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_DOWN)
				|| isAxisJustPressed(Direction.DOWN))
			move(Direction.DOWN);

		Point mouse = engine.getInput().getMousePos();

		// Check hover for each node
		for (Entry<String, LevelView> e : views.entrySet()) {
			LevelNode n = nodes.get(e.getKey());
			LevelView v = e.getValue();
			int nodeX = startX + v.x * (nodeWidth + nodeSpacing);
			int nodeY = startY + v.y * (nodeHeight + nodeSpacing);

			if (mouse.x >= nodeX && mouse.x <= nodeX + nodeWidth && mouse.y >= nodeY && mouse.y <= nodeY + nodeHeight) {
				v.hovered = true;
				if (engine.getInput().isMouseButtonJustPressed(GLFW.GLFW_MOUSE_BUTTON_LEFT))
				{
					Logger.info("Clicked node: %s", e.getKey());
					break;
				}
			}
		}
	}

	public void render(Renderer r) {
		r.drawTextCentered(currentNodeId, engine.getCamera().width / 2,
				Renderer.calculateTextDimensions(currentNodeId)[1]);

		for (Entry<String, LevelView> e : views.entrySet()) {
			String nodeId = e.getKey();
			LevelView v = e.getValue();

			int x1 = startX + v.x * (nodeWidth + nodeSpacing);
			int y1 = startY + v.y * (nodeHeight + nodeSpacing);

			// draw connections
			for (String neighborId : nodes.get(nodeId).neighbors) {
				LevelView nv = views.get(neighborId);
				if (nv == null)
					continue;

				int x2 = startX + nv.x * (nodeWidth + nodeSpacing);
				int y2 = startY + nv.y * (nodeHeight + nodeSpacing);

				Renderer.drawLine(x1 + nodeWidth / 2, y1 + nodeHeight / 2, x2 + nodeWidth / 2, y2 + nodeHeight / 2,
						Color.green);
			}
		}

		for (Entry<String, LevelView> e : views.entrySet()) {
			String nodeId = e.getKey();
			LevelView v = e.getValue();

			int x1 = startX + v.x * (nodeWidth + nodeSpacing);
			int y1 = startY + v.y * (nodeHeight + nodeSpacing);

			LevelNode current = nodes.get(currentNodeId);

			Color color;
			if (v.hovered) {
				color = Color.cyan;
			} else if (nodeId.equalsIgnoreCase(currentNodeId)) {
				color = Color.green;
			} else if (nodes.get(nodeId).unlocked) {
				color = Color.blue;
			} else {
				color = Color.pink;
			}

			// draw nodes
			Renderer.drawRect(x1, y1, nodeWidth, nodeHeight, color);
			r.drawTextCentered(nodeId, x1 + nodeWidth / 2, y1 + nodeHeight / 2, 8, 8);
		}

		Point m = engine.getInput().getMousePos();
		Renderer.drawRect(m.x - 2, m.y - 2, 4, 4, Color.red);
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

	private boolean isActionJustPressed(int keyboard, int gamepad) {
		Inputs input = engine.getInput();
		return input.isKeyJustPressed(keyboard) || input.isPadButtonJustPressed(gamepad);
	}

	private boolean isAxisJustPressed(Direction dir) {
		Inputs input = engine.getInput();
		return input.isAxisJustPressed(dir);
	}

	private boolean isActionDown(int keyboard, int gamepad) {
		Inputs input = engine.getInput();
		return input.isKeyDown(keyboard) || input.isPadButtonDown(gamepad);
	}
}