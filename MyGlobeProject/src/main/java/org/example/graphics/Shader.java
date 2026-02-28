package org.example.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final int id;
    private final FloatBuffer fb = BufferUtils.createFloatBuffer(16);

    public Shader(String v, String f) {
        try {
            int vs = glCreateShader(GL_VERTEX_SHADER);
            glShaderSource(vs, new String(Files.readAllBytes(Paths.get("src/main/resources/shaders/" + v))));
            glCompileShader(vs);
            int fs = glCreateShader(GL_FRAGMENT_SHADER);
            glShaderSource(fs, new String(Files.readAllBytes(Paths.get("src/main/resources/shaders/" + f))));
            glCompileShader(fs);
            id = glCreateProgram();
            glAttachShader(id, vs); glAttachShader(id, fs);
            glLinkProgram(id);
        } catch (Exception e) { throw new RuntimeException(e); }
    }
    public void use() { glUseProgram(id); }
    public void setMat4(String n, Matrix4f m) { glUniformMatrix4fv(glGetUniformLocation(id, n), false, m.get(fb)); }
    public void setVec3(String n, Vector3f v) { glUniform3f(glGetUniformLocation(id, n), v.x, v.y, v.z); }
}