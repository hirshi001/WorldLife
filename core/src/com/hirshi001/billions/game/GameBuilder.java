package com.hirshi001.billions.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.hirshi001.billions.field.Field;
import com.hirshi001.billions.gameadapter.GameApplicationAdapter;
import com.hirshi001.billions.inputhandlers.InputHandler;

public class GameBuilder {

    private OrthographicCamera camera;
    private Field field;
    private InputHandler handler;
    private GameApplicationAdapter adapter;

    public GameBuilder(OrthographicCamera camera, Field field){
        this.camera = camera;
        this.field = field;
    }

    public GameBuilder inputHandler(InputHandler handler){
        this.handler = handler;
        return this;
    }

    public GameBuilder gameApplicationAdapter(GameApplicationAdapter adapter){
        this.adapter = adapter;
        return this;
    }

    public Game build(){
        return build(new Game());
    }

    public Game build(Game game){
        game.setCamera(camera).setField(field).setInputHandler(handler).setGameApplicationAdapter(adapter);
        return game;
    }






}
