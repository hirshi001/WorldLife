package com.hirshi001.billions.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.Game;
import com.hirshi001.billions.field.Field;
import com.hirshi001.billions.gamepieces.entities.BoxGameEntity;
import com.hirshi001.billions.gamepieces.entities.Player;
import com.hirshi001.billions.gamepieces.entities.Slime;
import com.hirshi001.billions.gamepieces.items.Sword;
import com.hirshi001.billions.gamepieces.projecticles.Fireball;
import com.hirshi001.billions.gamepieces.structures.House;
import com.hirshi001.billions.inputhandlers.InputHandler;
import com.hirshi001.billions.registry.Registry;

public class GameApplication extends GameApplicationAdapter{

    private BoxGameEntity mainPlayer;

    @Override
    public void startup(){
        setGame(new Game(getCamera()));
        Field field = new Field(2000,2000, this);
        tiles(field);
        mainPlayer = new Player(new Vector2(100,100));
        field.setMainPlayer(mainPlayer);
        field.addMob(mainPlayer);

        for(int i=0;i<5;i++) field.addMob(new Slime(mainPlayer.getCenterPosition().cpy().add((int)(Math.random()*10)-5,(int)(Math.random()*10)+-5),mainPlayer).setField(field));
        for(int i=0;i<1000;i++) field.addStructure(new House(new Vector2((int)(Math.random()*(field.getCols()-3)),(int)(Math.random()*(field.getRows()-3)))));
        for(int i=0;i<1000;i++) field.addItem(new Sword(new Vector2((int)(Math.random()*(field.getCols()-3)),(int)(Math.random()*(field.getRows()-3)))));
        field.addProjectile(new Fireball(mainPlayer.getPosition().cpy(),new Vector2(1,1)));
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
                if(row==0 || row==tiles.length-1 || col == 0 || col==tiles[row].length-1) tiles[row][col] = Registry.WALL.getId();
                else if(Math.random()>0.95) tiles[row][col] = Registry.WALL.getId();
                else tiles[row][col] = Registry.GRASS.getId();
            }
        }
    }

}
