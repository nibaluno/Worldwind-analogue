package org.example.graphics;

import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private final int vao, vertexCount;
    public Mesh(List<Float> v, List<Integer> idx) {
        vertexCount = idx.size();
        vao = glGenVertexArrays();
        int vbo = glGenBuffers(), ebo = glGenBuffers();
        glBindVertexArray(vao);

        FloatBuffer vb = MemoryUtil.memAllocFloat(v.size());
        for (float f : v) vb.put(f);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vb.flip(), GL_STATIC_DRAW);

        IntBuffer ib = MemoryUtil.memAllocInt(idx.size());
        for (int i : idx) ib.put(i);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ib.flip(), GL_STATIC_DRAW);

        int s = 8 * 4;
        glVertexAttribPointer(0, 3, GL_FLOAT, false, s, 0); glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, s, 3 * 4); glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, s, 5 * 4); glEnableVertexAttribArray(2);
        MemoryUtil.memFree(vb); MemoryUtil.memFree(ib);
    }
    public void draw() { glBindVertexArray(vao); glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0); }
}