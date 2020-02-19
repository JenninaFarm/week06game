package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Sprite {

    private Animation<TextureRegion> beaverAnimation;
    private TextureRegion currentBeaverFrame;

    private Animation<TextureRegion> drowningAnimation;
    private Texture drowning;

    private final int FRAME_COLS = 3;
    private final int FRAME_ROWS = 1;
    private float stateTime = 0.0f;
    private boolean alive = true;

    private float speedX = 60f;
    private float speedY = 150f;
    private float direction = 0;

    //new commit

    public Player(Texture t) {
        super(new TextureRegion(t));
        drowning = new Texture("drownBeaver.png");

        setX(1f);
        setY(1f);
        setSize(getWidth()/3.2f, getHeight()/1.02f);

        createBeaverAnimation();
        createDrowningAnimation();
    }

    private void createBeaverAnimation() {
        TextureRegion [][] beaverRegion = TextureRegion.split(
                getTexture(),
                getRegionWidth() / FRAME_COLS,
                getRegionHeight() / FRAME_ROWS ) ;

        TextureRegion [] beaverArray = Utils.transformTo1D(beaverRegion, FRAME_COLS, FRAME_ROWS);

        beaverAnimation = new Animation(7/60f, beaverArray);

        currentBeaverFrame = beaverAnimation.getKeyFrame(stateTime, true);
    }

    private void createDrowningAnimation() {
        TextureRegion [][] drowningRegion = TextureRegion.split(
                drowning,
                drowning.getWidth() /FRAME_COLS,
                drowning.getHeight() / FRAME_ROWS);
        TextureRegion [] drowningArray = Utils.transformTo1D(drowningRegion, FRAME_COLS, FRAME_ROWS);

        drowningAnimation = new Animation(7/60, drowningArray);
    }

    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        float moveAmountX = speedX * Gdx.graphics.getDeltaTime();
        float moveAmountY = speedY * Gdx.graphics.getDeltaTime() * direction;
        setX(getX() + moveAmountX);
        setY(getY() + moveAmountY);
        direction = 0;

        batch.draw(currentBeaverFrame, getX(), getY(), getWidth(), getHeight());
        if(alive) {
            System.out.println(stateTime);
            currentBeaverFrame = beaverAnimation.getKeyFrame(stateTime, true);
        } else {
            System.out.println(stateTime);
            currentBeaverFrame = drowningAnimation.getKeyFrame(stateTime, true);
        }
    }

    public void move(float f) {
        direction = f;
    }

    public void die() {
        Gdx.app.log("Log", "Player Dies");
        if(alive) {
            stateTime = 0.0f;
            speedX = 0.0f;
            speedY = 0.0f;
            alive = false;
        }
    }
}
