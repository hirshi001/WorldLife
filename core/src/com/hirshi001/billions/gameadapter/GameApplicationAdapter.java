package com.hirshi001.billions.gameadapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.billions.game.Game;

public abstract class GameApplicationAdapter implements Disposable {

    private Game game;
    private OrthographicCamera camera;

    public abstract void startup();
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
