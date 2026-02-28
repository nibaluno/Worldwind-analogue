package org.example.core;

import org.example.math.Camera;
import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private final long window;
    private final Camera camera;
    private double lastX, lastY;
    private boolean mousePressed = false;

    public Input(long window, Camera camera) {
        this.window = window;
        this.camera = camera;
    }

    public void init() {
        glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT) {
                mousePressed = (action == GLFW_PRESS);
                double[] x = new double[1], y = new double[1];
                glfwGetCursorPos(window, x, y);
                lastX = x[0]; lastY = y[0];
            }
        });

        glfwSetCursorPosCallback(window, (w, xpos, ypos) -> {
            if (mousePressed) {
                float dx = (float) (xpos - lastX);
                float dy = (float) (ypos - lastY);
                camera.addRotation(dx * 0.005f, dy * 0.005f);
            }
            lastX = xpos; lastY = ypos;
        });

        glfwSetScrollCallback(window, (w, xoffset, yoffset) -> {
            camera.addZoom((float) yoffset * 0.5f);
        });
    }
}