package com.hirshi001.billions.gamepieces.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.gamepieces.entities.BoxEntity;

public abstract class ItemEntity extends BoxEntity {
    public ItemEntity(Vector2 position) {
        super(position);
    }

    public abstract void onDropped(BoxEntity e);
    public abstract void onPicked(BoxEntity e);

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    @Override
    public void drawEntity(SpriteBatch batch) {

    }

    @Override
    protected void update() {

    }
}
