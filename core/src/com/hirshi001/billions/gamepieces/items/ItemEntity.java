package com.hirshi001.billions.gamepieces.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.gamepieces.BoxEntity;
import com.hirshi001.billions.gamepieces.entities.BoxGameEntity;

public abstract class ItemEntity extends BoxEntity {
    public ItemEntity(Vector2 position) {
        super(position);
    }

    public abstract void onDropped(BoxGameEntity e);
    public abstract void onPicked(BoxGameEntity e);

    @Override
    public void draw(Vector2 bottomLeft, Vector2 topRight, SpriteBatch b){
        if(shouldDraw(bottomLeft, topRight)){
            drawItem(b);
        }
    }

    public boolean shouldDraw(Vector2 bottomLeft, Vector2 topRight){
        return !(getPosition().x+getWidth()<bottomLeft.x || getPosition().x>topRight.x || getPosition().y+getHeight()<bottomLeft.y || getPosition().y>topRight.y);
    }

    public abstract void drawItem(SpriteBatch batch);
}
