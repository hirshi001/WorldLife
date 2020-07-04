package com.hirshi001.billions.gameadapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.billions.game.Game;

public abstract class GameApplicationAdapter implements Disposable {

    /**
     * Reference to the game object
     */
    private Game game;
    /**
     * Reference to the Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * Called before the program's main loop starts to setup all necessary objects and references.
     */
    public abstract void startup();

    /**
     *
     * @return reference to the game object
     */
    public Game getGame(){
        return game;
    }
    public final GameApplicationAdapter setGame(Game game){this.game = game; return this;}
    public void update(){
        getGame().update();
    }
    public void draw(SpriteBatch batch){
        game.setSpriteBatch(batch);
        game.draw();
    }
    public OrthographicCamera getCamera(){
        return this.camera;
    }
    public GameApplicationAdapter setCamera(OrthographicCamera camera){
        this.camera = camera;
        return this;
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
