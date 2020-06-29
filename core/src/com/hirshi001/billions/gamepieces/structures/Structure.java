package com.hirshi001.billions.gamepieces.structures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.billions.field.Field;
import com.hirshi001.billions.gamepieces.Positionable;

import java.util.Objects;

public abstract class Structure implements Positionable {

    protected Field field;
    private boolean hasField = false;
    protected Vector2 position;
    protected Field insideField;
    public Structure(Vector2 position){
        this.position = position;
    }

    public Structure setField(Field field){this.field = field; hasField = true; return this;}
    public Field getField(){return field;}
    public Structure setInsideField(Field field){this.insideField = field;return this;}
    public Field getInnerField(){return insideField;}
    public boolean hasInnerField(){return insideField!=null;}
    public boolean hasField(){return hasField;}
    public abstract void update();
    public Vector2 getPosition(){return position;}
    public abstract StructureTile[][] getTiles();
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

    public static Integer[][] vFlip(Integer[][] tiles){
        Integer[][] newT = new Integer[tiles.length][longestRow(tiles)];
        for(int i=0;i<tiles.length;i++){
            for(int j=0;j<tiles[i].length;j++){
                newT[i][j] = tiles[tiles.length-1-i][j];
            }
        }
        return newT;
    }

    public static <E> int longestRow(E[][] tiles){
        int max = 0;
        for(E[] t:tiles){
            if(t.length>max){
                max = t.length;
            }
        }
        return max;
    }

    public static StructureTile[][] convert(Structure s, Integer[][] tiles){
        StructureTile[][] structureTiles = new StructureTile[tiles.length][longestRow(tiles)];
        StructureTile tile;
        for(int i=0;i<structureTiles.length;i++){
            for(int j=0;j<structureTiles[i].length;j++){
                tile = new StructureTile();
                int type = tiles[i][j];
                tile.structure(s);
                if(type<=0){structureTiles[i][j] = tile; continue;}
                switch (type){
                    case 1:
                        tile.collidable(true);
                        break;
                    case 2:
                        tile.isDoor(true);
                        break;
                }
               structureTiles[i][j] = tile;
            }
        }
        return structureTiles;
    }

    public Vector2 entrancePosition(){
        return new Vector2(0,0);
    }
}
