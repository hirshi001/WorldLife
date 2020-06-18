package com.hirshi001.billions.util.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {

    private TextureRegion[] textures;

    public Animator(TextureRegion[] textures){
        this.textures = textures;
    }

    public TextureRegion getTexture(int index){
        return textures[index%textures.length];
    }

    public int size(){
        return textures.length;
    }

}
