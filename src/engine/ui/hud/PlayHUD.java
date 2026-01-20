package engine.ui.hud;

import java.awt.Color;

import engine.graphics.Renderer;
import engine.ui.UIManager;
import engine.ui.components.UIBox;
import engine.ui.components.UILabel;
import engine.ui.components.UIPanel;
import engine.ui.constraints.Anchor;
import engine.ui.constraints.Margin;
import engine.ui.constraints.Padding;

public class PlayHUD {
	private UIPanel root;

    public PlayHUD(UIManager ui) {
        root = new UIPanel(0, 0, ui.getDisplayWidth(), ui.getDisplayHeight(), ui.getRoot());

        UIBox box = new UIBox(0, 0, 100, 150, root);
        box.setBackgroundColor(Color.black);
        box.setOutlineColor(Color.white);
        box.setMargin(new Margin(0, 10, 0, 0));
        box.setPadding(new Padding(5, 5, 10, 0));
        box.setAnchor(Anchor.CENTER_RIGHT);
        box.setBorder(4);
        
        UILabel label = new UILabel(0, 0, box);
//        label.setMargin(new Margin(10));
        label.setText("Inventory");
        label.setAnchor(Anchor.TOP_CENTER);
        box.add(label);
        
        root.add(box);
    }
    
    public void render(Renderer renderer) {
    		root.render(renderer);
    }

    public UIPanel getRoot() {
        return root;
    }
}
