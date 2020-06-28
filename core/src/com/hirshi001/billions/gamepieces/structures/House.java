package com.hirshi001.billions.gamepieces.structures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;

public class House extends Structure {

    public static final Texture t;
    private int[][] tiles;
    static {
        t = new Texture("rpg-pack/props_n_decorations/generic-rpg-house-inn.png");
        Registry.addDisposable(t);
    }

    public House(Vector2 position) {
        super(position);
        tiles = Structure.vFlip(new int[][]{
                {1,1,1,1},
                {1,1,1,1},
                {1,1,1,1},
                {0,2,2,0}
        });
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
    public int[][] getTiles() {
        return tiles;
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
