package com.hirshi001.billions.gamepieces.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.gamepieces.items.ItemEntity;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;
import com.hirshi001.billions.util.animation.AnimationCycle;
import com.hirshi001.billions.util.animation.Animator;

import java.util.List;

public class Slime extends BoxGameEntity {

    public static final float WIDTH = 16f/Block.BLOCKWIDTH, HEIGHT = 16f/Block.BLOCKHEIGHT;
    private BoxGameEntity master;
    private static Texture t1, t2;

    static{
        t1 = new Texture("textures/entities/mobs/slime/slimeIdle1.png");
        t2 = new Texture("textures/entities/mobs/slime/slimeIdle2.png");
        Registry.addDisposable(t1);
        Registry.addDisposable(t2);
    }

    private AnimationCycle cycle = new AnimationCycle(new Animator(new TextureRegion[]{
            new TextureRegion(t1), new TextureRegion(t2)
    }));;

    private boolean facingRight = true;
    private int count = 0;

    public Slime(Vector2 position, BoxGameEntity master) {
        super(position);
        this.master = master;
    }


    @Override
    public void itemTouching(List<ItemEntity> items) {
        return;
    }

    public BoxGameEntity getMaster(){return master;}

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public void drawEntity(SpriteBatch batch) {
        TextureRegion t = cycle.get();
        if(facingRight != t.isFlipX()){
            t.flip(true, false);
        }
        batch.draw(t, getPosition().x*Block.BLOCKWIDTH, getPosition().y*Block.BLOCKHEIGHT,t.getRegionWidth()/2f, t.getRegionHeight()/2f, t.getRegionWidth(), t.getRegionHeight(),1f,1f,0f);
    }


    @Override
    public void update() {
       moveToMaster();


        count++;
        if(count>12){
            cycle.cycle();
            count = -(int)(Math.random()*3);
        }
    }

    private void moveToMaster(){
        if(!getMaster().getField().equals(getField())){
            getField().removeMob(this);
            getMaster().getField().addMob(this);
        }

        Vector2 playerPos = master.getCenterPosition();

        float dx = playerPos.x - getCenterPosition().x;
        float dy = playerPos.y - getCenterPosition().y;
        if(!(playerPos.dst2(getCenterPosition())<4)) {
            if (playerPos.dst2(getCenterPosition()) > 30 * 30) {
                getPosition().x = playerPos.x;
                getPosition().y = playerPos.y;
            }
            else {
                Vector2 mov = new Vector2(dx, dy).nor();
                getPosition().add(mov.scl(0.202f));
            }
        }
        facingRight = getCenterPosition().x<getMaster().getCenterPosition().x;
    }

}
