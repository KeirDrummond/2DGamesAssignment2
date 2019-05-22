package com.group.game.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class BackgroundManager {

    private Texture[] background;
    private float[] bgX;
    private float backgroundSize;

    private float scrollSpeed = 0f;

    private float imageScale = 1/32f;

    public BackgroundManager(String path, int loops, float speed, CameraManager cameraManager){
        background = new Texture[loops];
        bgX = new float[loops];
        for (int i = 0; i < background.length; i++)
        {
            background[i] = new Texture(path);
        }
        backgroundSize = background[0].getWidth();
        for (int i = 0; i < background.length; i++)
        {
            bgX[i] = i * backgroundSize * imageScale;
        }

        scrollSpeed = speed;
    }

    public void update(float delta){
        for (int i = 0; i < background.length; i++){
            bgX[i] = bgX[i] - (1 * scrollSpeed * delta);
            if (bgX[i] <= 0 - 40)
            { bgX[i] = bgX[i] + (background.length * backgroundSize * imageScale); }
        }
    }

    public void draw(SpriteBatch batch){
        for (int i = 0; i < background.length; i++){
            batch.draw(background[i], MathUtils.floor(bgX[i]), 0, 40, 25);
        }
    }

    public void SetScrollSpeed(float speed){scrollSpeed = speed;}
    public float GetScrollSpeed() {return scrollSpeed;}
}
