package com.hirshi001.billions.gamepieces.structures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.field.Field;
import com.hirshi001.billions.gamepieces.Positionable;

public abstract class Structure implements Positionable {

    protected Field field;
    protected Vector2 position;
    public Structure(Vector2 position){
        this.position = position;
    }

    public Structure setField(Field field){this.field = field; return this;}
    public abstract void update();
    public Vector2 getPosition(){return position;}
    public abstract int[][] getTiles();

    /** @return the width of the texture drawn relative to the block scale. */
    public abstract float getWidth();
    /** @return the height of the texture drawn relative to the block scale. */
    public abstract float getHeight();

    @Override
    public void draw(Vector2 bottomLeft, Vector2 topRight, SpriteBatch batch) {
        if(shouldDraw(bottomLeft, topRight)){
            drawStructure(batch);
        }
    }
    public boolean shouldDraw(Vector2 bottomLeft, Vector2 topRight){
        float width = getWidth(), height = getHeight();
        return !(getPosition().x+width<bottomLeft.x || getPosition().x>topRight.x || getPosition().y+height<bottomLeft.y || getPosition().y>topRight.y);
    }
    public abstract void drawStructure(SpriteBatch batch);

    public static int[][] vFlip(int[][] tiles){
        int[][] newT = new int[tiles.length][longestRow(tiles)];
        for(int i=0;i<newT.length;i++){
            for(int j=0;j<newT[i].length;j++){
                newT[i][j] = tiles[newT.length-1-i][j];
            }
        }
        return newT;
    }

    public static int longestRow(int[][] tiles){
        int max = 0;
        for(int[] t:tiles){
            if(t.length>max){
                max = t.length;
            }
        }
        return max;
    }
}
