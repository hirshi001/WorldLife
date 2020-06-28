package com.hirshi001.billions.gamepieces.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.field.Field;
import com.hirshi001.billions.gamepieces.Positionable;
import com.hirshi001.billions.registry.Registry;
import com.hirshi001.billions.util.tiles.TileIter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class BoxEntity implements Positionable {

    protected final Vector2 position, lastPosition;
    protected Field field;

    public BoxEntity(final Vector2 position){
        this.position = position;
        lastPosition = position.cpy();
    }

    public BoxEntity setField(Field f){this.field = f; return this;}
    public Field getField(){return this.field;}
    public abstract float getWidth();
    public abstract float getHeight();
    public Vector2 getPosition(){return position;}

    @Override
    public Vector2 getLayerPosition() {return position; }

    public Vector2 getLastPosition(){return lastPosition;}
    public Vector2 getCenterPosition(){return getPosition().cpy().add(getWidth()/2f, getHeight()/2f);}
    public Vector2 getCenterPosition(Vector2 v){return v.cpy().add(getWidth()/2f, getHeight()/2f);}

    @Override
    public void draw(Vector2 bottomLeft, Vector2 topRight, SpriteBatch batch) {
        if(shouldDraw(bottomLeft, topRight)){
            drawEntity(batch);
        }
    }
    public abstract void drawEntity(SpriteBatch batch);
    public boolean shouldDraw(Vector2 bottomLeft, Vector2 topRight){
        return !(getPosition().x+getWidth()<bottomLeft.x || getPosition().x>topRight.x || getPosition().y+getHeight()<bottomLeft.y || getPosition().y>topRight.y);
    }

    public void updateBoxEntity(){
        lastPosition.set(position);
        update();
    }
    protected abstract void update();

    //Currently does not use TileIter. May change later
    public void tileCollision(){

        int[][] tiles =getField().getTiles();
        Vector2 dir = getPosition().cpy().sub(getLastPosition());
        //System.out.println(dir);
        //check right side of tile

        Vector2 centerPosition = getCenterPosition();
        Vector2 topLeft = new Vector2(centerPosition).sub(getWidth() / 2, getHeight() / 2);
        Vector2 bottomRight = new Vector2(centerPosition).add(getWidth()/2, getHeight()/2);
        int startX, endX, dx;
        if(dir.x>0) {
            startX = (int) topLeft.x;
            endX = (int) bottomRight.x;
            dx = 1;
        }
        else{
            endX = (int) topLeft.x;
            startX = (int) bottomRight.x;
            dx = -1;
        }

        int startY, endY, dy;
        if(dir.y>0) {
            startY = (int) topLeft.y;
            endY = (int) bottomRight.y;
            dy = 1;
        }
        else{
            endY = (int) topLeft.y;
            startY = (int) bottomRight.y;
            dy = -1;
        }
        float slope, b;
        //System.out.println(startX + " : " + endX + " :: "+startY + " : " + endY + " | dx : "+dx+" - dy : "+ dy);

        for(int i=startX;dir.x>0?i<=endX:i>=endX;i+=dx){
            for(int j=startY;dir.y>0?j<=endY:j>=endY;j+=dy){
                if (Registry.getBlock(tiles[j][i]).isCollidable() || field.getStructureTiles()[j][i]==1) {
                    if (!touchingBox(new Vector2(i, j), 1, 1, false)) continue;
                    if(dir.x!=0 && dir.y!=0) {
                        slope = dir.y / dir.x;
                        if (dir.x < 0 && dir.y < 0) {
                            b=getPosition().y-slope*getPosition().x;
                            float intX = (j+1-b)/slope;

                            if(intX>i+1) getPosition().x=i+1;
                            else getPosition().y=j+1;

                        }else if (dir.x < 0 && dir.y > 0) {
                            b=getPosition().y-slope*(getPosition().x)+getHeight();
                            float intX = (j-b)/slope;

                            if(intX>i+1) getPosition().x=i+1;
                            else getPosition().y=j-getHeight();

                        } else if (dir.x > 0 && dir.y < 0) {
                            b=getPosition().y-slope*(getPosition().x+getWidth());
                            float intX = (j+1-b)/slope;

                            if(intX<i) getPosition().x=i-getWidth();
                            else getPosition().y=j+1;

                        } else if (dir.x > 0 && dir.y > 0) {
                            b=getPosition().y-slope*(getPosition().x+getHeight())+getHeight();
                            float intX = (j-b)/slope;

                            if(intX<i) getPosition().x=i-getWidth();
                            else getPosition().y=j-getHeight();

                        }
                    }
                    else{
                        if(dir.x==0){
                            if(dir.y>0){
                                getPosition().y=j-getHeight();
                            }
                            else{
                                getPosition().y=j+1;
                            }
                        }
                        else {
                            if(dir.x>0){
                                getPosition().x=i-getWidth();
                            }
                            else{
                                getPosition().x=i+1;
                            }
                        }
                    }
                }
            }
        }

    }

    public void mobCollision(List<BoxEntity> mobs){
        for(BoxEntity e:mobs){
            if(touchingEntity(e)){
                if(e==this) continue;
                onMobCollision(e);
            }
        }
    }
    public boolean touchingEntity(BoxEntity e){
        return touchingBox(e.getPosition(),e.getWidth(), e.getHeight());
    }

    private boolean touchingBox(Vector2 pos, float width, float height){
        return oneDimensionOverlap(pos.x, pos.x+width,getPosition().x, getPosition().x+getWidth(),false)
                && oneDimensionOverlap(pos.y, pos.y+height,getPosition().y, getPosition().y+getHeight(),false);
    }

    private boolean touchingBox(Vector2 pos, float width, float height, boolean checkEdges){
        return oneDimensionOverlap(pos.x, pos.x+width,getPosition().x, getPosition().x+getWidth(),checkEdges)
                && oneDimensionOverlap(pos.y, pos.y+height,getPosition().y, getPosition().y+getHeight(),checkEdges);
    }

    protected void onMobCollision(BoxEntity e) {
        Random r = new Random();
        if(e.getCenterPosition().equals(getCenterPosition())){
            getLastPosition().set(getPosition());
            getPosition().add( (r.nextBoolean()?-1:1)*0.00001f,(r.nextBoolean()?-1:1)*0.00001f);
            tileCollision();
        }

        Vector2 mov = new Vector2(e.getCenterPosition().x - getCenterPosition().x, e.getCenterPosition().y-getCenterPosition().y);
        mov.nor().scl(0.01f);

        int i=0;
        while (touchingEntity(e)) {
            if (i > 20){
                break;
            }
            if(mov.len2()>1) mov.nor().scl(1);
            getLastPosition().set(getPosition());
            getPosition().sub(mov);
            i++;
            tileCollision();
        }
    }

    private static boolean oneDimensionOverlap(double x1, double x2, double y1, double y2, boolean checkEdges){
        double temp;
        if(x1>x2){
            temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if(y1>y2){
            temp = y1;
            y1 = y2;
            y2 = temp;
        }
        if(checkEdges) return x2>=y1 && y2>=x1;
        return  x2>y1 && y2>x1;
    }

}
