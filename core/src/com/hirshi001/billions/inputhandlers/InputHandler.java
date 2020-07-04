package com.hirshi001.billions.inputhandlers;

import com.badlogic.gdx.InputMultiplexer;
import com.hirshi001.billions.game.Game;

/**
 * This object is in charge of handling user inputs such as key presses and button presses.
 */
public class InputHandler extends InputMultiplexer {

    /**
     * Reference to the game object which will be used by other InputAdapters in the pipeline.
     */
    private Game game;
    /**
     * Reference to the ScreenMover obejct which is in charge of handling the movement of the camera
     */
    private ScreenMover screenMover;

    public InputHandler(){
        screenMover = new ScreenMover(this);
        addProcessor(screenMover);
    }

    /**
     *
     * @param game sets the game object which all InputHandlers in the pipeline will use.
     * @return a reference to this object for chaining.
     */
    public InputHandler setGame(Game game){
        this.game = game;
        return this;
    }

    /**
     *
     * @return the game object which all InputHandlers in the pipeline uses
     */
    public Game getGame(){
        return game;
    }

    /**
     *
     * @return the ScreenMover object in charge of handling camera position.
     */
    public ScreenMover getScreenMover() {
        return screenMover;
    }
}
