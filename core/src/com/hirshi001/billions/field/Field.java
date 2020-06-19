package com.hirshi001.billions.field;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.billions.Game;
import com.hirshi001.billions.game.GameApplication;
import com.hirshi001.billions.util.camera.CameraStyles;
import com.hirshi001.billions.gamepieces.PositionComparator;
import com.hirshi001.billions.gamepieces.Positionable;
import com.hirshi001.billions.gamepieces.entities.BoxEntity;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;
import com.hirshi001.billions.gamepieces.structures.Structure;
import com.hirshi001.billions.util.tiles.TileIter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private GameApplication game;


    public Field(int rows, int cols, GameApplication game){
        this.rows = rows;
        this.cols = cols;
        this.game = game;
        tiles = new int[rows][cols];
        structureTiles = new int[rows][cols];
        int i, j;
        for(i=0;i<rows;i++){
            for(j=0;j<cols;j++){
                tiles[i][j] = 0;
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

    public GameApplication getGameApplication(){return game;}

    public Field setTiles(int[][] tiles){
        this.tiles = tiles;
        return this;
    }
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
        updateEntities();
    }

    private void updateEntities(){
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
                        if(!((int)pos.y+i>=0 && (int)pos.y+i<getRows() && (int)pos.x+j>=0 && (int)pos.x+j<=getCols()) || structureTiles[(int)pos.y+i][(int)pos.x+j]!=0 || Registry.getBlock(tiles[(int)pos.y+i][(int)pos.x+j]).isCollidable()){
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
        Vector3 bottomLeft = new Vector3(0, Gdx.graphics.getHeight(),0), topRight = new Vector3(Gdx.graphics.getWidth(),0,0);
        getGameApplication().getGame().getCamera().unproject(bottomLeft).scl(1f/Block.BLOCKWIDTH,1f/Block.BLOCKHEIGHT,1);
        getGameApplication().getGame().getCamera().unproject(topRight).scl(1f/Block.BLOCKWIDTH,1f/Block.BLOCKHEIGHT,1);

        //System.exit(-1);
        TileIter tileIter = TileIter.createTileIterRelativeTo(getTiles(),bottomLeft.x, bottomLeft.y, topRight.x, topRight.y);
        int[][] newTiles = tileIter.tiles;
        for(int i=0;i<newTiles.length;i++){
            for(int j=0;j<newTiles[i].length;j++){
                batch.draw(Registry.getBlock(newTiles[i][j]).getTexture(),(j+tileIter.startX)*Block.BLOCKWIDTH, (i+tileIter.startY)*Block.BLOCKHEIGHT);
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
