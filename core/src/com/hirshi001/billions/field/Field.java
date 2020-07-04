package com.hirshi001.billions.field;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.billions.game.Game;
import com.hirshi001.billions.gamepieces.PositionComparator;
import com.hirshi001.billions.gamepieces.Positionable;
import com.hirshi001.billions.gamepieces.entities.GameMob;
import com.hirshi001.billions.gamepieces.items.ItemEntity;
import com.hirshi001.billions.gamepieces.projecticles.GameProjectile;
import com.hirshi001.billions.gamepieces.structures.StructureTile;
import com.hirshi001.billions.registry.Block;
import com.hirshi001.billions.registry.Registry;
import com.hirshi001.billions.gamepieces.structures.GameStructure;
import com.hirshi001.billions.util.tiles.TileIter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Field implements Disposable {

    private int rows, cols;

    private short[][] tiles;
    private StructureTile[][] structureTiles;

    private final List<GameMob> mobs = new LinkedList<>();
    private final Queue<GameMob> mobsRemove = new LinkedList<>();
    private final Queue<GameMob> mobsAdd = new LinkedList<>();

    private final List<GameProjectile> projectiles = new LinkedList<>();
    private final Queue<GameProjectile> projectilesRemove = new LinkedList<>();
    private final Queue<GameProjectile> projectilesAdd = new LinkedList<>();


    private final List<ItemEntity> items = new LinkedList<>();
    private final Queue<ItemEntity> itemsRemove = new LinkedList<>();
    private final Queue<ItemEntity> itemsAdd = new LinkedList<>();

    private final List<GameStructure> structures = new LinkedList<>();
    private final Queue<GameStructure> structuresRemove = new LinkedList<>();
    private final Queue<GameStructure> structuresAdd = new LinkedList<>();

    private final ArrayList<Positionable> positionables = new ArrayList<>(3000);

    private final PositionComparator positionComparator = new PositionComparator();

    private Game game;
    private GameMob mainPlayer;


    public Field(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        tiles = new short[rows][cols];
        structureTiles = new StructureTile[rows][cols];
        int i, j;
        for(i=0;i<rows;i++){
            for(j=0;j<cols;j++){
                tiles[i][j] = 0;
                structureTiles[i][j] = new StructureTile();
            }
        }
    }

    public List<GameMob> getMobsList(){return mobs;}
    public GameMob getMainPlayer(){return mainPlayer;}
    public void setMainPlayer(GameMob m){
        mainPlayer = m;
    }

    public Game getGame(){return this.game;}
    public Field setGame(Game game){this.game = game; return this;}

    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }


    public Field setTiles(short[][] tiles){
        this.tiles = tiles;
        return this;
    }
    public short[][] getTiles(){return tiles;}
    public StructureTile[][] getStructureTiles(){return structureTiles;}

    private boolean safeChange(int row, int col, short val){
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
        updateMobs();
        updateProjectiles();
        for(GameStructure s:structures){ s.update(); }
        for(ItemEntity e:items){ e.updateBoxEntity(); }


        int change = 0;

        change+=handleMobs();
        change+=handleProjectiles();
        change+=handleItems();
        change+=handleStructures();

        if(true) {
            Collections.sort(positionables, positionComparator);
        }



    }

    private void updateMobs(){
        for(GameMob m:mobs){ m.updateBoxEntity(); }
        for(GameMob m:mobs){ m.tileCollision(); }
        for(GameMob m:mobs){ m.mobCollision(mobs); }
        for(GameMob m:mobs){ m.itemTouching(items); }
    }
    private void updateProjectiles(){
        for(GameProjectile p:projectiles){p.updateBoxEntity();}
        for(GameProjectile p:projectiles){p.touchingMob(mobs);}
    }

    private int handleMobs(){
        int change = 0;
        GameMob e;
        change+=mobsAdd.size();
        while (!mobsAdd.isEmpty()) {
            e = mobsAdd.remove();
            mobs.add(e);
            positionables.add(e);
            e.setField(this);
        }


        change+=mobsRemove.size();
        while (!mobsRemove.isEmpty()) {
            e = mobsRemove.remove();
            mobs.remove(e);
            positionables.remove(e);
        }
        return change;
    }
    private int handleProjectiles(){
        int change = 0;
        GameProjectile p;
        change+=projectilesAdd.size();
        while (!projectilesAdd.isEmpty()) {
            p = projectilesAdd.remove();
            projectiles.add(p);
            positionables.add(p);
            p.setField(this);
        }


        change+=projectilesRemove.size();
        while (!projectilesRemove.isEmpty()) {
            p = projectilesRemove.remove();
            projectiles.remove(p);
            positionables.remove(p);
        }
        return change;
    }
    private int handleItems(){
        int change = 0;
        ItemEntity i;
        change+=itemsAdd.size();
        while (!itemsAdd.isEmpty()) {
            i = itemsAdd.remove();
            items.add(i);
            positionables.add(i);
            i.setField(this);
        }

        change+=itemsRemove.size();
        while (!itemsRemove.isEmpty()) {
            i = itemsRemove.remove();
            items.remove(i);
            positionables.remove(i);
        }
        return change;
    }
    private int handleStructures(){
        int change = 0;
        GameStructure s;
        Vector2 pos;
        boolean cont;
        change+=structuresAdd.size();
        while(!structuresAdd.isEmpty()){

            //Check if Structure can actually be put in that location. If it can't, then don't put it.
            cont = false;
            s = structuresAdd.remove();
            pos = s.getPosition();
            StructureTile[][] t = s.getTiles();
            int i, j;
            for(i=0;i<t.length;i++){
                for(j=0;j<t[i].length;j++){
                    if(!((int)pos.y+i>=0 && (int)pos.y+i<getRows() && (int)pos.x+j>=0 && (int)pos.x+j<=getCols()) || structureTiles[(int)pos.y+i][(int)pos.x+j].isStructure() || Registry.getBlock(tiles[(int)pos.y+i][(int)pos.x+j]).isCollidable()){
                        cont = true;
                        break;
                    }
                }
                if(cont) break;
            }
            if(cont) continue;

            for(i=0;i<t.length;i++){
                for(j=0;j<t[i].length;j++){
                    structureTiles[(int)pos.y+i][(int)pos.x+j].set(t[i][j]);
                }
            }
            structures.add(s);
            s.setField(this);
            positionables.add(s);
        }

        change+=structuresRemove.size();
        while(!structuresRemove.isEmpty()){
            s = structuresRemove.remove();
            pos = s.getPosition();
            StructureTile[][] t = s.getTiles();
            int i,j ;
            for(i=0;i<t.length;i++){
                for(j=0;j<t[i].length;j++){
                    if(t[i][j].isStructure()) structureTiles[(int)pos.y+i][(int)pos.x+j]=new StructureTile();
                }
            }
            structures.remove(s);
            positionables.remove(s);

        }
        return change;
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

    public void removeMob(GameMob m){
        mobsRemove.add(m);
    }
    public void addMob(GameMob m){
        mobsAdd.add(m);
        m.setField(this);
    }

    public void addProjectile(GameProjectile p){
        projectilesAdd.add(p);
    }
    public void removeProjectile(GameProjectile p){projectilesRemove.add(p);}

    public void removeItem(ItemEntity i){
        itemsRemove.add(i);
    }
    public void addItem(ItemEntity i){
        itemsAdd.add(i);
        i.setField(this);

    }

    public void addStructure(GameStructure s){
        structuresAdd.add(s);
    }
    public void removeStructure(GameStructure s){
        structuresRemove.add(s);
    }

    public void draw(SpriteBatch batch){
        Vector3 bottomLeft = new Vector3(0, Gdx.graphics.getHeight(),0), topRight = new Vector3(Gdx.graphics.getWidth(),0,0);
        OrthographicCamera camera = getGame().getGameApplicationAdapter().getCamera();
        camera.unproject(bottomLeft).scl(1f/Block.BLOCKWIDTH,1f/Block.BLOCKHEIGHT,1);
        camera.unproject(topRight).scl(1f/Block.BLOCKWIDTH,1f/Block.BLOCKHEIGHT,1);

        TileIter tileIter = TileIter.createTileIterRelativeTo(getTiles(),bottomLeft.x, bottomLeft.y, topRight.x, topRight.y);
        int[][] newTiles = tileIter.tiles;
        for(int i=0;i<newTiles.length;i++){
            for(int j=0;j<newTiles[i].length;j++){
                batch.draw(Registry.getBlock(newTiles[i][j]).getTexture(),(j+tileIter.startX)*Block.BLOCKWIDTH, (i+tileIter.startY)*Block.BLOCKHEIGHT);
            }
        }

        Vector2 bl = new Vector2(bottomLeft.x, bottomLeft.y).sub(1,1), tr = new Vector2(topRight.x, topRight.y).add(1,1);
        for(Positionable p:positionables){
            p.draw(bl, tr, batch);
        }
    }


    @Override
    public void dispose() {
        //t.dispose();
    }
}
