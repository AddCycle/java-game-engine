package engine.ui.menu;

public class MenuItem {
	public final String label;
    public final Runnable action;

    public MenuItem(String label, Runnable action) {
        this.label = label;
        this.action = action;
    }
}
