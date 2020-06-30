package com.hirshi001.billions.gamepieces.structures;

public class StructureTile {

    private boolean isStructure;
    private GameStructure structure;

    private boolean isCollidable;
    private boolean isDoor;

    public StructureTile() {

    }

    public StructureTile structure(GameStructure s){
        isStructure = true;
        structure = s;
        return this;
    }

    public boolean isStructure(){
        return isStructure;
    }

    public StructureTile isStructure(boolean isStructure){
        this.isStructure = isStructure;
        return this;
    }

    public GameStructure getStructure(){
        return structure;
    }

    public StructureTile collidable(boolean isCollidable){
        this.isCollidable = isCollidable;
        return this;
    }

    public boolean isCollidable(){
        return isCollidable;
    }

    public StructureTile isDoor(boolean isDoor){
        this.isDoor = isDoor;
        return this;
    }

    public boolean isDoor(){
        return isDoor;
    }

    public StructureTile set(StructureTile s){
        this.isStructure = s.isStructure;
        this.structure = s.structure;
        this.isCollidable = s.isCollidable;
        this.isDoor = s.isDoor;
        return this;
    }



}
