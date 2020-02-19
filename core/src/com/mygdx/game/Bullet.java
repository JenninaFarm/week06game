package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bullet extends Sprite {
    private float speedX = 100f;

    public Bullet(Texture t) {
        super(new TextureRegion(t));

        setSize(getWidth()/40f, getHeight()/40f);
    }

    public void shoot(float x, float y, SpriteBatch batch) {
        Gdx.app.log("Log", "Drawing tried");
        setX(x);
        setY(y);
        while(getX() < 3840) {
            batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
            setX(getX() + speedX * Gdx.graphics.getDeltaTime());
        }
    }
}

