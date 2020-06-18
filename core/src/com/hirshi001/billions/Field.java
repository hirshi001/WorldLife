package com.hirshi001.billions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.billions.gamepieces.PositionComparator;
import com.hirshi001.billions.gamepieces.Positionable;
import com.hirshi001.billions.gamepieces.entities.BoxEntity;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;
import com.hirshi001.billions.gamepieces.structures.Structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Field implements Disposable {

    private int rows, cols;

    private int[][] tiles, structureTiles;

    private final List<BoxEntity> mobs = new LinkedList<>();
    private final Queue<BoxEntity> mobsRemove = new LinkedList<>();
    private final Queue<BoxEntity> mobsAdd = new LinkedList<>();


    private final List<Structure> structures = new LinkedList<>();
    private final Queue<Structure> structuresRemove = new LinkedList<>();
    private final Queue<Structure> structuresAdd = new LinkedList<>();

    private final ArrayList<Positionable> positionables = new ArrayList<>();

    private final PositionComparator positionComparator = new PositionComparator();


    private BoxEntity mainPlayer;
    private OrthographicCamera camera;


    public Field(int rows, int cols, OrthographicCamera camera){
        this.rows = rows;
        this.cols = cols;
        this.camera = camera;
        tiles = new int[rows][cols];
        structureTiles = new int[rows][cols];
        int i, j;
        for(i=0;i<rows;i++){
            for(j=0;j<cols;j++){
                if(i==0 || j==0 || i==rows-1 || j == cols-1 || Math.random()>0.90)tiles[i][j] = 1;
                else tiles[i][j] = 0;
            }
        }
    }

    public List<BoxEntity> getMobsList(){return mobs;}
    public BoxEntity getMainPlayer(){return mainPlayer;}
    public void setMainPlayer(BoxEntity m){
        mainPlayer = m;
    }


    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }

    public OrthographicCamera getCamera(){return camera;}

    public int[][] getTiles(){return tiles;}
    public int[][] getStructureTiles(){return structureTiles;}

    private boolean safeChange(int row, int col, int val){
        if(row>=0 && row<rows && col>=0 && col<cols){
            tiles[row][col]=val;
            return true;
        }
        return false;
    }

    public void update(){
        for(BoxEntity m:mobs){
            m.updateBoxEntity();
        }
        for(BoxEntity m:mobs){
            m.tileCollision();
        }
        for(BoxEntity m:mobs){
            m.mobCollision(mobs);
        }

        for(Structure s:structures){
            s.update();
        }

        int change = 0;

        BoxEntity e;
        synchronized (mobsAdd) {
            change+=mobsAdd.size();
            while (!mobsAdd.isEmpty()) {
                e = mobsAdd.remove();
                mobs.add(e);
                positionables.add(e);
            }
        }
        synchronized (mobsRemove) {
            change+=mobsRemove.size();
            while (!mobsRemove.isEmpty()) {
                e = mobsRemove.remove();
                mobs.remove(e);
                positionables.remove(e);
            }
        }
        Structure s;
        synchronized (structuresAdd){
            Vector2 pos;
            boolean cont;
            change+=structuresAdd.size();
            while(!structuresAdd.isEmpty()){
                cont = false;
                s = structuresAdd.remove();
                pos = s.getPosition();
                int[][] t = s.getTiles();
                int i, j;
                for(i=0;i<t.length;i++){
                    for(j=0;j<t[i].length;j++){
                        if(structureTiles[(int)pos.y+i][(int)pos.x+j]!=0 || Registry.getBlock(tiles[(int)pos.y+i][(int)pos.x+j]).isCollidable()){
                            cont = true;
                            break;
                        }
                    }
                    if(cont) break;
                }
                if(cont) continue;

                for(i=0;i<t.length;i++){
                    for(j=0;j<t[i].length;j++){
                        structureTiles[(int)pos.y+i][(int)pos.x+j] += t[i][j];
                    }
                }
                structures.add(s);
                positionables.add(s);
            }
        }
        synchronized (structuresRemove){
            Vector2 pos;
            change+=structuresRemove.size();
            while(!structuresRemove.isEmpty()){
                s = structuresRemove.remove();
                pos = s.getPosition();
                int[][] t = s.getTiles();
                int i,j ;
                for(i=0;i<t.length;i++){
                    for(j=0;j<t[i].length;j++){
                        if(t[i][j]!=0) structureTiles[(int)pos.y+i][(int)pos.x+j] = 0;
                    }
                }
                structures.remove(s);
                positionables.remove(s);
            }
        }
        if(change>=50) {
            Collections.sort(positionables, positionComparator);
        }
        else{
            bubbleSort(positionables, positionComparator);
        }

    }

    private <T> void bubbleSort(ArrayList<T> e, Comparator<? super T> c){
        boolean change = true;
        int j = 0;
        T temp;
        while(change){
            change = false;
            for(int i=0;i<e.size()-1;i++){
                if(c.compare(e.get(i), e.get(i+1))>0){
                    j++;
                    temp = e.get(i);

                    e.set(i,e.get(i+1));
                    e.set(i+1,temp);
                    change = true;
                }
                if(j>1000){
                    Collections.sort(positionables, positionComparator);
                }
            }
        }
    }

    public void removeMob(BoxEntity m){
        synchronized (mobsRemove){
            mobsRemove.add(m);
        }
    }

    public void addMob(BoxEntity m){
        synchronized (mobsAdd){
            mobsAdd.add(m);
            m.setField(this);
        }
    }

    public void addStructure(Structure s){
        synchronized (structuresAdd){
            structuresAdd.add(s);
        }
    }

    public void removeStructure(Structure s){
        synchronized (structuresRemove){
            structuresRemove.add(s);
        }
    }

    public void draw(SpriteBatch batch){
        Vector3 topLeft = new Vector3(0,0,0), bottomRight = new Vector3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),0);
        camera.unproject(topLeft);
        camera.unproject(bottomRight);

        topLeft.scl(1f/Block.BLOCKWIDTH,1f/Block.BLOCKHEIGHT,1);
        if(topLeft.x<0) topLeft.x=0;
        if(topLeft.y<0) topLeft.y=0;
        if(topLeft.x>cols-1) topLeft.x=cols-1;
        if(topLeft.y>rows-1) topLeft.y=rows-1;
        bottomRight.scl(1f/Block.BLOCKWIDTH,1f/Block.BLOCKHEIGHT,1);
        if(bottomRight.x<0) bottomRight.x=0;
        if(bottomRight.y<0) bottomRight.y=0;
        if(bottomRight.x>cols-1) bottomRight.x=cols-1;
        if(bottomRight.y>rows-1) bottomRight.y=rows-1;

        int i, j;
        for(i=(int)Math.floor(bottomRight.y);i<(int)Math.floor(topLeft.y)+1;i++){
            for(j=(int)Math.floor(topLeft.x);j<(int)Math.floor(bottomRight.x)+1;j++){
                batch.draw(Registry.getBlock(tiles[i][j]).getTexture(), Block.BLOCKWIDTH *j, Block.BLOCKHEIGHT*i);
            }
        }

        for(Positionable p:positionables){
            p.draw(batch);
        }
    }

    @Override
    public void dispose() {
        //t.dispose();
    }
}
