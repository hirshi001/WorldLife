package com.hirshi001.billions.gamepieces.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.gamepieces.entities.BoxGameEntity;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;

public class Totem extends ItemEntity {

    public static final Texture t = new Texture("rpg-pack/props_n_decorations/generic-rpg-loot01.png");

    static{
        Registry.addDisposable(t);
    }

    public Totem(Vector2 position) {
        super(position);
    }

    @Override
    public void onDropped(BoxGameEntity e) {

    }

    @Override
    public void onPicked(BoxGameEntity e) {

    }

    @Override
    public float getWidth() {
        return (float)t.getWidth()/Block.BLOCKWIDTH;
    }

    @Override
    public float getHeight() {
        return (float)t.getHeight()/Block.BLOCKHEIGHT;
    }

    @Override
    public void drawItem(SpriteBatch batch) {
        batch.draw(t,getPosition().x* Block.BLOCKWIDTH, getPosition().y*Block.BLOCKHEIGHT);
    }

    @Override
    public void updateBoxEntity() {

    }
}
