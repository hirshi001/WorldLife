package com.hirshi001.billions.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.billions.field.Field;
import com.hirshi001.billions.gameadapter.GameApplicationAdapter;
import com.hirshi001.billions.inputhandlers.InputHandler;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.util.camera.CameraStyles;

public class Game implements Disposable {

    private Field field;
    private SpriteBatch spriteBatch;
    private InputHandler inputHandler;

    private GameApplicationAdapter application;

    public Game(){  }

    public Game setGameApplicationAdapter(GameApplicationAdapter application){
        this.application = application;
        return this;
    }
    public GameApplicationAdapter getGameApplicationAdapter(){
        return application;
    }

    public Game setSpriteBatch(SpriteBatch batch){
        this.spriteBatch = batch;
        return this;
    }
    public SpriteBatch getSpriteBatch(){return this.spriteBatch;}
    public Game setField(Field f){
        this.field = f;
        field.setGame(this);
        return this;
    }
    public Field getField(){return field;}
    public Game setInputHandler(InputHandler handler){
        this.inputHandler = handler;
        return this;
    }
    public InputHandler getInputHandler(){return this.inputHandler;}


    public void draw(){
        field.draw(spriteBatch);
    }

    public void update(){
        field.update();
        handleCameraPosition();
    }

    private void handleCameraPosition(){
        if(getInputHandler().getScreenMover().isCameraFollow()){
            OrthographicCamera camera = getGameApplicationAdapter().getCamera();
            float startX = camera.viewportWidth/2;
            float startY = camera.viewportHeight/2;
            float width = getField().getCols()*Block.BLOCKWIDTH-camera.viewportWidth;
            float height = getField().getRows()*Block.BLOCKHEIGHT-camera.viewportHeight;

            //to make camera movement smooth when the player is near the edge of the map because of boundry.

            Vector2 pos = CameraStyles.boundary(getField().getMainPlayer().getCenterPosition().scl(Block.BLOCKWIDTH,Block.BLOCKHEIGHT),startX,startY,width,height);
            CameraStyles.lerpToTarget(camera.position,pos,0.1f);
            CameraStyles.boundary(camera.position,startX, startY,width,height);
        }
    }

    @Override
    public void dispose() {
        field.dispose();
    }
}
