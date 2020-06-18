package com.hirshi001.billions.camera;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.hirshi001.billions.registry.Block;

public class CameraStyles {

    public static void lerpToTarget(Camera camera, Vector2 position){

        Vector3 pos = camera.position;

        //linear interpolation
        pos.x += (position.x-pos.x) * 0.1f;
        pos.y += (position.y-pos.y) * 0.1f;
    }


}
