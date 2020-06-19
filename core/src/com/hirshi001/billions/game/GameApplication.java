package com.hirshi001.billions.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.Game;
import com.hirshi001.billions.field.Field;
import com.hirshi001.billions.gamepieces.entities.BoxEntity;
import com.hirshi001.billions.gamepieces.entities.Player;
import com.hirshi001.billions.gamepieces.entities.Slime;
import com.hirshi001.billions.gamepieces.structures.House;
import com.hirshi001.billions.inputhandlers.InputHandler;
import com.hirshi001.billions.registry.Registry;

public class GameApplication extends GameApplicationAdapter{

    private BoxEntity mainPlayer;

    @Override
    public void startup(){
        setGame(new Game(getCamera()));
        Field field = new Field(500,500, this);
        tiles(field);
        mainPlayer = new Player(new Vector2(20,20));
        field.setMainPlayer(mainPlayer);
        field.addMob(mainPlayer);

        for(int i=0;i<5;i++) field.addMob(new Slime(new Vector2((int)(Math.random()*20)+1,(int)(Math.random()*20)+1),mainPlayer).setField(field));
        for(int i=0;i<1000;i++) field.addStructure(new House(new Vector2((int)(Math.random()*450)+10,(int)(Math.random()*450)+10)));
        getGame().setField(field).setInputHandler(new InputHandler(getCamera(), field));
        Gdx.input.setInputProcessor(getGame().getInputHandler());
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    private void tiles(Field f){
        int[][] tiles = f.getTiles();
        int row, col;
        for(row=0;row<tiles.length;row++){
            for(col=0;col<tiles[row].length;col++){
                if(Math.random()>0.95) tiles[row][col] = Registry.WALL.getId();
                else tiles[row][col] = Registry.GRASS.getId();
            }
        }
    }

}
