package com.hirshi001.billions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.billions.field.Field;
import com.hirshi001.billions.game.GameApplication;
import com.hirshi001.billions.game.GameApplicationAdapter;
import com.hirshi001.billions.gamepieces.entities.Player;
import com.hirshi001.billions.gamepieces.entities.Slime;
import com.hirshi001.billions.inputhandlers.InputHandler;
import com.hirshi001.billions.gamepieces.structures.House;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.util.camera.CameraStyles;

public class Game implements Disposable {

    private Field field;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private InputHandler inputHandler;

    private GameApplicationAdapter application;

    public Game(OrthographicCamera camera){
        this.camera = camera;
    }

    public Game setSpriteBatch(SpriteBatch batch){
        this.spriteBatch = batch;
        return this;
    }
    public SpriteBatch getSpriteBatch(){return this.spriteBatch;}
    public Game setField(Field f){
        this.field = f;
        return this;
    }
    public Field getField(){return field;}
    public Game setCamera(OrthographicCamera camera){
        this.camera = camera;
        return this;
    }
    public OrthographicCamera getCamera(){return this.camera;}
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
            float startX = getCamera().viewportWidth/2;
            float startY = getCamera().viewportHeight/2;
            float width = getField().getCols()*Block.BLOCKWIDTH-camera.viewportWidth;
            float height = getField().getRows()*Block.BLOCKHEIGHT-camera.viewportHeight;

            //to make camera movement smooth when the player is near the edge of the map because of boundry.

            Vector2 pos = CameraStyles.boundry(getField().getMainPlayer().getCenterPosition().scl(Block.BLOCKWIDTH,Block.BLOCKHEIGHT),startX,startY,width,height);
            CameraStyles.lerpToTarget(getCamera().position,pos,0.1f);
            CameraStyles.boundry(getCamera().position,startX, startY,width,height);
        }
    }

    @Override
    public void dispose() {
        field.dispose();
    }
}
