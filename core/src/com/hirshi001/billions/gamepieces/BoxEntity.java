package com.hirshi001.billions.gamepieces;

import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.field.Field;

public abstract class BoxEntity implements Positionable {

    protected Vector2 position;
    protected Field field;

    public BoxEntity(Vector2 position){
        this.position = position;
    }
    public BoxEntity(Vector2 position, boolean isCenter){
        this.position = position.sub(getWidth()/2, getHeight()/2);
    }

    @Override
    public Vector2 getLayerPosition() {return position; }

    public BoxEntity setField(Field f){this.field = f; return this;}
    public Field getField(){return this.field;}
    public Vector2 getPosition(){return position;}

    public BoxEntity shiftByCenter(){
        getPosition().sub(getWidth()/2, getHeight()/2);
        return this;
    }

    public abstract void updateBoxEntity();

    public abstract float getWidth();
    public abstract float getHeight();

    public final boolean touchingBox(Vector2 pos, float width, float height){
        return oneDimensionOverlap(pos.x, pos.x+width,getPosition().x, getPosition().x+getWidth(),false)
                && oneDimensionOverlap(pos.y, pos.y+height,getPosition().y, getPosition().y+getHeight(),false);
    }

    public final boolean touchingBox(Vector2 pos, float width, float height, boolean checkEdges){
        return oneDimensionOverlap(pos.x, pos.x+width,getPosition().x, getPosition().x+getWidth(),checkEdges)
                && oneDimensionOverlap(pos.y, pos.y+height,getPosition().y, getPosition().y+getHeight(),checkEdges);
    }

    public static boolean oneDimensionOverlap(double x1, double x2, double y1, double y2, boolean checkEdges){
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
