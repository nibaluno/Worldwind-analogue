package org.example.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private float distance = 5.0f;
    private float yaw = 0, pitch = 0;

    public void addRotation(float dy, float dp) {
        yaw += dy; pitch += dp;
        if (pitch > 1.5f) pitch = 1.5f;
        if (pitch < -1.5f) pitch = -1.5f;
    }

    public void addZoom(float delta) { distance = Math.max(1.8f, Math.min(distance - delta, 40.0f)); }

    public Matrix4f getViewMatrix() {
         float x = (float)(distance * Math.cos(pitch) * Math.sin(yaw));
        float y = (float)(distance * Math.sin(pitch));
        float z = (float)(distance * Math.cos(pitch) * Math.cos(yaw));
         return new Matrix4f().lookAt(new Vector3f(x, y, z), new Vector3f(0,0,0), new Vector3f(0,1,0));
    }

    public Vector3f getPosition() {
         return new Vector3f((float)(distance * Math.cos(pitch) * Math.sin(yaw)),
                (float)(distance * Math.sin(pitch)),
                (float)(distance * Math.cos(pitch) * Math.cos(yaw)));
    }
}