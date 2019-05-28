package com.group.game.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

//New class created to create the scrolling background effect.

public class BackgroundManager {

    //An array of background images to create a seamless loop.
    private Texture[] background;
    private float[] bgX;
    private float backgroundSize;

    private float scrollSpeed = 0f;

    //Image scale based on the world size and the image size.
    private float imageScale = 1/32f;

    //The constructor of this class gets the image to use, how many cycles of the image (enough to fit the world) and how fast they move.
    public BackgroundManager(String path, int loops, float speed){
        //Creates the array with size based on loops.
        background = new Texture[loops];
        //An array of identical size to hold the X coordinates.
        bgX = new float[loops];

        //Each array item gets the same texture.
        for (int i = 0; i < background.length; i++)
        {
            background[i] = new Texture(path);
        }

        //Singular example for the width.
        backgroundSize = background[0].getWidth();

        //Sets the starting position for each image so they're all touching at the edges.
        for (int i = 0; i < background.length; i++)
        {
            bgX[i] = i * backgroundSize * imageScale;
        }

        scrollSpeed = speed;
    }

    //Every update, the background images move a bit.
    public void update(float delta){
        for (int i = 0; i < background.length; i++){
            bgX[i] = bgX[i] - (1 * scrollSpeed * delta);

            //If one image reaches the left edge (Off camera), it teleports back around to the right to continue the loop.
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
