package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {
    public static TextureRegion[] transformTo1D(TextureRegion [][] tmp, int frameCols, int frameRows) {
        TextureRegion [] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;

        for(int i=0; i < frameRows; i++) {
            for(int j=0; j < frameCols; j++) {
                frames[index] = tmp[i][j];
                index++;
            }
        }
        return frames;
    }

    public static TextureRegion [] deleteTheLastFrame(TextureRegion [] tr) {
        TextureRegion [] temp = new TextureRegion[tr.length - 1];
        for(int i=0; i<temp.length; i++) {
            temp[i] = tr[i];
        }
        return temp;
    }

    public static void flip(Animation<TextureRegion> animation) {
        TextureRegion [] regions = animation.getKeyFrames();
        for(TextureRegion r : regions) {
            r.flip(true, false);
        }
    }
}
