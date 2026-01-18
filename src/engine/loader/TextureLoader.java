package engine.loader;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class TextureLoader {
	private ArrayList<Integer> textures = new ArrayList<>();

	public TextureLoader() {
		
	}

	public int loadTexture(String path) {
	    int textureID = GL11.glGenTextures();

	    GL11.glBindTexture(GL_TEXTURE_2D, textureID);

	    // Texture parameters
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	    glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL_REPEAT);
	    glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	    glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_NEAREST);

	    try (MemoryStack stack = MemoryStack.stackPush()) {
	        IntBuffer w = stack.mallocInt(1);
	        IntBuffer h = stack.mallocInt(1);
	        IntBuffer channels = stack.mallocInt(1);

	        ByteBuffer image = STBImage.stbi_load(path, w, h, channels, 4);
	        if (image == null) throw new RuntimeException(String.format("[ENGINE/LOADER] Failed to load texture : %s", path));

	        glTexImage2D(
	            GL_TEXTURE_2D,
	            0,
	            GL_RGBA,
	            w.get(),
	            h.get(),
	            0,
	            GL_RGBA,
	            GL_UNSIGNED_BYTE,
	            image
	        );

	        STBImage.stbi_image_free(image);
	    }

	    textures.add(textureID);
	    return textureID;
	}
	
	public void destroyTextures() {
		for (int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}
}