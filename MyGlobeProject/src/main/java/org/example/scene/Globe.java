package org.example.scene;

import org.example.graphics.Mesh;
import java.util.ArrayList;
import java.util.List;

public class Globe {
    public Mesh createSphere(float r, int det) {
        List<Float> v = new ArrayList<>();
        List<Integer> idx = new ArrayList<>();
        for (int i = 0; i <= det; i++) {
            float lat = (float)Math.PI / 2 - (float)i * (float)Math.PI / det;
            for (int j = 0; j <= det; j++) {
                float lon = (float)j * 2 * (float)Math.PI / det;
                float x = r * (float)Math.cos(lat) * (float)Math.cos(lon);
                float y = r * (float)Math.sin(lat);
                float z = r * (float)Math.cos(lat) * (float)Math.sin(lon);
                v.add(x); v.add(y); v.add(z);
                v.add((float)j/det); v.add((float)i/det);
                v.add(x/r); v.add(y/r); v.add(z/r);
            }
        }
        for (int i = 0; i < det; i++) {
            for (int j = 0; j < det; j++) {
                int k1 = i * (det + 1) + j, k2 = k1 + det + 1;
                idx.add(k1); idx.add(k2); idx.add(k1 + 1);
                idx.add(k1 + 1); idx.add(k2); idx.add(k2 + 1);
            }
        }
        return new Mesh(v, idx);
    }
}