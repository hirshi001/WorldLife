package com.hirshi001.billions.util.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationCycle {

    private Animator animator;
    int index;
    public AnimationCycle(Animator animator){
        this.animator = animator;
        index = 0;
    }

    public TextureRegion cycle(){
        index++;
        if(index>=animator.size()) index = 0;
        TextureRegion t = animator.getTexture(index);
        return t;
    }

    public TextureRegion get(){
        return animator.getTexture(index);
    }

}
