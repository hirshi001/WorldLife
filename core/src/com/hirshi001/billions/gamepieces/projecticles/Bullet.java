package com.hirshi001.billions.gamepieces.projecticles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.gamepieces.entities.GameMob;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;

public class Bullet extends GameProjectile {

    public static final Texture t = new Texture("textures/entities/projectiles/bullet/bullet.png");
    private Vector2 angle;
    private float speed = 0.5f;
    private int lifeSpan = 200;
    private int life = 0;
    static{
        Registry.addDisposable(t);
    }

    public Bullet(Vector2 position, Vector2 dir) {
        super(position);
        this.angle = dir.nor().scl(speed);
    }

    @Override
    public void updateBoxEntity() {
        getPosition().add(angle);
        life++;
        if(life>lifeSpan){
            getField().removeProjectile(this);
        }
    }

    @Override
    public void drawProjectile(SpriteBatch batch) {
        batch.draw(t,getPosition().x*Block.BLOCKWIDTH, getPosition().y*Block.BLOCKHEIGHT);
        batch.draw(t,getPosition().x*Block.BLOCKWIDTH, getPosition().y*Block.BLOCKHEIGHT, t.getWidth()/2, t.getHeight()/2, t.getWidth(), t.getHeight(), 1, 1, angle.angle()+180,0,0,t.getWidth(), t.getHeight(),false,false);
    }

    @Override
    public void onTouchingMob(GameMob m) {
        if(m==getSource()) return;
        m.applyDamage(5, this);
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
