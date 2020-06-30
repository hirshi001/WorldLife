package com.hirshi001.billions.registry;

import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Registry {

    public static final Block
    GRASS = new Block((short)0).setTexture("generic-rpg-tile01.png").collidable(false),
    WALL = new Block((short)1).setTexture("generic-rpg-tile02.png").collidable(true);

    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<Disposable> TODISPOSE = new LinkedList<>();

    static{
        BLOCKS.addAll(Arrays.asList(GRASS,WALL));
    }

    public static void dispose(){
        synchronized (BLOCKS) {
            for (Block b : BLOCKS) {
                b.dispose();
            }
        }
        synchronized (TODISPOSE) {
            for (Disposable d:TODISPOSE) {
                d.dispose();
            }
        }
    }

    public static Block getBlock(int id){
        synchronized (BLOCKS){
            return BLOCKS.get(id);
        }
    }

    public static void addDisposable(Disposable d){
        synchronized (TODISPOSE){
            TODISPOSE.add(d);
        }
    }



}
