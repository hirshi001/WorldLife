package com.hirshi001.billions.gamepieces.projecticles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.gamepieces.entities.GameMob;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;

public class Fireball extends GameProjectile {

    public static final Texture t = new Texture("textures/entities/projectiles/fireball/fireball.png");
    private Vector2 angle;
    private float speed = 0.3f;
    static{
        Registry.addDisposable(t);
    }

    public Fireball(Vector2 position, Vector2 dir) {
        super(position);
        this.angle = dir.nor().scl(speed);
    }

    @Override
    public void updateBoxEntity() {
        getPosition().add(angle);
    }

    @Override
    public void drawProjectile(SpriteBatch batch) {
        batch.draw(t,getPosition().x*Block.BLOCKWIDTH, getPosition().y*Block.BLOCKHEIGHT);
    }

    @Override
    public void onTouchingMob(GameMob m) {
        getPosition().sub(angle);
        //angle.rotate((int)(Math.random()*360));
        angle.set(getCenterPosition().sub(m.getCenterPosition())).nor().scl(speed);
        getPosition().add(angle);
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
