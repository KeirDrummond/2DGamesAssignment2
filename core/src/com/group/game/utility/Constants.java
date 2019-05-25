package com.group.game.utility;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by gerard on 09/11/2016.
 * Updated 17/02/18
 */

public class Constants {
    //Screen Size
    public static final float VIRTUAL_WIDTH = Gdx.graphics.getWidth();
    public static final float VIRTUAL_HEIGHT = Gdx.graphics.getHeight();
    //World to screen scale
    public static final float TILE_SIZE   = 32;
    public static final float UNITSCALE = 1.0f / TILE_SIZE;
    //Animation Speed
    public static final float FRAME_DURATION = 1.0f / 30.0f;
    public static final float TIME_STEP=1/60f;
    public static final int LEVEL_TIME = 999;

    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    public static final String BACKGROUND = "tileData/fullTrain_V3.1.tmx";
    public static final String PHYSICS_MATERIALS_PATH = "tileData/physicsData.json";

    public static final float DENSITY=5f;
    public static final float FRICTION=20f;
    public static final float RESTITUTION= 0f;
    //impulse strength
    public static final float MOVESPEED=50f;
    public static final float FORCE_Y=300f;
    //Speed
    public static final float MAX_VELOCITY = 15f;
    //player body
    public static int PLAYER_WIDTH= 3;
    public static int PLAYER_HEIGHT=4;
    public static float PLAYER_OFFSET_Y=2.15f;
    public static float PLAYER_OFFSET_X=1.5f;
    //player graphics
    public static final String PLAYER_ATLAS_PATH = "atlas/character.atlas";
    public static final Texture MEDIUM = new Texture(Gdx.files.internal("gfx/mediumSize.png"));
    public static final Texture SMALL = new Texture(Gdx.files.internal("gfx/smallSize.png"));
    public static final Texture TINY = new Texture(Gdx.files.internal("gfx/tinySize.png"));
    //player start position
    public static final Vector2 START_POSITION = new Vector2(10,10);

    public static final String BACKGROUND_PATH = "backgrounds/background_V1-1.png";

    public static final String REVOLVER_AMMO_PICKUP_PATH = "atlas/revolver_ammo.atlas";
    public static final String REVOLVER_AMMO_PICKUP_SOUND_PATH = "sounds/revolverpickup.wav";


}
