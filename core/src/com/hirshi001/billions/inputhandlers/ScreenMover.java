package com.hirshi001.billions.inputhandlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.hirshi001.billions.registry.Block;

public class ScreenMover extends InputAdapter {

    private InputHandler handler;

    private int lastX, lastY;
    private boolean cameraFollow = true;

    public ScreenMover(InputHandler handler){
        this.handler = handler;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode==Input.Keys.SHIFT_LEFT && !isCameraFollow()){
            handler.getCamera().position.x = handler.getField().getMainPlayer().getCenterPosition().x*Block.BLOCKWIDTH;
            handler.getCamera().position.y = handler.getField().getMainPlayer().getCenterPosition().y*Block.BLOCKHEIGHT;
            return true;
        }
        if(keycode==Input.Keys.R){
            cameraFollow = !cameraFollow;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        lastX = screenX;
        lastY = screenY;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            handler.getCamera().translate(lastX - screenX, screenY-lastY);
            lastX = screenX;
            lastY = screenY;
            cameraFollow = false;
            return true;
        }
        return false;
    }

    public boolean isCameraFollow() {
        return cameraFollow;
    }
}
