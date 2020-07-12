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

    /**
     *
     * @param application the GameApplicationAdapter which runs the game
     * @return a reference to this object for chaining
     */
    public Game setGameApplicationAdapter(GameApplicationAdapter application){
        this.application = application;
        return this;
    }

    /**
     *
     * @return a reference to the GameApplicationAdapter
     */
    public GameApplicationAdapter getGameApplicationAdapter(){
        return application;
    }

    /**
     *
     * @param batch SpriteBatch used to draw sprites
     * @return a reference to this object for chaining
     */
    public Game setSpriteBatch(SpriteBatch batch){
        this.spriteBatch = batch;
        return this;
    }

    /**
     *
     * @return the SpriteBatch used for drawing sprites
     */
    public SpriteBatch getSpriteBatch(){return this.spriteBatch;}

    /**
     *
     * @param field used for displaying the individual tiles, structures, and other entities in the game
     * @return a reference to this object for chaining
     */
    public Game setField(Field field){
        this.field = field;
        field.setGame(this);
        return this;
    }

    /**
     *
     * @return the field that will next be displayed on the screen. This will remain true unless the Field in this Game
     * object is changed after this method call. Changing the field can be done through the setField(Field field);
     * method. If the behavior of the game object is changed however, it can be done through other means.
     */
    public Field getField(){return field;}

    /**
     *
     * @param handler which handles all inputs including key presses and mouse clicks.
     * @return a reference to this object for chaining
     */
    public Game setInputHandler(InputHandler handler){
        this.inputHandler = handler;
        return this;
    }

    /**
     *
     * @return the InputHandler which handles all inputs including key presses and mouse clicks.
     */
    public InputHandler getInputHandler(){return this.inputHandler;}

    /**
     * draws the current state of the game onto the screen. Usually, little to no changes in the state of the game
     * should occur in this method. Changes in state should happen in the update method.
     */
    public void draw(){
        field.draw(spriteBatch);
    }

    /**
     * updates the state of all objects which 
     */
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
