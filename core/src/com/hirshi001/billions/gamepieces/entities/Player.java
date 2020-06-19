package com.hirshi001.billions.gamepieces.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;
import com.hirshi001.billions.util.animation.AnimationCycle;
import com.hirshi001.billions.util.animation.Animator;

public class Player extends BoxEntity {

    public static final float WIDTH = 12f/ Block.BLOCKWIDTH, HEIGHT = 12f/Block.BLOCKHEIGHT;
    private static Texture t1, t2;

    static{
        t1 = new Texture("textures/entities/player/player1.png");
        t2 = new Texture("textures/entities/player/player2.png");
        Registry.addDisposable(t1);
        Registry.addDisposable(t2);
    }

    private AnimationCycle cycle = new AnimationCycle(new Animator(new TextureRegion[]{
            new TextureRegion(t1),new TextureRegion(t2)}));;
    private boolean facingRight = true;

    public Player(Vector2 position){
        super(position);
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion t = cycle.get();
        if(facingRight != t.isFlipX()){
            t.flip(true, false);
        }
        batch.draw(t, (getPosition().x+getWidth()/2)*Block.BLOCKWIDTH-t.getRegionWidth()/2f, (getPosition().y+getHeight()/2)*Block.BLOCKHEIGHT-t.getRegionHeight()/2f,t.getRegionWidth()/2f, t.getRegionHeight()/2f, t.getRegionWidth(), t.getRegionHeight(),1f,1f,0f);

    }

    private int count = 0;

    @Override
    public void update() {
        Vector2 mov = Vector2.Zero.cpy();
        if(Gdx.input.isKeyPressed(Input.Keys.D)) mov.x++;
        if(Gdx.input.isKeyPressed(Input.Keys.A)) mov.x--;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) mov.y++;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) mov.y--;
        mov.nor().scl(0.223f);
        getPosition().add(mov);

        Vector2 centerPos = getCenterPosition();
        Vector3 screenPos = field.getGameApplication().getGame().getCamera().project(new Vector3(centerPos.x*Block.BLOCKWIDTH, centerPos.y*Block.BLOCKHEIGHT, 0));
        facingRight = screenPos.x<Gdx.input.getX();

        count++;
        if(count>10){
            cycle.cycle();
            count = 0;
        }
    }


    @Override
    protected void onMobCollision(BoxEntity e) {
        if(e instanceof Slime){
            Slime s = (Slime)e;
            if(s.getMaster()==this){return;}
        }
        else {
            super.onMobCollision(e);
        }
    }
}
