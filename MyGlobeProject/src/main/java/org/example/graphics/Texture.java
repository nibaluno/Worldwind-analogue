package org.example.graphics;

import org.lwjgl.system.MemoryStack;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    private final int id;
    public Texture(String path) {
        id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);
        try (MemoryStack stack = MemoryStack.stackPush()) {
             IntBuffer w = stack.mallocInt(1), h = stack.mallocInt(1), c = stack.mallocInt(1);

             stbi_set_flip_vertically_on_load(true);
             ByteBuffer data = stbi_load(path, w, h, c, 4);
             if (data != null) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w.get(), h.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
                 glGenerateMipmap(GL_TEXTURE_2D);
                stbi_image_free(data);
            }
        }
    }
        public void bind() { glBindTexture(GL_TEXTURE_2D, id); }
}