package com.hirshi001.billions.util.camera;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraStyles {

    public static Vector3 lerpToTarget(Vector3 cameraPos, Vector2 position, float factor){
        Vector2 pos = lerpToTarget(new Vector2(cameraPos.x, cameraPos.y), position, factor);
        cameraPos.x = pos.x;
        cameraPos.y = pos.y;
        return cameraPos;
    }

    public static Vector2 lerpToTarget(Vector2 cameraPos, Vector2 position, float factor){
        //linear interpolation
        cameraPos.x += (position.x-cameraPos.x) * factor;
        cameraPos.y += (position.y-cameraPos.y) * factor;
        return cameraPos;
    }

    public static Vector3 boundry(Vector3 cameraPos, float startX, float startY, float width, float height){
        Vector2 pos = boundry(new Vector2(cameraPos.x, cameraPos.y), startX, startY, width, height);
        cameraPos.x = pos.x;
        cameraPos.y = pos.y;
        return cameraPos;
    }

    public static Vector2 boundry(Vector2 cameraPos, float startX, float startY, float width, float height){
        if(cameraPos.x<startX) cameraPos.x = startX;
        if(cameraPos.x>startX+width) cameraPos.x = startX+width;
        if(cameraPos.y<startY) cameraPos.y = startY;
        if(cameraPos.y>startY+height) cameraPos.y = startY+height;
        return cameraPos;
    }


}
