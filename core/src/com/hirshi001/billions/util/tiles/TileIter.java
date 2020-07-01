package com.hirshi001.billions.util.tiles;

public class TileIter {

    public int startX, startY;
    public int[][] tiles;

    public TileIter(int startX, int startY, int[][] tiles) {
        this.startX = startX;
        this.startY = startY;
        this.tiles = tiles;
    }

    public static TileIter createTileIterRelativeTo(short[][] tiles, float startX, float startY, float endX, float endY){
        if(startX<0) startX=0;
        if(startY<0) startY=0;
        if(endX>tiles[0].length-1) endX=tiles[0].length-1;
        if(endY>tiles.length-1) endY = tiles.length-1;

        int stX = (int)Math.floor(startX);
        int stY = (int)Math.floor(startY);
        int enX = (int)Math.floor(endX);
        int enY = (int)Math.floor(endY);
        if(enY+1 - stY<0 || enX+1-stX<0){
            return new TileIter(stX, stY, new int[][]{});
        }
        int[][] newTiles = new int[enY+1 - stY][enX+1-stX];

        int i, j;
        for(i=0;i<newTiles.length;i++){
            for(j=0;j<newTiles[i].length;j++){
                newTiles[i][j] = tiles[i+stY][j+stX];
            }
        }
        return new TileIter(stX, stY, newTiles);
    }

}
