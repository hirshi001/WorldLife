package com.hirshi001.billions.gamepieces.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.hirshi001.billions.field.Field;
import com.hirshi001.billions.gamepieces.items.ItemEntity;
import com.hirshi001.billions.gamepieces.projecticles.Bullet;
import com.hirshi001.billions.gamepieces.projecticles.GameProjectile;
import com.hirshi001.billions.gamepieces.structures.StructureTile;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;
import com.hirshi001.billions.util.animation.AnimationCycle;
import com.hirshi001.billions.util.animation.Animator;

import java.util.List;

public class Player extends GameMob {

    public static final float WIDTH = 12f/ Block.BLOCKWIDTH, HEIGHT = 12f/Block.BLOCKHEIGHT;
    private static Texture t1, t2;

    private int lastShot = 0;
    private int lastShotLim = 10;

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
    public void drawEntity(SpriteBatch batch) {
        TextureRegion t = cycle.get();
        if(facingRight != t.isFlipX()){
            t.flip(true, false);
        }
        batch.draw(t, (getPosition().x+getWidth()/2)*Block.BLOCKWIDTH-t.getRegionWidth()/2f, (getPosition().y+getHeight()/2)*Block.BLOCKHEIGHT-t.getRegionHeight()/2f,t.getRegionWidth()/2f, t.getRegionHeight()/2f, t.getRegionWidth(), t.getRegionHeight(),1f,1f,0f);
    }

    @Override
    public void onItemTouching(ItemEntity i){ }

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

        OrthographicCamera camera = getField().getGame().getGameApplicationAdapter().getCamera();

        Vector2 centerPos = getCenterPosition();
        Vector3 screenPos = camera.project(new Vector3(centerPos.x*Block.BLOCKWIDTH, centerPos.y*Block.BLOCKHEIGHT, 0));
        facingRight = screenPos.x<Gdx.input.getX();

        count++;
        if(count>10){
            cycle.cycle();
            count = 0;
        }

        lastShot++;
        if(lastShot>lastShotLim){
            lastShot=lastShotLim;
        }
        if(lastShot==lastShotLim && Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            lastShot = 0;
            Vector3 dir3 = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0));
            Vector2 dir = getCenterPosition().scl(Block.BLOCKWIDTH, Block.BLOCKHEIGHT).sub(dir3.x, dir3.y).rotate(180+(int)(Math.random()*20)-10);

            getField().addProjectile(new Bullet(getCenterPosition().add(dir.nor().scl(1.15f)),dir).shiftByCenter().source(this));

        }
    }

    @Override
    public GameMob applyDamage(int damage, GameProjectile source) {
        return this;
    }

    @Override
    public boolean onTouchingDoor(StructureTile tile) {
        if(!tile.isDoor()) return false;
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            if(tile.getStructure().hasInnerField()){
                Field currentField = getField();
                currentField.removeMob(this);
                Field newField = tile.getStructure().getInnerField();
                newField.addMob(this);
                newField.setMainPlayer(this);
                currentField.getGame().setField(newField);
                getPosition().set(tile.getStructure().entrancePosition());
                getField().getGame().getInputHandler().getScreenMover().setIsCameraFollow(true);


                List<GameMob> mobs = currentField.getMobsList();
                for(GameMob m:mobs){
                    if(m instanceof Slime){
                        if(((Slime) m).getMaster()==this){
                            currentField.removeMob(m);
                            newField.addMob(m);
                            m.getPosition().set(tile.getStructure().entrancePosition());
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onMobCollision(GameMob e) {
        if(e instanceof Slime){
            Slime s = (Slime)e;
            if(s.getMaster()==this){return;}
        }
        else {
            super.onMobCollision(e);
        }
    }
}
