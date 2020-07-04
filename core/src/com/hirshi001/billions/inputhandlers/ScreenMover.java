package com.hirshi001.billions.inputhandlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.util.camera.CameraStyles;

public class ScreenMover extends InputAdapter {

    /**
     * The input handler which contains the game object which this ScreenMover will modify
     */
    private InputHandler handler;

    /**
     * The last x position of the mouse when the Right button is pressed
     */
    private int lastX;
    /**
     * The last y position of the mouse when the Right button is pressed
     */
    private int lastY;

    /**
     * whether or not the camera will follow the main player.
     */
    private boolean cameraFollow = true;

    /**
     *
     * @param handler the InputHandler which contains a reference to the game object which will be used by this object.
     */
    public ScreenMover(InputHandler handler){
        this.handler = handler;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode==Input.Keys.SHIFT_LEFT && !isCameraFollow()){
            OrthographicCamera camera = handler.getGame().getGameApplicationAdapter().getCamera();
            camera.position.x = handler.getGame().getField().getMainPlayer().getCenterPosition().x*Block.BLOCKWIDTH;
            camera.position.y = handler.getGame().getField().getMainPlayer().getCenterPosition().y*Block.BLOCKHEIGHT;
        }
        if(keycode==Input.Keys.R){
            cameraFollow = !cameraFollow;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //used for updating the camera position in the touchDragged method
        lastX = screenX;
        lastY = screenY;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Moves the camera in the same direction as the mouse to "drag the field"
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            OrthographicCamera camera = handler.getGame().getGameApplicationAdapter().getCamera();
            camera.translate(lastX - screenX, screenY-lastY);
            lastX = screenX;
            lastY = screenY;
            //sets it so the camera will not follow the player anymore
            cameraFollow = false;
            return true;
        }
        return false;
    }

    /**
     *
     * @return whether the camera is following the player
     */
    public boolean isCameraFollow() {
        return cameraFollow;
    }

    /**
     *
     * @param isCameraFollow whether the camera should follow the main player or not.
     *                       If true, it will follow. If false, it will not follow.
     *
     * @return returns itself for chaining
     */
    public ScreenMover setIsCameraFollow(boolean isCameraFollow) {
        this.cameraFollow = isCameraFollow;
        return this;
    }
}
