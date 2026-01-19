package engine.ui.components;

import java.awt.Color;

import engine.graphics.Renderer;

public class UIPanel extends UIComponent {
	private Color color;

	public UIPanel() {
		super();
	}

	public UIPanel(float x, float y, float width, float height, UIComponent parent) {
		super(x, y, width, height, parent);
	}
	
	public void setBackgroundColor(Color bgcolor) {
		this.color = bgcolor;
	}

	@Override
	protected void drawSelf(Renderer renderer) {
		if (color != null) {
			renderer.drawRect(getAbsoluteX(), getAbsoluteY(), width, height, color);
		}
	}
}
