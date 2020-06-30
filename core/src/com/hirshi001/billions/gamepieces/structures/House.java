package com.hirshi001.billions.gamepieces.structures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.field.Field;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;

public class House extends GameStructure {

    public static final Texture t;
    private  StructureTile[][] structureTiles;
    static {
        t = new Texture("rpg-pack/props_n_decorations/generic-rpg-house-inn.png");
        Registry.addDisposable(t);

    }

    public House(Vector2 position) {
        super(position);
        structureTiles = GameStructure.convert(this, GameStructure.vFlip(new Integer[][]{
                {1,1,1,1},
                {1,1,1,1},
                {1,1,1,1},
                {0,2,2,0}
        }));
        Field f = new Field(30,30);
        tiles(f);
        setInsideField(f);
    }

    private void tiles(Field f){
        short[][] tiles = f.getTiles();
        int row, col;
        for(row=0;row<tiles.length;row++){
            for(col=0;col<tiles[row].length;col++){
                if(row==0 || row==tiles.length-1 || col == 0 || col==tiles[row].length-1) tiles[row][col] = Registry.WALL.getId();
                else if(Math.random()>0.95) tiles[row][col] = Registry.WALL.getId();
                else tiles[row][col] = Registry.GRASS.getId();
            }
        }
    }

    @Override
    public boolean hasField() {
        return true;
    }

    @Override
    public Vector2 entrancePosition() {
        return new Vector2(5,5);
    }

    @Override
    public void drawStructure(SpriteBatch batch) {
        batch.draw(t,getPosition().x* Block.BLOCKWIDTH-3, (getPosition().y+1)*Block.BLOCKHEIGHT);
    }

    @Override
    public void update() {

    }

    @Override
    public Vector2 getLayerPosition() {
        return getPosition().cpy().add(0,1);
    }

    @Override
    public StructureTile[][] getTiles() {
        return structureTiles;
    }

    @Override
    public float getWidth() {
        return (float)t.getWidth()/Block.BLOCKWIDTH;
    }

    @Override
    public float getHeight() {
        return (float)t.getHeight()/Block.BLOCKHEIGHT;
    }
}
