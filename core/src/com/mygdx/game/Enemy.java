package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends Sprite {
    public Enemy(Texture t) {
        super(new TextureRegion(t));
    }
}
