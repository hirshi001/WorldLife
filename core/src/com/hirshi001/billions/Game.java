package com.hirshi001.billions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.billions.camera.CameraStyles;
import com.hirshi001.billions.gamepieces.entities.Player;
import com.hirshi001.billions.gamepieces.entities.Slime;
import com.hirshi001.billions.inputhandlers.InputHandler;
import com.hirshi001.billions.gamepieces.structures.House;
import com.hirshi001.billions.registry.Block;

public class Game implements Disposable {

    private Field field;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private InputHandler inputHandler;
    private Player p;
    public Game(OrthographicCamera camera){
        /* set field */
        field = new Field(500,500, camera);
        field.setMainPlayer(p = new Player(new Vector2(10,470)));
        p.setField(field);
        field.addMob(p);
        for(int i=0;i<10;i++) field.addMob(new Slime(new Vector2(100,100),p).setField(field));
        for(int i=0;i<1000;i++) field.addStructure(new House(new Vector2((int)(Math.random()*400)+50,(int)(Math.random()*400)+50)));
        //field.addStructure(new House(new Vector2(0,0)));
        this.camera = camera;
        Gdx.input.setInputProcessor(inputHandler = new InputHandler(camera, field));
        camera.position.x = field.getMainPlayer().getCenterPosition().x* Block.BLOCKWIDTH;
        camera.position.y = field.getMainPlayer().getCenterPosition().y*Block.BLOCKHEIGHT;
    }

    public Game setSpriteBatch(SpriteBatch batch){
        this.spriteBatch = batch;
        return this;
    }

    public Field getField(){return field;}


    public void draw(){
        field.draw(spriteBatch);
    }

    public void update(){

        field.update();
        handleCameraPosition();

    }

    private void handleCameraPosition(){
        if(inputHandler.getScreenMover().isCameraFollow()){
            float startX = camera.viewportWidth/2;
            float startY = camera.viewportHeight/2;
            float width = field.getCols()*Block.BLOCKWIDTH-camera.viewportWidth;
            float height = field.getRows()*Block.BLOCKHEIGHT-camera.viewportHeight;

            //to make camera movement smooth when the player is near the edge of the map because of boundry.

            Vector2 pos = CameraStyles.boundry(getField().getMainPlayer().getCenterPosition().scl(Block.BLOCKWIDTH,Block.BLOCKHEIGHT),startX,startY,width,height);
            CameraStyles.lerpToTarget(camera.position,pos);
            CameraStyles.boundry(camera.position,startX, startY,width,height);
        }
    }

    @Override
    public void dispose() {
        field.dispose();
    }
}
