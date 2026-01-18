package engine.graphics;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

/**
 * Using a tileset can save a lot of memory especially instead of using the OpenGL
 * that required many and sucks memory a lot... (regretting this night)
 */
public class Tileset {
	private int textureID;
    private final int tileSize;
    private int tilesX, tilesY;

    public Tileset(String path, int tileSize) {
        this.tileSize = tileSize;

        loadTexture(path);
    }

    private void loadTexture(String path) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer image = STBImage.stbi_load(path, w, h, channels, 4);
            if (image == null) throw new RuntimeException("Failed to load tileset: " + path);

            textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w.get(0), h.get(0), 0,
                    GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);

            STBImage.stbi_image_free(image);

            tilesX = w.get(0) / tileSize;
            tilesY = h.get(0) / tileSize;
        }
	}

	public int getTextureID() {
        return textureID;
    }

    /**
     * Returns UV coordinates (u0,v0,u1,v1) for the tile at (tx, ty) in the tileset
     */
    public float[] getTileUV(int tx, int ty) {
        float u0 = tx * (float) tileSize / (tilesX * tileSize);
        float v0 = ty * (float) tileSize / (tilesY * tileSize);
        float u1 = (tx + 1) * (float) tileSize / (tilesX * tileSize);
        float v1 = (ty + 1) * (float) tileSize / (tilesY * tileSize);
        return new float[]{u0, v0, u1, v1};
    }

    public void destroy() {
        GL11.glDeleteTextures(textureID);
    }
    
    public int getTilesX() {
    		return tilesX;
    }

    public int getTilesY() {
    		return tilesY;
    }
}