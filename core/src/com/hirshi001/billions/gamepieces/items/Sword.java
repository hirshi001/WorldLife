package com.hirshi001.billions.gamepieces.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.gamepieces.entities.BoxGameEntity;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;

public class Sword extends ItemEntity {

    public static final Texture t = new Texture("rpg-pack/props_n_decorations/generic-rpg-loot01.png");
    private float bounceHeight = 0;
    private float bounceHeightLim = 5;
    private boolean bouncingUp = true;

    static{
        Registry.addDisposable(t);
    }

    public Sword(Vector2 position) {
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

        batch.draw(t,getPosition().x* Block.BLOCKWIDTH, getPosition().y*Block.BLOCKHEIGHT+bounceHeight);
    }

    @Override
    public void updateBoxEntity() {
        float d = (float)Math.random()/10;
        if(bouncingUp){
            bounceHeight+=0.2+d;
            if(bounceHeight>=bounceHeightLim){
                bouncingUp=false;
            }
        }
        else{
            bounceHeight-=0.2+d;
            if(bounceHeight<=0){
                bouncingUp=true;
            }
        }
    }
}
