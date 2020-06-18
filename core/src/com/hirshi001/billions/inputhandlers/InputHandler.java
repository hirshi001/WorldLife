package com.hirshi001.billions.inputhandlers;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.hirshi001.billions.field.Field;

public class InputHandler extends InputMultiplexer {

    private OrthographicCamera camera;
    private Field field;
    private ScreenMover screenMover;

    public InputHandler(OrthographicCamera camera, Field field){
        this.camera = camera;
        this.field = field;
        screenMover = new ScreenMover(this);
        addProcessor(screenMover);
       // addProcessor();
    }

    public OrthographicCamera getCamera(){
        return camera;
    }

    public Field getField(){
        return field;
    }

    public ScreenMover getScreenMover() {
        return screenMover;
    }
}
