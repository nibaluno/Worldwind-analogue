package org.example;

import org.example.core.Input;
import org.example.graphics.*;
import org.example.math.Camera;
import org.example.scene.Globe;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    private long window;
    private int w = 1280, h = 720;
    private Camera camera = new Camera();

    public void run() {
        if (!glfwInit()) return;
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        window = glfwCreateWindow(w, h, "3D Globe OOP - BGUIR Project", 0, 0);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);

        Input input = new Input(window, camera);
        input.init();

        glfwSetFramebufferSizeCallback(window, (win, newW, newH) -> {
            w = newW; h = newH; glViewport(0, 0, w, h);
        });

        Shader shader = new Shader("globe.vert", "globe.frag");
        Globe gen = new Globe();
        Mesh earth = gen.createSphere(1.5f, 64);
        Mesh stars = gen.createSphere(35.0f, 32);
        Texture earthTex = new Texture("src/main/resources/earth.jpg");
        Texture starsTex = new Texture("src/main/resources/stars.jpg");

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            shader.use();

            Matrix4f proj = new Matrix4f().perspective((float)Math.toRadians(45), (float)w/h, 0.1f, 100f);
            shader.setMat4("projection", proj);
            shader.setMat4("view", camera.getViewMatrix());
            shader.setVec3("viewPos", camera.getPosition());
            shader.setVec3("sunPos", new Vector3f(10, 5, 10)); // Позиция солнца

            // 1. Звезды
            glDepthMask(false);
            shader.setMat4("model", new Matrix4f().rotateY((float)glfwGetTime() * 0.01f));
            starsTex.bind(); stars.draw();
            glDepthMask(true);

            // 2. Земля
            shader.setMat4("model", new Matrix4f());
            earthTex.bind(); earth.draw();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        glfwTerminate();
    }

    public static void main(String[] args) { new Main().run(); }
}