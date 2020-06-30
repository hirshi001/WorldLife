package com.hirshi001.billions.gamepieces.projecticles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.gamepieces.BoxEntity;
import com.hirshi001.billions.gamepieces.entities.GameMob;
import com.hirshi001.billions.gamepieces.structures.GameStructure;

import java.util.List;

public abstract class GameProjectile extends BoxEntity {

    protected Object source = null;

    public GameProjectile(Vector2 position) {
        super(position);
    }

    @Override
    public void draw(Vector2 bottomLeft, Vector2 topRight, SpriteBatch b) {
        if(shouldDraw(bottomLeft, topRight)){
            drawProjectile(b);
        }
    }

    public abstract void drawProjectile(SpriteBatch batch);

    public boolean shouldDraw(Vector2 bottomLeft, Vector2 topRight){
        return !(getPosition().x+getWidth()<bottomLeft.x || getPosition().x>topRight.x || getPosition().y+getHeight()<bottomLeft.y || getPosition().y>topRight.y);
    }

    public GameProjectile source(Object o){
        this.source = o;
        return this;
    }

    public Object getSource(){
        return source;
    }

    public void touchingMob(List<GameMob> mobs){
        for(GameMob m:mobs){
            if(touchingBox(m.getPosition(),m.getWidth(), m.getHeight())){
                onTouchingMob(m);
            }
        }
    }

    @Override
    public GameProjectile shiftByCenter() {
        return (GameProjectile)super.shiftByCenter();
    }

    public void touchingStructure(List<GameStructure> structures){

    }

    public abstract void onTouchingMob(GameMob m);
}
