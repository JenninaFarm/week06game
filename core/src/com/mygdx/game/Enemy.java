package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Enemy extends Sprite {

    final int FRAME_COL = 3;
    final int FRAME_ROW = 1;
    private float stateTime = 0.0f;
    private float speedX = 70f;
    private float speedY = 70f;
    private boolean directionDown;

    private Animation<TextureRegion> polarAnimation;
    private TextureRegion currentPolarFrame;


    public Enemy(Texture t) {
        super(new TextureRegion(t));

        setX(30);
        setX(30);

        //setX(MathUtils.random(840, 3600));
        //setY(MathUtils.random(1, 600));

        int dir = MathUtils.random(1, 2);
        if(dir == 1) {
            directionDown = true;
        } else {
            directionDown = false;
        }

        createPolarAnimation();
    }

    private void createPolarAnimation() {
        TextureRegion [][] polarRegion = TextureRegion.split(
                getTexture(),
                getRegionWidth() / FRAME_COL,
                getRegionHeight() / FRAME_ROW
        );

        TextureRegion [] polarArray = Utils.transformTo1D(polarRegion, FRAME_COL, FRAME_ROW);

        polarAnimation = new Animation(7/60, polarArray);

        currentPolarFrame = polarAnimation.getKeyFrame(stateTime, true);
    }

    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        batch.draw(currentPolarFrame, getX(), getY(), getWidth(), getHeight());

        currentPolarFrame = polarAnimation.getKeyFrame(stateTime, true);
    }


}
