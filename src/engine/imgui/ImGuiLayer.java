package engine.imgui;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

public class ImGuiLayer {
	private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
	private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

	public void init(long windowId) {
		ImGui.createContext();

		ImGuiIO io = ImGui.getIO();
		io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
//        io.getFonts().addFontFromFileTTF("resources/fonts/Roboto-Regular.ttf", 18f);
//        io.getFonts().build();
		ImGui.getIO().setFontGlobalScale(1.5f);

		imGuiGlfw.init(windowId, true);
		imGuiGl3.init("#version 330");
	}

	public void begin() {
		imGuiGlfw.newFrame();
		imGuiGl3.newFrame();
		ImGui.newFrame();
	}

	public void end() {
		ImGui.render();
		imGuiGl3.renderDrawData(ImGui.getDrawData());
	}

	public void dispose() {
		imGuiGl3.shutdown();
		imGuiGlfw.shutdown();
		ImGui.destroyContext();
	}

	public ImGuiImplGlfw getImGuiGlfw() {
		return imGuiGlfw;
	}

	public ImGuiImplGl3 getImGuiGl3() {
		return imGuiGl3;
	}
}
