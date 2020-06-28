package com.hirshi001.billions.gamepieces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface Positionable{

    public Vector2 getLayerPosition();
    public void draw(Vector2 bottomLeft, Vector2 topRight, SpriteBatch b);

}
